# Jenkins Pipeline - Quick Reference

## Quick Start

### 1. One-Time Setup
```bash
# In Jenkins → Manage Jenkins → Global Tool Configuration
- Add Maven: Name = "Maven-3.9.5"
- Add JDK: Name = "JDK-21"

# Install Required Plugins
- Pipeline
- Git
- Maven Integration
- HTML Publisher
- Cucumber Reports
- Email Extension
```

### 2. Create Jenkins Job
```
New Item → Pipeline → Configure:
- Pipeline from SCM
- Git: <your-repo-url>
- Script Path: Jenkinsfile
```

### 3. Build with Parameters
```
Browser: chrome | firefox | edge
Environment: dev | staging | prod
Headless: true | false
Tags: @smoke | @regression | @admin | @provider | @client
```

## Common Tag Combinations

| Use Case | Tag |
|----------|-----|
| Quick smoke test | `@smoke` |
| Full regression | `@regression` |
| Admin module only | `@admin` |
| Provider module only | `@provider` |
| Client module only | `@client` |
| Smoke tests for admin | `@smoke and @admin` |
| Exclude WIP tests | `@regression and not @wip` |

## Local Testing (Before Jenkins)

### Windows
```batch
# Basic smoke test
run-tests.bat chrome dev false @smoke

# Regression with parallel execution
run-tests.bat chrome dev true @regression true 3

# Admin module tests
run-tests.bat chrome dev false "@admin and @smoke"
```

### Linux/Mac
```bash
# Basic smoke test
./run-tests.sh chrome dev false @smoke

# Regression with parallel execution
./run-tests.sh firefox staging true @regression true 3

# Provider module tests
./run-tests.sh chrome dev false "@provider and @smoke"
```

## Pipeline Stages Flow

```
Checkout → Environment Setup → Dependency Resolution → 
Compile → Run Tests → Generate Reports → Publish Reports
```

## Accessing Reports

After build completion:
1. **Cucumber HTML Report** - Left sidebar
2. **Extent Report** - Left sidebar
3. **Build Artifacts** - Screenshots, logs, JSON reports
4. **Test Results** - JUnit test results trend

## Common Build Configurations

### Daily Smoke Tests
```
Schedule: 0 2 * * *
Browser: chrome
Environment: dev
Headless: true
Tags: @smoke
Parallel: false
```

### Nightly Regression
```
Schedule: 0 22 * * *
Browser: chrome
Environment: staging
Headless: true
Tags: @regression
Parallel: true
Threads: 5
```

### Pre-Release Testing
```
Browser: chrome
Environment: staging
Headless: false
Tags: @regression and not @wip
Parallel: true
Threads: 3
```

## Troubleshooting Quick Fixes

| Issue | Solution |
|-------|----------|
| Maven not found | Check tool name matches "Maven-3.9.5" |
| JDK not found | Check tool name matches "JDK-21" |
| No tests executed | Verify tags are correct |
| Reports not showing | Check HTML Publisher plugin installed |
| Email not sent | Configure SMTP in Jenkins settings |

## Environment URLs

| Environment | URL |
|-------------|-----|
| dev | https://dev1.mtomics.com |
| staging | https://staging.mtomics.com |
| prod | https://mtomics.com |

## Email Notifications

Automatically sent for:
- ✓ **Success** - All tests passed
- ✗ **Failure** - Build/compilation failed
- ⚠ **Unstable** - Some tests failed

Configure recipients in Jenkins job settings.

## Best Practices

1. ✓ Start with `@smoke` tags for quick validation
2. ✓ Use `headless=true` for CI/CD pipelines
3. ✓ Enable parallel execution for large test suites
4. ✓ Review Cucumber trends regularly
5. ✓ Keep test tags organized and meaningful
6. ✓ Archive screenshots for failed tests
7. ✓ Set up email notifications for team awareness

## File Structure

```
Project_Automation/
├── Jenkinsfile              # Main pipeline definition
├── JENKINS_SETUP.md         # Detailed setup guide
├── JENKINS_QUICK_REF.md     # This quick reference
├── jenkins.properties       # Configuration properties
├── run-tests.bat           # Windows local test script
├── run-tests.sh            # Linux/Mac local test script
└── pom.xml                 # Maven configuration
```

## Support

For detailed information, see [JENKINS_SETUP.md](JENKINS_SETUP.md)
