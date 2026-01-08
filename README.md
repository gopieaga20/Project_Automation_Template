# MTOmics Automation Testing Framework

## Overview
Selenium Java Cucumber automation framework for the MTOmics platform with Jenkins CI/CD integration.

## Project Structure
```
Project_Automation/
├── src/
│   ├── main/java/com/mtomics/          # Page Objects and utilities
│   └── test/
│       ├── java/com/mtomics/
│       │   ├── runners/                # Test runners
│       │   ├── stepDefinitions/        # Cucumber step definitions
│       │   └── hooks/                  # Test hooks
│       └── resources/
│           ├── features/               # Cucumber feature files
│           └── config/                 # Configuration files
├── Jenkinsfile                         # Jenkins pipeline definition
├── JENKINS_SETUP.md                    # Jenkins setup guide
├── JENKINS_QUICK_REF.md                # Quick reference
├── run-tests.bat                       # Windows test script
├── run-tests.sh                        # Linux/Mac test script
└── pom.xml                             # Maven configuration
```

## Quick Start

### Prerequisites
- Java 21
- Maven 3.9.5+
- Chrome/Firefox/Edge browser

### Run Tests Locally

**Windows**:
```batch
run-tests.bat chrome dev false @smoke
```

**Linux/Mac**:
```bash
./run-tests.sh chrome dev false @smoke
```

### Run with Maven
```bash
mvn clean test -Dbrowser=chrome -Denvironment=dev -Dcucumber.filter.tags="@smoke"
```

## Jenkins CI/CD

### Quick Setup
1. Install required Jenkins plugins (Pipeline, Git, Maven, HTML Publisher, Cucumber Reports)
2. Configure Maven and JDK in Global Tool Configuration
3. Create Pipeline job pointing to `Jenkinsfile`
4. Build with parameters

### Build Parameters
- **Browser**: chrome | firefox | edge
- **Environment**: dev | staging | prod
- **Headless**: true | false
- **Tags**: @smoke | @regression | @admin | @provider | @client

### Documentation
- **[JENKINS_SETUP.md](JENKINS_SETUP.md)** - Comprehensive setup guide
- **[JENKINS_QUICK_REF.md](JENKINS_QUICK_REF.md)** - Quick reference

## Test Execution

### Common Tag Combinations
```bash
@smoke                    # Quick smoke tests
@regression              # Full regression suite
@admin                   # Admin module tests
@provider                # Provider module tests
@client                  # Client module tests
@smoke and @admin        # Admin smoke tests
@regression and not @wip # Regression excluding WIP
```

### Parallel Execution
```bash
mvn clean test -Dcucumber.filter.tags="@regression" -Dparallel=methods -DthreadCount=3
```

## Reports

### Generated Reports
- **Cucumber HTML**: `target/cucumber-reports/cucumber.html`
- **Extent Report**: `test-output/`
- **JUnit XML**: `target/cucumber-reports/cucumber.xml`
- **Screenshots**: `reports/screenshots/`
- **Logs**: `logs/`

### View Reports in Jenkins
- Cucumber HTML Report (left sidebar)
- Extent Report (left sidebar)
- Test Results (JUnit trends)
- Build Artifacts (screenshots, logs)

## Configuration

### Environment URLs
- **dev**: https://dev1.mtomics.com
- **staging**: https://staging.mtomics.com
- **prod**: https://mtomics.com

### Browser Configuration
Edit `src/test/resources/config/config.properties`:
```properties
browser=chrome
headless=false
implicit.wait=10
explicit.wait=20
```

## Technology Stack
- **Java**: 21
- **Selenium**: 4.15.0
- **Cucumber**: 7.14.0
- **Maven**: 3.9.5
- **WebDriverManager**: 5.6.2
- **ExtentReports**: 5.1.1
- **JUnit**: 4.13.2
- **TestNG**: 7.8.0

## Additional Documentation
- **[AUTOMATION_TUTORIAL.md](AUTOMATION_TUTORIAL.md)** - Framework tutorial
- **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)** - Quick reference guide
- **[README_AUTOMATION_DOCS.md](README_AUTOMATION_DOCS.md)** - Documentation index

## Support
For detailed information, refer to the documentation files or contact the automation team.
