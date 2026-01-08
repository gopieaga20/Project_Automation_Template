# Jenkins Pipeline Setup Guide

## Overview
This Jenkins pipeline automates the execution of Selenium Cucumber tests for the MTOmics platform. It supports multiple browsers, environments, and flexible test execution strategies.

## Prerequisites

### 1. Jenkins Installation
- Jenkins 2.x or higher
- Required Jenkins plugins:
  - Pipeline
  - Git
  - Maven Integration
  - HTML Publisher
  - Cucumber Reports
  - Email Extension
  - Workspace Cleanup

### 2. Global Tool Configuration
Configure the following tools in Jenkins (Manage Jenkins → Global Tool Configuration):

#### Maven Configuration
- Name: `Maven-3.9.5`
- Install automatically or specify MAVEN_HOME path
- Version: 3.9.5 or higher

#### JDK Configuration
- Name: `JDK-21`
- Install automatically or specify JAVA_HOME path
- Version: Java 21

### 3. Browser Drivers
Ensure the following browsers are installed on Jenkins agents:
- Google Chrome (for Chrome tests)
- Mozilla Firefox (for Firefox tests)
- Microsoft Edge (for Edge tests)

The project uses WebDriverManager, so browser drivers will be downloaded automatically.

## Pipeline Features

### 1. Parameterized Builds
The pipeline supports the following parameters:

| Parameter | Type | Options | Description |
|-----------|------|---------|-------------|
| BROWSER | Choice | chrome, firefox, edge | Browser for test execution |
| ENVIRONMENT | Choice | dev, staging, prod | Target environment |
| HEADLESS | Choice | true, false | Run browser in headless mode |
| TAGS | String | @smoke, @regression, etc. | Cucumber tags to execute |
| PARALLEL_EXECUTION | Boolean | true, false | Enable parallel test execution |
| THREAD_COUNT | Choice | 1, 2, 3, 4, 5 | Number of parallel threads |

### 2. Environment URLs
The pipeline automatically selects the base URL based on the environment:
- **dev**: https://dev1.mtomics.com
- **staging**: https://staging.mtomics.com
- **prod**: https://mtomics.com

### 3. Test Execution Tags
Common tag combinations:
- `@smoke` - Quick smoke tests
- `@regression` - Full regression suite
- `@admin` - Admin module tests
- `@provider` - Provider module tests
- `@client` - Client module tests
- `@smoke and @admin` - Smoke tests for admin module
- `@regression and not @wip` - Regression excluding work-in-progress

## Setting Up Jenkins Job

### 1. Create New Pipeline Job
1. Go to Jenkins Dashboard
2. Click "New Item"
3. Enter job name: `MTOmics-Automation-Tests`
4. Select "Pipeline"
5. Click "OK"

### 2. Configure Pipeline
1. **General Settings**
   - ☑ This project is parameterized (parameters are defined in Jenkinsfile)
   - ☑ Discard old builds (keep last 10 builds)

2. **Pipeline Configuration**
   - Definition: `Pipeline script from SCM`
   - SCM: `Git`
   - Repository URL: `<your-git-repository-url>`
   - Credentials: Select appropriate credentials
   - Branch Specifier: `*/main` (or your default branch)
   - Script Path: `Jenkinsfile`

3. **Build Triggers** (Optional)
   - ☑ Poll SCM: `H/15 * * * *` (every 15 minutes)
   - ☑ GitHub hook trigger for GITScm polling (if using GitHub)

4. **Email Notifications**
   - Configure in Jenkins → Manage Jenkins → Configure System
   - Set up SMTP server details
   - Configure default recipients

### 3. Save and Build
1. Click "Save"
2. Click "Build with Parameters"
3. Select desired parameters
4. Click "Build"

## Pipeline Stages

### 1. Checkout
- Checks out code from Git repository

### 2. Environment Setup
- Displays build configuration
- Cleans previous build artifacts
- Creates necessary directories

### 3. Dependency Resolution
- Resolves Maven dependencies
- Downloads required libraries

### 4. Compile
- Compiles source and test code

### 5. Run Tests
- Executes Cucumber tests with specified parameters
- Supports parallel execution if enabled

### 6. Generate Reports
- Generates Cucumber HTML reports
- Creates Extent reports

### 7. Publish Reports
- Publishes Cucumber reports
- Publishes HTML reports
- Archives test artifacts (screenshots, logs)
- Publishes JUnit test results

## Accessing Test Reports

After build completion, access reports from the build page:

1. **Cucumber HTML Report**
   - Click "Cucumber HTML Report" in the left sidebar
   - View feature-wise test results

2. **Extent Report**
   - Click "Extent Report" in the left sidebar
   - View detailed test execution report

3. **Build Artifacts**
   - Click "Build Artifacts" to download:
     - Screenshots
     - Logs
     - JSON reports

## Running Tests

### Example 1: Smoke Tests on Chrome (Dev)
```
BROWSER: chrome
ENVIRONMENT: dev
HEADLESS: false
TAGS: @smoke
PARALLEL_EXECUTION: false
```

### Example 2: Regression Tests on Firefox (Staging)
```
BROWSER: firefox
ENVIRONMENT: staging
HEADLESS: true
TAGS: @regression
PARALLEL_EXECUTION: true
THREAD_COUNT: 3
```

### Example 3: Admin Module Tests
```
BROWSER: chrome
ENVIRONMENT: dev
HEADLESS: false
TAGS: @admin and @smoke
PARALLEL_EXECUTION: false
```

## Email Notifications

The pipeline sends email notifications for:
- ✓ **Success**: All tests passed
- ✗ **Failure**: Build or compilation failed
- ⚠ **Unstable**: Some tests failed

Email includes:
- Build information
- Test configuration
- Links to build and reports
- Console output (for failures)

## Troubleshooting

### Issue: Maven or JDK not found
**Solution**: Verify tool names in Jenkinsfile match Global Tool Configuration

### Issue: Browser driver errors
**Solution**: Ensure browsers are installed on Jenkins agent. WebDriverManager should handle drivers automatically.

### Issue: Tests not executing
**Solution**: 
- Check Cucumber tags are correct
- Verify TestRunner.java configuration
- Check console output for errors

### Issue: Reports not publishing
**Solution**:
- Verify HTML Publisher plugin is installed
- Check report paths in Jenkinsfile
- Ensure tests generated report files

### Issue: Email notifications not working
**Solution**:
- Configure SMTP settings in Jenkins
- Set DEFAULT_RECIPIENTS in job configuration
- Check Email Extension plugin configuration

## Advanced Configuration

### Parallel Execution
For faster execution, enable parallel execution:
- Set `PARALLEL_EXECUTION: true`
- Adjust `THREAD_COUNT` based on available resources
- Ensure tests are thread-safe

### Custom Environment URLs
Modify `getBaseUrl()` function in Jenkinsfile to add custom environments:
```groovy
case 'qa':
    return 'https://qa.mtomics.com'
```

### Additional Browsers
Add more browser options in the BROWSER parameter:
```groovy
choices: ['chrome', 'firefox', 'edge', 'safari']
```

### Scheduled Builds
Add cron triggers in job configuration:
```
# Run daily at 2 AM
0 2 * * *

# Run every weekday at 6 PM
0 18 * * 1-5
```

## Best Practices

1. **Start with Smoke Tests**: Run smoke tests first to catch critical issues
2. **Use Headless Mode for CI**: Faster execution and lower resource usage
3. **Tag Your Tests**: Organize tests with meaningful tags
4. **Monitor Build Trends**: Review Cucumber trend reports regularly
5. **Keep Builds Fast**: Use parallel execution for large test suites
6. **Archive Important Artifacts**: Screenshots and logs help debugging
7. **Set Up Notifications**: Stay informed about test failures

## Integration with CI/CD

### GitHub Integration
1. Install GitHub plugin
2. Configure webhook in GitHub repository
3. Enable "GitHub hook trigger" in Jenkins job
4. Tests run automatically on push/PR

### Scheduled Regression
Create separate job for nightly regression:
- Schedule: `0 2 * * *` (2 AM daily)
- Tags: `@regression`
- Parallel: `true`
- Thread Count: `5`

## Support

For issues or questions:
1. Check Jenkins console output
2. Review test logs in `logs/` directory
3. Check Cucumber reports for test failures
4. Contact automation team

## Version History

- **v1.0** - Initial Jenkins pipeline with multi-browser support, parameterized builds, and comprehensive reporting
