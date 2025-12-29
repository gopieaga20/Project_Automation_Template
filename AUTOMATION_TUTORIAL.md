# Selenium Cucumber Java Automation Framework - Complete Tutorial

## Table of Contents
1. [Introduction](#introduction)
2. [Framework Architecture](#framework-architecture)
3. [Prerequisites](#prerequisites)
4. [Project Setup](#project-setup)
5. [Framework Components Explained](#framework-components-explained)
6. [Creating Your First Test - Step by Step](#creating-your-first-test---step-by-step)
7. [Best Practices](#best-practices)
8. [Running Tests](#running-tests)
9. [Common Errors and Solutions](#common-errors-and-solutions)
10. [Troubleshooting Guide](#troubleshooting-guide)
11. [FAQ](#faq)

---

## Introduction

Welcome to this comprehensive tutorial on Selenium-based automation framework! This guide will teach you everything you need to know about test automation using:

- **Selenium WebDriver** - For browser automation
- **Cucumber** - For writing tests in plain English (BDD)
- **JUnit** - For running tests
- **Page Object Model (POM)** - For organizing code
- **Maven** - For managing dependencies

### What You'll Learn
✅ How automation frameworks are structured  
✅ How to create test scenarios from scratch  
✅ How to write Page Objects (representing web pages)  
✅ How to implement Step Definitions (test logic)  
✅ How to run and debug tests  
✅ How to fix common errors  

---

## Framework Architecture

Our framework uses a **layered architecture** where each layer has a specific responsibility:

```
automation-project/
├── src/test/
│   ├── java/com/company/
│   │   ├── context/          # Sharing data between tests
│   │   ├── hooks/            # Setup and cleanup actions
│   │   ├── pages/            # Page Object classes (one per page)
│   │   ├── runners/          # Test execution configuration
│   │   ├── stepDefinitions/  # Test step implementations
│   │   └── utils/            # Helper classes (waits, config, etc.)
│   └── resources/
│       ├── config/           # Configuration files
│       └── features/         # Test scenarios in plain English
├── pom.xml                   # Dependencies and build settings
└── logs/                     # Test execution logs
```

### Design Patterns Used

1. **Page Object Model (POM)**: Each web page = one Java class
2. **Dependency Injection**: Share data between test steps
3. **Singleton Pattern**: One instance of driver/config
4. **Factory Pattern**: Create page objects efficiently

---

## Prerequisites

### Software You Need

| Software | Version | Download Link |
|----------|---------|---------------|
| Java JDK | 11 or higher | https://www.oracle.com/java/technologies/downloads/ |
| Maven | 3.6+ | https://maven.apache.org/download.cgi |
| IDE | Any (IntelliJ recommended) | https://www.jetbrains.com/idea/download/ |
| Git | Latest | https://git-scm.com/downloads |

### Knowledge You Need
- ✅ Basic Java (variables, methods, classes)
- ✅ Basic understanding of web pages (HTML elements)
- ⚠️ No prior Selenium/Cucumber knowledge required!

### Verify Installation

```bash
# Check Java
java -version
# Should show: java version "11" or higher

# Check Maven
mvn -version
# Should show: Apache Maven 3.6.x or higher

# Check Git
git --version
# Should show: git version 2.x.x
```

**❌ If any command fails**: The software is not installed or not in PATH. Reinstall and add to system PATH.

---

## Project Setup

### Step 1: Get the Project

```bash
# Clone the repository
git clone <your-repository-url>

# Navigate to project folder
cd automation-project
```

### Step 2: Open in IDE

**Using IntelliJ IDEA:**
1. Open IntelliJ IDEA
2. Click **File** → **Open**
3. Select your project folder
4. Click **OK**
5. Wait for Maven to download dependencies (see bottom right corner)

**✅ Success**: You'll see "Build successful" in the bottom panel

**❌ Error "Cannot resolve dependencies"**: 
- Check internet connection
- Run: `mvn clean install -U` in terminal
- Delete `.m2/repository` folder and retry

### Step 3: Install Dependencies

```bash
# Clean and install all dependencies
mvn clean install
```

**This command will:**
- Download all required libraries
- Compile your code
- Run any existing tests

**⏱️ First time**: May take 5-10 minutes to download everything

**❌ Error "BUILD FAILURE"**:
- Check Java version: `java -version` (must be 11+)
- Check Maven version: `mvn -version` (must be 3.6+)
- Check internet connection
- Delete `target` folder and retry

### Step 4: Configure Your Environment

Open `src/test/resources/config/config.properties`:

```properties
# Application URL
base.url=https://your-application-url.com

# Browser Settings
browser=chrome          # Options: chrome, firefox, edge
headless=false         # true = no browser window, false = see browser
maximize=true          # true = fullscreen browser

# Wait Times (in seconds)
implicit.wait=10       # Default wait for elements
explicit.wait=20       # Maximum wait for specific conditions
page.load.timeout=30   # Maximum time to load a page

# Test Credentials (for development only)
test.email=test@example.com
test.password=Test@123
```

**Important**: Change `base.url` to your application's URL!

---

## Framework Components Explained

### 1. TestContext - Sharing Data Between Steps

**Location**: `src/test/java/com/company/context/TestContext.java`

**What it does**: Allows different test steps to share information.

**Example Scenario**:
- Step 1: User creates an account (generates email)
- Step 2: User logs in (needs the same email from Step 1)

**How to use**:

```java
// STORING data (in any step)
testContext.setContext("USER_EMAIL", "john@example.com");
testContext.setContext("USER_ID", 12345);
testContext.setContext("IS_VERIFIED", true);

// RETRIEVING data (in another step)
String email = testContext.getContextAsString("USER_EMAIL");
Integer userId = testContext.getContextAsInteger("USER_ID");
Boolean verified = testContext.getContextAsBoolean("IS_VERIFIED");

// CHECKING if data exists
if (testContext.containsContext("USER_EMAIL")) {
    // Do something
}

// CLEARING all data (done automatically after each test)
testContext.clearContext();
```

---

### 2. DriverManager - Managing the Browser

**Location**: `src/test/java/com/company/utils/DriverManager.java`

**What it does**: Opens, configures, and closes the browser.

**Key Features**:
- ✅ Automatically downloads browser drivers
- ✅ Supports Chrome, Firefox, Edge
- ✅ Can run with or without showing browser (headless)
- ✅ Thread-safe for parallel testing

**How it works**:

```java
// Initialize browser (done automatically in Hooks)
DriverManager.initializeDriver();

// Get browser instance to use
WebDriver driver = DriverManager.getDriver();

// Close browser (done automatically in Hooks)
DriverManager.quitDriver();
```

**You don't call these directly!** Hooks handle this automatically.

---

### 3. Hooks - Setup and Cleanup

**Location**: `src/test/java/com/company/hooks/Hooks.java`

**What it does**: Runs code before and after each test scenario.

**Before Each Test (@Before)**:
1. Opens the browser
2. Navigates to the application URL
3. Maximizes the window

**After Each Test (@After)**:
1. Takes screenshot if test failed
2. Closes the browser
3. Clears test data

**Example**:

```java
@Before
public void beforeScenario(Scenario scenario) {
    logger.info("Starting test: " + scenario.getName());
    
    // Open browser
    DriverManager.initializeDriver();
    WebDriver driver = DriverManager.getDriver();
    context.setDriver(driver);
    
    // Go to application
    driver.get(configReader.getBaseUrl());
}

@After
public void afterScenario(Scenario scenario) {
    logger.info("Test finished: " + scenario.getName());
    
    // Take screenshot if failed
    if (scenario.isFailed()) {
        takeScreenshot(scenario);
    }
    
    // Close browser
    DriverManager.quitDriver();
}
```

---

### 4. BasePage - Common Methods for All Pages

**Location**: `src/test/java/com/company/pages/BasePage.java`

**What it does**: Provides reusable methods that all page objects can use.

**Common Methods**:

```java
// Click on an element
protected void click(WebElement element)

// Type text into a field
protected void enterText(WebElement element, String text)

// Get text from an element
protected String getText(WebElement element)

// Check if element is visible
protected boolean isDisplayed(WebElement element)

// Scroll to an element
protected void scrollToElement(WebElement element)

// Click using JavaScript (when normal click doesn't work)
protected void clickUsingJS(WebElement element)

// Get current page URL
protected String getCurrentUrl()

// Refresh the page
protected void refreshPage()
```

**All your page classes will extend BasePage** to use these methods.

---

### 5. WaitHelper - Waiting for Elements

**Location**: `src/test/java/com/company/utils/WaitHelper.java`

**What it does**: Waits for elements to be ready before interacting with them.

**Why it's important**: Web pages load dynamically. Elements may not be immediately available.

**Common Wait Methods**:

```java
// Wait until element is visible on screen
waitHelper.waitForElementVisible(element);

// Wait until element is clickable
waitHelper.waitForElementClickable(element);

// Wait until element exists in HTML
waitHelper.waitForElementPresent(element);

// Check if element is displayed (returns true/false)
boolean isVisible = waitHelper.isElementDisplayed(element);

// Wait with custom timeout (in seconds)
waitHelper.waitForElement(element, 30);

// Hard wait - pause for specific time (USE SPARINGLY!)
waitHelper.hardWait(2000); // 2 seconds
```

**Best Practice**: Always wait before interacting with elements!

---

### 6. ConfigReader - Reading Configuration

**Location**: `src/test/java/com/company/utils/ConfigReader.java`

**What it does**: Reads settings from `config.properties` file.

**Usage**:

```java
ConfigReader config = new ConfigReader();

// Get application URL
String url = config.getBaseUrl();

// Get browser name
String browser = config.getBrowser();

// Get any custom property
String value = config.getProperty("test.email");

// Get with default value if not found
String value = config.getProperty("some.key", "default-value");
```

---

## Creating Your First Test - Step by Step

Let's create a complete test for **Login Functionality**.

### Step 1: Write the Test Scenario (Feature File)

**Location**: `src/test/resources/features/login.feature`

**Create new file** and write:

```gherkin
Feature: User Login
  As a registered user
  I want to log in to the application
  So that I can access my account

  Background:
    Given the login page is open

  @smoke @login
  Scenario: Successful login with valid credentials
    When I enter email "test@example.com"
    And I enter password "Test@123"
    And I click the login button
    Then I should be redirected to the dashboard
    And I should see welcome message

  @login @negative
  Scenario: Login with invalid password
    When I enter email "test@example.com"
    And I enter password "WrongPassword"
    And I click the login button
    Then I should see error message "Invalid credentials"
    And I should remain on the login page

  @login @negative
  Scenario: Login with empty fields
    When I click the login button
    Then I should see error message "Email is required"
```

**Understanding Gherkin**:
- **Feature**: What functionality you're testing
- **Background**: Steps that run before EACH scenario
- **Scenario**: One test case
- **@tags**: Labels to organize and run specific tests
- **Given**: Starting condition (setup)
- **When**: Action performed
- **Then**: Expected result (verification)
- **And**: Additional step of same type

---

### Step 2: Create the Page Object

**Location**: `src/test/java/com/company/pages/LoginPage.java`

**Create new file**:

```java
package com.company.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LoginPage - Represents the login page of the application
 */
public class LoginPage extends BasePage {

    // ========== STEP 2A: Define Web Elements ==========
    
    // Email input field
    @FindBy(id = "email")
    private WebElement emailInput;
    
    // Password input field
    @FindBy(id = "password")
    private WebElement passwordInput;
    
    // Login button
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;
    
    // Error message
    @FindBy(css = ".error-message")
    private WebElement errorMessage;
    
    // Welcome message (after login)
    @FindBy(xpath = "//h1[contains(text(),'Welcome')]")
    private WebElement welcomeMessage;

    // ========== STEP 2B: Constructor ==========
    
    /**
     * Constructor - initializes the page
     */
    public LoginPage(WebDriver driver) {
        super(driver);  // Call parent class constructor
        PageFactory.initElements(driver, this);  // Initialize all @FindBy elements
        logger.info("LoginPage initialized");
    }

    // ========== STEP 2C: Page Actions (Methods) ==========
    
    /**
     * Check if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(loginButton);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Enter email address
     */
    public void enterEmail(String email) {
        logger.info("Entering email: " + email);
        waitHelper.waitForElementVisible(emailInput);
        enterText(emailInput, email);
    }
    
    /**
     * Enter password
     */
    public void enterPassword(String password) {
        logger.info("Entering password");
        waitHelper.waitForElementVisible(passwordInput);
        enterText(passwordInput, password);
    }
    
    /**
     * Click login button
     */
    public void clickLoginButton() {
        logger.info("Clicking login button");
        waitHelper.waitForElementClickable(loginButton);
        click(loginButton);
    }
    
    /**
     * Get error message text
     */
    public String getErrorMessage() {
        logger.info("Getting error message");
        waitHelper.waitForElementVisible(errorMessage);
        return getText(errorMessage);
    }
    
    /**
     * Check if welcome message is displayed
     */
    public boolean isWelcomeMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(welcomeMessage);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Complete login (combines all steps)
     */
    public void login(String email, String password) {
        logger.info("Logging in with email: " + email);
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
}
```

**Key Points**:
- **@FindBy**: Tells Selenium how to find elements on the page
- **private WebElement**: Elements are private (encapsulation)
- **public methods**: Actions you can perform on the page
- **extends BasePage**: Inherits common methods like click(), enterText()

**How to find element locators**:
1. Open your application in Chrome
2. Right-click on element → **Inspect**
3. In DevTools, right-click on HTML → **Copy** → **Copy selector** or **Copy XPath**

---

### Step 3: Create Step Definitions

**Location**: `src/test/java/com/company/stepDefinitions/LoginSteps.java`

**Create new file**:

```java
package com.company.stepDefinitions;

import com.company.context.TestContext;
import com.company.pages.LoginPage;
import com.company.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * LoginSteps - Implements the steps from login.feature file
 */
public class LoginSteps {

    // ========== STEP 3A: Declare Variables ==========
    
    private WebDriver driver;
    private TestContext testContext;
    private LoginPage loginPage;
    private ConfigReader configReader;

    // ========== STEP 3B: Constructor (Dependency Injection) ==========
    
    /**
     * Constructor - TestContext is automatically injected by Cucumber
     */
    public LoginSteps(TestContext testContext) {
        this.testContext = testContext;
        this.driver = testContext.getDriver();
        this.loginPage = new LoginPage(driver);
        this.configReader = new ConfigReader();
    }

    // ========== STEP 3C: Implement Each Step ==========
    
    @Given("the login page is open")
    public void the_login_page_is_open() {
        logger.info("Opening login page");
        
        // Navigate to login page
        String baseUrl = configReader.getBaseUrl();
        driver.get(baseUrl + "/login");
        
        // Verify page loaded
        Assert.assertTrue("Login page should be displayed", 
            loginPage.isLoginPageDisplayed());
    }

    @When("I enter email {string}")
    public void i_enter_email(String email) {
        logger.info("Entering email: " + email);
        loginPage.enterEmail(email);
        
        // Store email for later use
        testContext.setContext("USER_EMAIL", email);
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        logger.info("Entering password");
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        logger.info("Clicking login button");
        loginPage.clickLoginButton();
    }

    @Then("I should be redirected to the dashboard")
    public void i_should_be_redirected_to_the_dashboard() {
        logger.info("Verifying redirect to dashboard");
        
        // Wait for page to load
        loginPage.waitHelper.hardWait(2000);
        
        // Check URL contains "dashboard"
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Should redirect to dashboard", 
            currentUrl.contains("/dashboard"));
    }

    @Then("I should see welcome message")
    public void i_should_see_welcome_message() {
        logger.info("Verifying welcome message");
        Assert.assertTrue("Welcome message should be displayed", 
            loginPage.isWelcomeMessageDisplayed());
    }

    @Then("I should see error message {string}")
    public void i_should_see_error_message(String expectedMessage) {
        logger.info("Verifying error message: " + expectedMessage);
        
        // Wait for error to appear
        loginPage.waitHelper.hardWait(1000);
        
        // Get actual error message
        String actualMessage = loginPage.getErrorMessage();
        
        // Verify it contains expected text
        Assert.assertTrue("Error message should contain: " + expectedMessage, 
            actualMessage.contains(expectedMessage));
    }

    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        logger.info("Verifying still on login page");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Should remain on login page", 
            currentUrl.contains("/login"));
    }
}
```

**Key Points**:
- **Method names**: Use underscores to match Gherkin step text
- **{string}**: Parameter from Gherkin step
- **Assert**: Verify expected results
- **logger**: Log what's happening (for debugging)

---

### Step 4: Update Test Runner (if needed)

**Location**: `src/test/java/com/company/runners/TestRunner.java`

```java
package com.company.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * TestRunner - Configures and runs Cucumber tests
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        // Where are feature files?
        features = "src/test/resources/features",
        
        // Where are step definitions and hooks?
        glue = { "com.company.stepDefinitions", "com.company.hooks" },
        
        // What reports to generate?
        plugin = {
                "pretty",  // Console output
                "html:target/cucumber-reports/cucumber.html",  // HTML report
                "json:target/cucumber-reports/cucumber.json",  // JSON report
        },
        
        // Make console output readable
        monochrome = true,
        
        // false = run tests, true = just check if steps are defined
        dryRun = false,
        
        // Which tests to run? (change this to run different tests)
        tags = "@smoke"
)
public class TestRunner {
    // This class is empty - Cucumber uses annotations to run tests
}
```

**Tag Options**:
- `@smoke` - Run only smoke tests
- `@login` - Run all login tests
- `@smoke and @login` - Run tests with BOTH tags
- `not @negative` - Exclude negative tests
- `@smoke or @regression` - Run tests with EITHER tag

---

### Step 5: Run Your Test!

**Option 1: From IDE**
1. Right-click on `TestRunner.java`
2. Click **Run 'TestRunner'**
3. Watch the browser open and test execute!

**Option 2: From Command Line**
```bash
mvn clean test
```

**Option 3: Run Specific Tags**
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

**✅ Success**: You'll see "BUILD SUCCESS" and browser will execute the test

**❌ Failure**: Check the error message and see [Common Errors](#common-errors-and-solutions) section

---

## Best Practices

### 1. Page Object Design

✅ **DO THIS**:
```java
// Good: Descriptive method names
public void clickLoginButton() { }
public boolean isErrorDisplayed() { }
public String getErrorMessage() { return getText(errorMessage); }

// Good: Combine related actions
public void login(String email, String password) {
    enterEmail(email);
    enterPassword(password);
    clickLoginButton();
}
```

❌ **DON'T DO THIS**:
```java
// Bad: Exposing WebElements
public WebElement getLoginButton() { }  // Never expose elements!

// Bad: Assertions in Page Objects
public void verifyLogin() {
    Assert.assertTrue(isDashboardDisplayed());  // No assertions in pages!
}

// Bad: Interacting with driver directly
public void clickButton() {
    driver.findElement(By.id("btn")).click();  // Use page elements instead!
}
```

---

### 2. Step Definitions

✅ **DO THIS**:
```java
// Good: Simple, single responsibility
@When("I enter email {string}")
public void i_enter_email(String email) {
    loginPage.enterEmail(email);
}

// Good: Store data for later use
@When("I create user with email {string}")
public void i_create_user(String email) {
    testContext.setContext("USER_EMAIL", email);
    userPage.createUser(email);
}
```

❌ **DON'T DO THIS**:
```java
// Bad: Business logic in step definitions
@When("I login")
public void i_login() {
    driver.findElement(By.id("email")).sendKeys("test@test.com");
    driver.findElement(By.id("password")).sendKeys("password");
    // Don't interact with driver directly!
}

// Bad: Hard-coded values
@When("I login")
public void i_login() {
    loginPage.login("test@test.com", "password");  // Use parameters instead!
}
```

---

### 3. Locator Strategy (Priority Order)

**1. ID (Best - Most Reliable)**
```java
@FindBy(id = "email")
private WebElement emailInput;
```

**2. Name (Good for Form Fields)**
```java
@FindBy(name = "username")
private WebElement usernameField;
```

**3. CSS Selector (Fast and Flexible)**
```java
@FindBy(css = "button[type='submit']")
private WebElement submitButton;

@FindBy(css = ".error-message")
private WebElement errorMsg;
```

**4. XPath (Last Resort - Slower)**
```java
// Good XPath: Multiple attributes
@FindBy(xpath = "//input[@placeholder='Email' or @name='email']")
private WebElement emailField;

// Good XPath: Text-based
@FindBy(xpath = "//button[contains(text(),'Login')]")
private WebElement loginBtn;

// Bad XPath: Absolute path (very brittle!)
@FindBy(xpath = "/html/body/div[1]/div[2]/form/input[1]")  // DON'T DO THIS!
```

---

### 4. Wait Strategies

✅ **DO THIS**:
```java
// Good: Explicit waits
waitHelper.waitForElementVisible(element);
waitHelper.waitForElementClickable(button);

// Good: Custom timeout for slow operations
waitHelper.waitForElement(element, 30);

// Good: Check before interacting
if (waitHelper.isElementDisplayed(element)) {
    click(element);
}
```

❌ **DON'T DO THIS**:
```java
// Bad: Hard waits everywhere
Thread.sleep(5000);  // Avoid this!

// Bad: No waits at all
element.click();  // May fail if element not ready!
```

---

## Running Tests

### 1. Run All Tests
```bash
mvn clean test
```

### 2. Run Specific Tags
```bash
# Run smoke tests only
mvn clean test -Dcucumber.filter.tags="@smoke"

# Run login tests only
mvn clean test -Dcucumber.filter.tags="@login"

# Run smoke AND login tests
mvn clean test -Dcucumber.filter.tags="@smoke and @login"

# Run all EXCEPT negative tests
mvn clean test -Dcucumber.filter.tags="not @negative"

# Run smoke OR regression tests
mvn clean test -Dcucumber.filter.tags="@smoke or @regression"
```

### 3. Run in Different Browsers

Edit `config.properties`:
```properties
browser=chrome    # or firefox, edge
```

### 4. Run in Headless Mode (No Browser Window)

Edit `config.properties`:
```properties
headless=true
```

### 5. Run from IDE

**Run Entire Test Suite:**
1. Right-click `TestRunner.java`
2. Select **Run 'TestRunner'**

**Run Single Feature File:**
1. Right-click on `.feature` file
2. Select **Run Feature: <name>**

**Run Single Scenario:**
1. Open feature file
2. Click green arrow next to scenario
3. Select **Run Scenario**

**Debug Mode:**
1. Set breakpoints in step definitions
2. Right-click → **Debug** instead of Run

---

## Common Errors and Solutions

### Error 1: NoSuchElementException

**Error Message:**
```
org.openqa.selenium.NoSuchElementException: no such element: Unable to locate element
```

**What it means:** Selenium cannot find the element on the page.

**Possible Causes & Solutions:**

**Cause 1: Element not loaded yet**
```java
// ❌ Problem
element.click();

// ✅ Solution: Add explicit wait
waitHelper.waitForElementVisible(element);
element.click();
```

**Cause 2: Wrong locator**
```java
// ❌ Problem: Incorrect ID
@FindBy(id = "email-input")  // But actual ID is "email"

// ✅ Solution: Verify locator
// 1. Open browser DevTools (F12)
// 2. Inspect element
// 3. Copy correct ID/XPath
@FindBy(id = "email")
```

**Cause 3: Element in iframe**
```java
// ✅ Solution: Switch to iframe first
driver.switchTo().frame("iframeName");
element.click();
driver.switchTo().defaultContent();  // Switch back
```

**Cause 4: Element not visible**
```java
// ✅ Solution: Scroll to element
scrollToElement(element);
waitHelper.waitForElementVisible(element);
element.click();
```

---

### Error 2: ElementNotInteractableException

**Error Message:**
```
org.openqa.selenium.ElementNotInteractableException: element not interactable
```

**What it means:** Element exists but cannot be clicked/interacted with.

**Possible Causes & Solutions:**

**Cause 1: Element not clickable yet**
```java
// ❌ Problem
element.click();

// ✅ Solution: Wait for clickable
waitHelper.waitForElementClickable(element);
element.click();
```

**Cause 2: Element covered by another element**
```java
// ✅ Solution 1: Wait for overlay to disappear
waitHelper.waitForElementInvisible(overlay);
element.click();

// ✅ Solution 2: Scroll to element
scrollToElement(element);
element.click();

// ✅ Solution 3: Use JavaScript click
clickUsingJS(element);
```

**Cause 3: Element is disabled**
```java
// ✅ Solution: Check if enabled first
if (element.isEnabled()) {
    element.click();
} else {
    logger.warn("Element is disabled");
}
```

---

### Error 3: StaleElementReferenceException

**Error Message:**
```
org.openqa.selenium.StaleElementReferenceException: stale element reference
```

**What it means:** Element was found but page refreshed/changed, making reference invalid.

**Possible Causes & Solutions:**

**Cause 1: Page refreshed after finding element**
```java
// ❌ Problem
WebElement element = driver.findElement(By.id("btn"));
driver.navigate().refresh();
element.click();  // Fails! Element reference is stale

// ✅ Solution: Re-find element after refresh
driver.navigate().refresh();
WebElement element = driver.findElement(By.id("btn"));
element.click();
```

**Cause 2: DOM updated dynamically**
```java
// ✅ Solution: Use @FindBy (automatically re-finds)
@FindBy(id = "dynamicElement")
private WebElement element;

// When you call element.click(), it re-finds automatically
public void clickElement() {
    waitHelper.waitForElementClickable(element);
    element.click();  // Element is re-found each time
}
```

**Cause 3: Element removed and re-added**
```java
// ✅ Solution: Wait and re-find
waitHelper.hardWait(1000);
waitHelper.waitForElementVisible(element);
element.click();
```

---

### Error 4: TimeoutException

**Error Message:**
```
org.openqa.selenium.TimeoutException: Expected condition failed: waiting for visibility of element
```

**What it means:** Element didn't appear within the wait time.

**Possible Causes & Solutions:**

**Cause 1: Element takes longer to load**
```java
// ❌ Problem: Default timeout too short
waitHelper.waitForElementVisible(element);  // Times out at 20 seconds

// ✅ Solution: Increase timeout
waitHelper.waitForElement(element, 60);  // Wait up to 60 seconds
```

**Cause 2: Wrong locator**
```java
// ✅ Solution: Verify element exists
// 1. Open browser manually
// 2. Navigate to page
// 3. Check if element is there
// 4. Verify locator in DevTools
```

**Cause 3: Element never appears (application issue)**
```java
// ✅ Solution: Add conditional check
try {
    waitHelper.waitForElement(element, 30);
    element.click();
} catch (TimeoutException e) {
    logger.error("Element did not appear: " + e.getMessage());
    Assert.fail("Element not found within timeout");
}
```

**Cause 4: Slow network/application**
```java
// ✅ Solution: Increase global timeouts in config.properties
explicit.wait=30
page.load.timeout=60
```

---

### Error 5: WebDriverException

**Error Message:**
```
org.openqa.selenium.WebDriverException: unknown error: cannot find Chrome binary
```

**What it means:** Browser or driver not found.

**Solutions:**

**Solution 1: Browser not installed**
```bash
# Install Chrome/Firefox/Edge
# Download from official websites
```

**Solution 2: Driver not downloaded**
```java
// ✅ Framework uses WebDriverManager (automatic)
// But if it fails, manually download:
// Chrome: https://chromedriver.chromium.org/
// Firefox: https://github.com/mozilla/geckodriver/releases
// Edge: https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/

// Set path manually:
System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
```

**Solution 3: Version mismatch**
```bash
# Check Chrome version: chrome://version/
# Download matching ChromeDriver version
# Or update WebDriverManager:
mvn clean install -U
```

**Solution 4: Permission issues**
```bash
# On Linux/Mac, make driver executable:
chmod +x chromedriver
```

---

### Error 6: NullPointerException

**Error Message:**
```
java.lang.NullPointerException
```

**What it means:** Trying to use something that doesn't exist (null).

**Possible Causes & Solutions:**

**Cause 1: Element not initialized**
```java
// ❌ Problem
@FindBy(id = "email")
private WebElement emailInput;

// Constructor missing PageFactory.initElements()
public LoginPage(WebDriver driver) {
    super(driver);
    // Missing: PageFactory.initElements(driver, this);
}

// ✅ Solution: Initialize in constructor
public LoginPage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);  // Add this!
}
```

**Cause 2: Driver not initialized**
```java
// ❌ Problem
WebDriver driver = null;
driver.get("https://example.com");  // NullPointerException!

// ✅ Solution: Initialize driver first
WebDriver driver = DriverManager.getDriver();
driver.get("https://example.com");
```

**Cause 3: Context data not set**
```java
// ❌ Problem
String email = testContext.getContextAsString("USER_EMAIL");  // Returns null
email.contains("@");  // NullPointerException!

// ✅ Solution: Check if null first
String email = testContext.getContextAsString("USER_EMAIL");
if (email != null) {
    email.contains("@");
} else {
    logger.warn("Email not found in context");
}
```

---

### Error 7: InvalidSelectorException

**Error Message:**
```
org.openqa.selenium.InvalidSelectorException: invalid selector: Unable to locate an element
```

**What it means:** XPath or CSS selector syntax is wrong.

**Solutions:**

**Problem: Invalid XPath syntax**
```java
// ❌ Problem: Missing quotes
@FindBy(xpath = "//button[text()=Login]")  // Wrong!

// ✅ Solution: Add quotes around text
@FindBy(xpath = "//button[text()='Login']")  // Correct!
```

**Problem: Invalid CSS syntax**
```java
// ❌ Problem: Wrong syntax
@FindBy(css = "button[type=submit]")  // Missing quotes

// ✅ Solution: Add quotes
@FindBy(css = "button[type='submit']")  // Correct!
```

**Validation: Test selector in browser**
```javascript
// In browser console (F12):
// For XPath:
$x("//button[text()='Login']")

// For CSS:
$$("button[type='submit']")

// Should return elements if selector is correct
```

---

### Error 8: SessionNotCreatedException

**Error Message:**
```
org.openqa.selenium.SessionNotCreatedException: session not created: This version of ChromeDriver only supports Chrome version X
```

**What it means:** Browser and driver versions don't match.

**Solutions:**

**Solution 1: Update WebDriverManager**
```bash
# Update dependencies
mvn clean install -U

# Or update version in pom.xml
<webdrivermanager.version>5.6.2</webdrivermanager.version>
```

**Solution 2: Update browser**
```bash
# Update Chrome/Firefox/Edge to latest version
# Chrome: Settings → About Chrome → Auto-updates
```

**Solution 3: Specify driver version**
```java
// In DriverManager.java
WebDriverManager.chromedriver().driverVersion("120.0.6099.109").setup();
```

---

### Error 9: ElementClickInterceptedException

**Error Message:**
```
org.openqa.selenium.ElementClickInterceptedException: element click intercepted
```

**What it means:** Another element is covering the element you want to click.

**Solutions:**

**Solution 1: Wait for overlay to disappear**
```java
// Wait for loading spinner/overlay
waitHelper.waitForElementInvisible(loadingSpinner);
element.click();
```

**Solution 2: Scroll to element**
```java
scrollToElement(element);
waitHelper.waitForElementClickable(element);
element.click();
```

**Solution 3: Use JavaScript click**
```java
clickUsingJS(element);
```

**Solution 4: Close popup/modal**
```java
// Close any popups first
if (waitHelper.isElementDisplayed(closeButton)) {
    click(closeButton);
}
element.click();
```

---

### Error 10: FileNotFoundException (config.properties)

**Error Message:**
```
java.io.FileNotFoundException: src/test/resources/config/config.properties
```

**What it means:** Configuration file not found.

**Solutions:**

**Solution 1: Create missing file**
```bash
# Create directory
mkdir -p src/test/resources/config

# Create file
touch src/test/resources/config/config.properties
```

**Solution 2: Check file path**
```java
// Verify path in ConfigReader.java
private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";

// Make sure this matches your actual file location
```

**Solution 3: Refresh IDE**
```
In IntelliJ: File → Invalidate Caches → Invalidate and Restart
```

---

### Error 11: BUILD FAILURE (Maven)

**Error Message:**
```
[ERROR] Failed to execute goal ... BUILD FAILURE
```

**Possible Causes & Solutions:**

**Cause 1: Java version mismatch**
```bash
# Check Java version
java -version

# Should be 11 or higher
# If not, install correct version and set JAVA_HOME
```

**Cause 2: Dependency download failed**
```bash
# Delete .m2 repository and retry
rm -rf ~/.m2/repository  # Linux/Mac
# or
rmdir /s %USERPROFILE%\.m2\repository  # Windows

# Then run:
mvn clean install -U
```

**Cause 3: Compilation errors**
```bash
# Check console for specific errors
# Fix syntax errors in Java files
# Then run:
mvn clean compile
```

**Cause 4: Port already in use**
```bash
# If running tests in parallel
# Kill existing Java processes
# Windows: taskkill /F /IM java.exe
# Linux/Mac: pkill java
```

---

### Error 12: Tests Pass Locally but Fail in CI/CD

**Possible Causes & Solutions:**

**Cause 1: Headless mode issues**
```properties
# In config.properties
headless=true

# Some elements behave differently in headless mode
# Use JavaScript actions if needed
```

**Cause 2: Timing issues**
```properties
# Increase timeouts for CI environment
implicit.wait=15
explicit.wait=30
page.load.timeout=60
```

**Cause 3: Screen resolution**
```java
// Set specific window size
driver.manage().window().setSize(new Dimension(1920, 1080));
```

**Cause 4: Missing dependencies in CI**
```bash
# Install browser in CI pipeline
# For Chrome:
apt-get install -y chromium-browser

# For Firefox:
apt-get install -y firefox
```

---

## Troubleshooting Guide

### General Debugging Steps

**Step 1: Check Logs**
```
Look in: logs/application.log
Check for: ERROR or WARN messages
```

**Step 2: Take Screenshots**
```java
// Add in step definition
ScreenshotHelper.takeScreenshot(driver, "debug_screenshot");
// Check: target/screenshots/
```

**Step 3: Add Debug Logging**
```java
// Add before failing step
logger.info("Current URL: " + driver.getCurrentUrl());
logger.info("Element displayed: " + element.isDisplayed());
logger.info("Element enabled: " + element.isEnabled());
System.out.println("Debug: Element text = " + element.getText());
```

**Step 4: Pause Execution**
```java
// Add temporary pause to see what's happening
waitHelper.hardWait(5000);  // 5 seconds
// Remove after debugging!
```

**Step 5: Run in Debug Mode**
```
1. Set breakpoint in step definition
2. Right-click → Debug
3. Step through code line by line
4. Inspect variables
```

---

### Quick Fixes Checklist

When test fails, try these in order:

- [ ] Add explicit wait before action
- [ ] Verify element locator in browser DevTools
- [ ] Check if element is in iframe
- [ ] Scroll to element before clicking
- [ ] Use JavaScript click instead of normal click
- [ ] Increase timeout in config.properties
- [ ] Check for overlays/popups covering element
- [ ] Verify element is enabled and visible
- [ ] Check browser console for JavaScript errors
- [ ] Run test in non-headless mode to see what's happening

---

## FAQ

### Q1: How do I find element locators?

**Answer:**
1. Open application in Chrome
2. Right-click element → **Inspect** (or press F12)
3. In DevTools, right-click on HTML element
4. Select **Copy** → **Copy selector** (for CSS) or **Copy XPath**
5. Test in console:
   ```javascript
   // For CSS:
   $$("your-css-selector")
   
   // For XPath:
   $x("your-xpath")
   ```
6. If it returns elements, your locator is correct!

---

### Q2: How do I handle dropdowns?

**Answer:**
```java
// Use DropdownHelper utility
DropdownHelper dropdown = new DropdownHelper(driver);

// Select by visible text
dropdown.selectByVisibleText(dropdownElement, "Option 1");

// Select by value attribute
dropdown.selectByValue(dropdownElement, "value1");

// Select by index (0-based)
dropdown.selectByIndex(dropdownElement, 0);

// Get selected option
String selected = dropdown.getSelectedOption(dropdownElement);
```

---

### Q3: How do I handle alerts/popups?

**Answer:**
```java
// Use AlertHelper utility
AlertHelper alertHelper = new AlertHelper(driver);

// Accept alert (click OK)
alertHelper.acceptAlert();

// Dismiss alert (click Cancel)
alertHelper.dismissAlert();

// Get alert text
String alertText = alertHelper.getAlertText();

// Type in alert (for prompt)
alertHelper.sendKeysToAlert("text");
```

---

### Q4: How do I upload files?

**Answer:**
```java
// Method 1: Direct sendKeys
@FindBy(id = "fileInput")
private WebElement fileInput;

public void uploadFile(String filePath) {
    fileInput.sendKeys("C:\\path\\to\\file.pdf");
}

// Method 2: Use FileUploadHelper
FileUploadHelper fileHelper = new FileUploadHelper(driver);
fileHelper.uploadFile(fileInputElement, "C:\\path\\to\\file.pdf");
```

---

### Q5: How do I handle multiple windows/tabs?

**Answer:**
```java
// Get current window handle
String mainWindow = driver.getWindowHandle();

// Click link that opens new window
element.click();

// Switch to new window
Set<String> allWindows = driver.getWindowHandles();
for (String window : allWindows) {
    if (!window.equals(mainWindow)) {
        driver.switchTo().window(window);
        break;
    }
}

// Do something in new window
// ...

// Close new window and switch back
driver.close();
driver.switchTo().window(mainWindow);
```

---

### Q6: How do I generate random test data?

**Answer:**
```java
// Use RandomDataGenerator utility
RandomDataGenerator dataGen = new RandomDataGenerator();

String email = dataGen.generateEmail();
String firstName = dataGen.generateFirstName();
String lastName = dataGen.generateLastName();
String phone = dataGen.generatePhoneNumber();
String password = dataGen.generatePassword();

// Or use timestamp for unique data
String uniqueEmail = "test_" + System.currentTimeMillis() + "@example.com";
```

---

### Q7: How do I run tests in parallel?

**Answer:**
```xml
<!-- Add to pom.xml in surefire plugin -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>3</threadCount>
    </configuration>
</plugin>
```

**Note:** Make sure your tests are independent and don't share data!

---

### Q8: How do I wait for page to load completely?

**Answer:**
```java
// Method 1: Wait for specific element
waitHelper.waitForElementVisible(pageLoadedIndicator);

// Method 2: Wait for page load using JavaScript
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("return document.readyState").equals("complete");

// Method 3: Wait for AJAX calls to complete
waitHelper.hardWait(2000);  // Simple but not recommended

// Method 4: Wait for loading spinner to disappear
waitHelper.waitForElementInvisible(loadingSpinner);
```

---

### Q9: How do I debug a failing test?

**Answer:**
1. **Run in non-headless mode** (set `headless=false`)
2. **Add breakpoints** in step definitions
3. **Run in Debug mode** (right-click → Debug)
4. **Add logging**:
   ```java
   logger.info("Current URL: " + driver.getCurrentUrl());
   logger.info("Element displayed: " + element.isDisplayed());
   ```
5. **Take screenshots**:
   ```java
   ScreenshotHelper.takeScreenshot(driver, "debug");
   ```
6. **Pause execution**:
   ```java
   waitHelper.hardWait(5000);  // See what's happening
   ```
7. **Check browser console** for JavaScript errors

---

### Q10: How do I handle dynamic elements?

**Answer:**
```java
// Use dynamic XPath with variables
String buttonText = "Submit";
String xpath = String.format("//button[text()='%s']", buttonText);
WebElement button = driver.findElement(By.xpath(xpath));

// Or use contains() for partial matches
@FindBy(xpath = "//div[contains(@id,'dynamic-')]")
private WebElement dynamicElement;

// Wait for element to appear
waitHelper.waitForElement(dynamicElement, 30);
```

---

## Quick Reference Cheat Sheet

### Common Annotations
```java
@FindBy(id = "id")              // Find by ID
@FindBy(name = "name")          // Find by name
@FindBy(xpath = "//xpath")      // Find by XPath
@FindBy(css = ".class")         // Find by CSS

@Given("step text")             // Given step
@When("step text")              // When step
@Then("step text")              // Then step
@Before                         // Before each scenario
@After                          // After each scenario
```

### Common Assertions
```java
Assert.assertTrue(condition);
Assert.assertFalse(condition);
Assert.assertEquals(expected, actual);
Assert.assertNotNull(object);
Assert.assertTrue("Message", condition);
```

### Common Wait Methods
```java
waitHelper.waitForElementVisible(element);
waitHelper.waitForElementClickable(element);
waitHelper.isElementDisplayed(element);
waitHelper.waitForElement(element, 30);
```

### Common Page Methods
```java
click(element);
enterText(element, "text");
getText(element);
isDisplayed(element);
scrollToElement(element);
clickUsingJS(element);
```

---

## Conclusion

🎉 **Congratulations!** You now know how to:
- ✅ Understand framework architecture
- ✅ Create test scenarios in Gherkin
- ✅ Build Page Objects
- ✅ Implement Step Definitions
- ✅ Run and debug tests
- ✅ Fix common errors

### Next Steps:
1. Practice creating simple tests
2. Explore existing tests in the framework
3. Read utility class code to learn helper methods
4. Start contributing to test automation!

### Additional Resources:
- **Selenium Docs**: https://www.selenium.dev/documentation/
- **Cucumber Docs**: https://cucumber.io/docs/cucumber/
- **Maven Docs**: https://maven.apache.org/guides/
- **Java Tutorial**: https://docs.oracle.com/javase/tutorial/

**Happy Testing! 🚀**
