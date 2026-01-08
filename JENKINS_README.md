# Jenkins Pipeline Setup Guide

## Prerequisites

### 1. Jenkins Configuration
Install the following plugins in Jenkins:
- **Pipeline** - For pipeline support
- **Git** - For source code management
- **Maven Integration** - For Maven builds
- **HTML Publisher** - For publishing HTML reports
- **JUnit** - For test result visualization

### 2. Global Tool Configuration
Configure in Jenkins → Manage Jenkins → Global Tool Configuration:

**Maven**:
- Name: `Maven`
- Install automatically or specify Maven home path

**JDK**:
- Name: `Java`
- Install automatically or specify JDK 21 path

## Jenkins Job Setup

### Step 1: Create Pipeline Job
1. Go to Jenkins Dashboard
2. Click **New Item**
3. Enter job name: `MTOmics-Automation-Tests`
4. Select **Pipeline**
5. Click **OK**

### Step 2: Configure Pipeline
1. **General Settings**:
   - Description: `MTOmics Selenium Cucumber Automation Tests`
   - ☑ Discard old builds (Keep last 10 builds)

2. **Pipeline Configuration**:
   - Definition: `Pipeline script from SCM`
   - SCM: `Git`
   - Repository URL: `https://github.com/gopieaga20/Project_Automation_Template.git`
   - Credentials: Add your GitHub credentials
   - Branch: `*/main`
   - Script Path: `Jenkinsfile`

3. **Build Triggers** (Optional):
   - ☑ Poll SCM: `H/30 * * * *` (every 30 minutes)
   - ☑ GitHub hook trigger (if webhook configured)

### Step 3: Save and Build
1. Click **Save**
2. Click **Build with Parameters**
3. Select parameters and click **Build**

## Build Parameters

| Parameter | Options | Default | Description |
|-----------|---------|---------|-------------|
| BROWSER | chrome, firefox, edge | chrome | Browser for test execution |
| ENVIRONMENT | dev, staging, prod | dev | Target environment |
| HEADLESS | true, false | false | Headless browser mode |
| TAGS | Any Cucumber tags | @adminlogin | Test tags to execute |

## Environment URLs

| Environment | URL |
|-------------|-----|
| dev | https://dev1.mtomics.com |
| staging | https://staging.mtomics.com |
| prod | https://mtomics.com |

## Test Execution Examples

### Example 1: Admin Login Test
```
BROWSER: chrome
ENVIRONMENT: dev
HEADLESS: false
TAGS: @adminlogin
```

### Example 2: Smoke Tests
```
BROWSER: chrome
ENVIRONMENT: dev
HEADLESS: true
TAGS: @smoke
```

### Example 3: Full Admin Module
```
BROWSER: firefox
ENVIRONMENT: staging
HEADLESS: true
TAGS: @admin
```

## Viewing Reports

After build completion, access reports from the build page:

1. **Cucumber HTML Report**
   - Click "Cucumber HTML Report" in left sidebar
   - View feature-wise test results

2. **Extent Report**
   - Click "Extent Report" in left sidebar
   - View detailed test execution dashboard

3. **Test Results**
   - Click "Test Results" for JUnit trends
   - View pass/fail statistics

4. **Build Artifacts**
   - Click "Build Artifacts"
   - Download screenshots, logs, reports

## Pipeline Stages

1. **Checkout** - Retrieves code from Git repository
2. **Environment Setup** - Displays configuration and cleans artifacts
3. **Build** - Compiles project and resolves dependencies
4. **Run Tests** - Executes Cucumber tests with specified parameters
5. **Generate Reports** - Creates test reports
6. **Post Actions** - Publishes reports and archives artifacts

## Common Cucumber Tags

| Tag | Description |
|-----|-------------|
| @adminlogin | Admin login test |
| @smoke | Quick smoke tests |
| @regression | Full regression suite |
| @admin | Admin module tests |
| @provider | Provider module tests |
| @client | Client module tests |
| @positive | Positive test scenarios |
| @negative | Negative test scenarios |

## Troubleshooting

### Issue: Maven or JDK not found
**Solution**: Verify tool names in Jenkinsfile match Global Tool Configuration (Maven and Java)

### Issue: Tests not executing
**Solution**: 
- Check Cucumber tags are correct
- Verify TestRunner.java has matching tags
- Check console output for errors

### Issue: Reports not publishing
**Solution**:
- Verify HTML Publisher plugin is installed
- Check report paths exist after test execution
- Review Jenkins console output

### Issue: Git checkout fails
**Solution**:
- Verify repository URL is correct
- Check Git credentials are configured
- Ensure branch name is correct (main)

## Best Practices

1. ✓ Start with `@adminlogin` or `@smoke` tags for quick validation
2. ✓ Use `headless=true` for faster CI/CD execution
3. ✓ Review test reports after each build
4. ✓ Keep Jenkins plugins updated
5. ✓ Monitor build trends regularly
6. ✓ Use meaningful Cucumber tags
7. ✓ Archive important artifacts (screenshots, logs)

## Scheduled Builds

To run tests automatically, configure build triggers:

**Daily Smoke Tests** (2 AM):
```
0 2 * * *
```

**Nightly Regression** (10 PM):
```
0 22 * * *
```

**Weekday Tests** (6 PM Mon-Fri):
```
0 18 * * 1-5
```

## Support

For issues or questions:
1. Check Jenkins console output
2. Review test logs in artifacts
3. Check Cucumber/Extent reports
4. Contact automation team

## Quick Reference

### Run Build with Parameters
```
Jenkins → MTOmics-Automation-Tests → Build with Parameters
```

### View Latest Reports
```
Jenkins → MTOmics-Automation-Tests → Latest Build → Reports
```

### Download Artifacts
```
Jenkins → MTOmics-Automation-Tests → Build → Build Artifacts
```
