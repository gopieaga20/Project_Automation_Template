#!/bin/bash
# Script to run tests locally with Jenkins-like configuration
# This helps test the pipeline configuration before pushing to Jenkins

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Default values
BROWSER=${1:-chrome}
ENVIRONMENT=${2:-dev}
HEADLESS=${3:-false}
TAGS=${4:-@smoke}
PARALLEL=${5:-false}
THREADS=${6:-1}

# Environment URLs
case $ENVIRONMENT in
    dev)
        BASE_URL="https://dev1.mtomics.com"
        ;;
    staging)
        BASE_URL="https://staging.mtomics.com"
        ;;
    prod)
        BASE_URL="https://mtomics.com"
        ;;
    *)
        BASE_URL="https://dev1.mtomics.com"
        ;;
esac

echo -e "${BLUE}=========================================="
echo "MTOmics Test Execution"
echo -e "==========================================${NC}"
echo -e "${GREEN}Browser:${NC} $BROWSER"
echo -e "${GREEN}Environment:${NC} $ENVIRONMENT"
echo -e "${GREEN}Base URL:${NC} $BASE_URL"
echo -e "${GREEN}Headless:${NC} $HEADLESS"
echo -e "${GREEN}Tags:${NC} $TAGS"
echo -e "${GREEN}Parallel:${NC} $PARALLEL"
echo -e "${GREEN}Threads:${NC} $THREADS"
echo -e "${BLUE}==========================================${NC}"

# Clean previous build artifacts
echo -e "${YELLOW}Cleaning previous build artifacts...${NC}"
rm -rf target test-output reports logs
mkdir -p reports/screenshots logs

# Build Maven command
MVN_CMD="mvn clean test \
    -Dbrowser=$BROWSER \
    -Dheadless=$HEADLESS \
    -Denvironment=$ENVIRONMENT \
    -Dbase.url=$BASE_URL \
    -Dcucumber.filter.tags=\"$TAGS\""

if [ "$PARALLEL" = "true" ]; then
    MVN_CMD="$MVN_CMD -Dparallel=methods -DthreadCount=$THREADS"
fi

# Execute tests
echo -e "${YELLOW}Executing tests...${NC}"
eval $MVN_CMD

# Check exit code
if [ $? -eq 0 ]; then
    echo -e "${GREEN}=========================================="
    echo "✓ Tests completed successfully"
    echo -e "==========================================${NC}"
else
    echo -e "${RED}=========================================="
    echo "✗ Tests failed"
    echo -e "==========================================${NC}"
fi

# Generate reports
echo -e "${YELLOW}Generating reports...${NC}"
mvn verify -DskipTests

# Display report locations
echo -e "${BLUE}=========================================="
echo "Test Reports Generated"
echo -e "==========================================${NC}"
echo -e "${GREEN}Cucumber HTML Report:${NC} target/cucumber-reports/cucumber.html"
echo -e "${GREEN}Extent Report:${NC} test-output/"
echo -e "${GREEN}Screenshots:${NC} reports/screenshots/"
echo -e "${GREEN}Logs:${NC} logs/"
echo -e "${BLUE}==========================================${NC}"

# Usage information
if [ "$1" = "--help" ] || [ "$1" = "-h" ]; then
    echo ""
    echo "Usage: ./run-tests.sh [BROWSER] [ENVIRONMENT] [HEADLESS] [TAGS] [PARALLEL] [THREADS]"
    echo ""
    echo "Parameters:"
    echo "  BROWSER      - chrome, firefox, edge (default: chrome)"
    echo "  ENVIRONMENT  - dev, staging, prod (default: dev)"
    echo "  HEADLESS     - true, false (default: false)"
    echo "  TAGS         - Cucumber tags (default: @smoke)"
    echo "  PARALLEL     - true, false (default: false)"
    echo "  THREADS      - Number of threads (default: 1)"
    echo ""
    echo "Examples:"
    echo "  ./run-tests.sh chrome dev false @smoke"
    echo "  ./run-tests.sh firefox staging true @regression true 3"
    echo "  ./run-tests.sh chrome dev false \"@admin and @smoke\""
    echo ""
fi
