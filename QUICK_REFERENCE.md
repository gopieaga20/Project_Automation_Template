# Selenium Cucumber Automation - Quick Reference Guide

## 📋 Table of Contents
- [Maven Commands](#maven-commands)
- [Running Tests](#running-tests)
- [Common Code Snippets](#common-code-snippets)
- [Locator Strategies](#locator-strategies)
- [Wait Strategies](#wait-strategies)
- [Assertions](#assertions)
- [Error Quick Fixes](#error-quick-fixes)
- [Troubleshooting](#troubleshooting)

---

## Maven Commands

```bash
# Clean and compile
mvn clean compile

# Run all tests
mvn clean test

# Run specific tags
mvn clean test -Dcucumber.filter.tags="@smoke"
mvn clean test -Dcucumber.filter.tags="@login"
mvn clean test -Dcucumber.filter.tags="@smoke and @login"
mvn clean test -Dcucumber.filter.tags="not @negative"

# Skip tests during build
mvn clean install -DskipTests

# Update dependencies
mvn clean install -U

# Generate reports
mvn verify
```

---

## Running Tests

### From Command Line
```bash
# Run all tests
mvn clean test

# Run smoke tests only
mvn clean test -Dcucumber.filter.tags="@smoke"

# Run in headless mode (edit config.properties first: headless=true)
mvn clean test
```

### From IntelliJ IDEA
1. **Run TestRunner**: Right-click `TestRunner.java` → Run
2. **Run Feature File**: Right-click `.feature` file → Run
3. **Run Scenario**: Click green arrow next to scenario → Run
4. **Debug Mode**: Right-click → Debug (set breakpoints first)

### Change Browser
Edit `src/test/resources/config/config.properties`:
```properties
browser=chrome    # Options: chrome, firefox, edge
headless=false    # true = no browser window
```

---

## Common Code Snippets

### 1. Create New Page Object

```java
package com.company.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YourPage extends BasePage {

    // ========== Elements ==========
    @FindBy(id = "elementId")
    private WebElement element;
    
    @FindBy(xpath = "//button[text()='Submit']")
    private WebElement submitButton;

    // ========== Constructor ==========
    public YourPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("YourPage initialized");
    }

    // ========== Methods ==========
    public void clickElement() {
        logger.info("Clicking element");
        waitHelper.waitForElementClickable(element);
        click(element);
    }

    public boolean isElementDisplayed() {
        return waitHelper.isElementDisplayed(element);
    }
    
    public String getElementText() {
        waitHelper.waitForElementVisible(element);
        return getText(element);
    }
}
```

### 2. Create New Step Definition

```java
package com.company.stepDefinitions;

import com.company.context.TestContext;
import com.company.pages.YourPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class YourSteps {

    private WebDriver driver;
    private TestContext testContext;
    private YourPage yourPage;

    // Constructor with dependency injection
    public YourSteps(TestContext testContext) {
        this.testContext = testContext;
        this.driver = testContext.getDriver();
        this.yourPage = new YourPage(driver);
    }

    @Given("step text here")
    public void step_text_here() {
        logger.info("Executing step");
        // Implementation
    }

    @When("I perform action {string}")
    public void i_perform_action(String parameter) {
        logger.info("Performing action: " + parameter);
        yourPage.clickElement();
        testContext.setContext("ACTION", parameter);
    }

    @Then("I should see result")
    public void i_should_see_result() {
        logger.info("Verifying result");
        Assert.assertTrue("Element should be displayed", 
            yourPage.isElementDisplayed());
    }
}
```

### 3. Create New Feature File

```gherkin
Feature: Feature Name
  As a user type
  I want to perform action
  So that I can achieve goal

  Background:
    Given common precondition for all scenarios

  @smoke @module
  Scenario: Positive test case
    Given precondition
    When I perform action
    Then I should see expected result

  @module @negative
  Scenario: Negative test case
    Given precondition
    When I perform invalid action
    Then I should see error message

  @module
  Scenario Outline: Data-driven test
    When I enter "<input>"
    Then I should see "<output>"

    Examples:
      | input  | output  |
      | test1  | result1 |
      | test2  | result2 |
```

---

## Locator Strategies

### Priority Order (Best to Worst)
1. **ID** - Most reliable and fast
2. **Name** - Good for form fields
3. **CSS Selector** - Fast and flexible
4. **XPath** - Last resort, slower

### Examples

```java
// ========== By ID (BEST) ==========
@FindBy(id = "email")
private WebElement emailInput;

// ========== By Name ==========
@FindBy(name = "password")
private WebElement passwordInput;

// ========== By CSS Selector ==========
@FindBy(css = "button[type='submit']")
private WebElement submitButton;

@FindBy(css = ".error-message")
private WebElement errorMessage;

@FindBy(css = "#loginForm > button")
private WebElement formButton;

// ========== By XPath - Multiple Attributes (Good) ==========
@FindBy(xpath = "//input[@placeholder='Email' or @name='email']")
private WebElement emailField;

// ========== By XPath - Contains Text (Good for buttons) ==========
@FindBy(xpath = "//button[contains(text(),'Login')]")
private WebElement loginButton;

// ========== By XPath - Partial Match ==========
@FindBy(xpath = "//div[contains(@class,'error')]")
private WebElement error;

// ========== By XPath - Following Sibling ==========
@FindBy(xpath = "//label[text()='Email']/following-sibling::input")
private WebElement emailAfterLabel;

// ========== Dynamic Locators ==========
public WebElement getButtonByText(String text) {
    String xpath = String.format("//button[text()='%s']", text);
    return driver.findElement(By.xpath(xpath));
}
```

### ❌ Avoid These

```java
// Bad: Absolute XPath (very brittle!)
@FindBy(xpath = "/html/body/div[1]/div[2]/form/input[1]")  // DON'T!

// Bad: Too generic
@FindBy(xpath = "//div")  // Matches too many elements

// Bad: Index-based (fragile)
@FindBy(xpath = "(//button)[3]")  // Breaks if order changes
```

---

## Wait Strategies

### Explicit Waits (Recommended)

```java
// Wait for element to be visible
waitHelper.waitForElementVisible(element);

// Wait for element to be clickable
waitHelper.waitForElementClickable(element);

// Wait for element to be present in DOM
waitHelper.waitForElementPresent(element);

// Wait for element to disappear
waitHelper.waitForElementInvisible(element);

// Wait for text to be present in element
waitHelper.waitForTextPresent(element, "Expected Text");

// Wait with custom timeout (seconds)
waitHelper.waitForElement(element, 30);

// Check if element is displayed (returns true/false)
boolean isDisplayed = waitHelper.isElementDisplayed(element);
```

### Hard Wait (Use Sparingly!)

```java
// Only when absolutely necessary
waitHelper.hardWait(2000); // 2 seconds

// Better alternatives:
// - Wait for specific element
// - Wait for element to disappear
// - Wait for page load
```

### Implicit Wait (Already Configured)

```properties
# In config.properties
implicit.wait=10
explicit.wait=20
page.load.timeout=30
```

---

## Assertions

### Basic Assertions

```java
// Boolean assertions
Assert.assertTrue("Should be true", condition);
Assert.assertFalse("Should be false", condition);

// Equality assertions
Assert.assertEquals("Should be equal", expected, actual);
Assert.assertNotEquals("Should not be equal", unexpected, actual);

// Null checks
Assert.assertNull("Should be null", object);
Assert.assertNotNull("Should not be null", object);
```

### Common Assertion Patterns

```java
// ========== Verify Element Displayed ==========
Assert.assertTrue("Element should be displayed", 
    page.isElementDisplayed());

// ========== Verify Text Content ==========
String actualText = page.getElementText();
Assert.assertEquals("Text should match", "Expected Text", actualText);

// Or contains:
Assert.assertTrue("Text should contain expected", 
    actualText.contains("Expected"));

// ========== Verify URL Navigation ==========
String currentUrl = driver.getCurrentUrl();
Assert.assertTrue("Should navigate to dashboard", 
    currentUrl.contains("/dashboard"));

// ========== Verify Error Message ==========
String errorMsg = page.getErrorMessage();
Assert.assertTrue("Should show error", 
    errorMsg.contains("Invalid credentials"));

// ========== Verify Element Count ==========
List<WebElement> elements = driver.findElements(By.cssSelector(".item"));
Assert.assertEquals("Should have 5 items", 5, elements.size());
```

---

## TestContext Usage

### Store Data

```java
// Store simple values
testContext.setContext("USER_EMAIL", "test@example.com");
testContext.setContext("USER_ID", 12345);
testContext.setContext("IS_VERIFIED", true);

// Store complex objects
testContext.setContext("USER_OBJECT", userObject);
```

### Retrieve Data

```java
// Get as String
String email = testContext.getContextAsString("USER_EMAIL");

// Get as Integer
Integer userId = testContext.getContextAsInteger("USER_ID");

// Get as Boolean
Boolean isVerified = testContext.getContextAsBoolean("IS_VERIFIED");

// Get as Object (cast to your type)
User user = (User) testContext.getContext("USER_OBJECT");

// Check if exists
if (testContext.containsContext("USER_EMAIL")) {
    // Do something
}

// Clear all data (done automatically after each test)
testContext.clearContext();
```

---

## Configuration

### Read from config.properties

```java
ConfigReader config = new ConfigReader();

// Predefined methods
String baseUrl = config.getBaseUrl();
String browser = config.getBrowser();
String environment = config.getEnvironment();

// Custom properties
String customValue = config.getProperty("test.email");
String withDefault = config.getProperty("key", "defaultValue");
```

### Common Properties

```properties
# Application
base.url=https://your-app.com
environment=dev

# Browser
browser=chrome
headless=false
maximize=true

# Timeouts (seconds)
implicit.wait=10
explicit.wait=20
page.load.timeout=30

# Test Credentials
test.email=test@example.com
test.password=Test@123
```

---

## Error Quick Fixes

### Quick Lookup Table

| Error | Quick Fix |
|-------|-----------|
| `NoSuchElementException` | Add `waitHelper.waitForElementVisible(element)` |
| `ElementNotInteractableException` | Use `waitHelper.waitForElementClickable(element)` |
| `StaleElementReferenceException` | Element auto-refreshes with `@FindBy`, just retry |
| `TimeoutException` | Increase timeout: `waitHelper.waitForElement(element, 60)` |
| `ElementClickInterceptedException` | Use `scrollToElement(element)` or `clickUsingJS(element)` |
| `NullPointerException` | Check if object is null before using |
| `InvalidSelectorException` | Fix XPath/CSS syntax, test in browser console |
| `SessionNotCreatedException` | Update browser or WebDriverManager version |
| `WebDriverException` | Check browser is installed, driver is compatible |
| `FileNotFoundException` | Verify file path exists |

### Common Solutions

```java
// ========== Element Not Found ==========
// Add explicit wait
waitHelper.waitForElementVisible(element);
element.click();

// ========== Element Not Clickable ==========
// Option 1: Wait for clickable
waitHelper.waitForElementClickable(element);
element.click();

// Option 2: Scroll to element
scrollToElement(element);
element.click();

// Option 3: JavaScript click
clickUsingJS(element);

// ========== Stale Element ==========
// Just retry - @FindBy auto-refreshes
waitHelper.waitForElementClickable(element);
element.click();

// ========== Timeout ==========
// Increase timeout
waitHelper.waitForElement(element, 60);

// Or increase global timeout in config.properties
explicit.wait=30

// ========== Element Covered by Overlay ==========
// Wait for overlay to disappear
waitHelper.waitForElementInvisible(loadingSpinner);
element.click();

// ========== Null Pointer ==========
// Check before using
if (element != null && element.isDisplayed()) {
    element.click();
}

// For context data
String email = testContext.getContextAsString("EMAIL");
if (email != null) {
    // Use email
}
```

---

## Troubleshooting

### Debug Checklist

When test fails, try these in order:

1. **Add Explicit Wait**
   ```java
   waitHelper.waitForElementVisible(element);
   ```

2. **Verify Locator**
   - Open browser DevTools (F12)
   - Test selector in console:
     ```javascript
     // For CSS:
     $$("your-css-selector")
     
     // For XPath:
     $x("your-xpath")
     ```

3. **Check if Element in iframe**
   ```java
   driver.switchTo().frame("frameName");
   element.click();
   driver.switchTo().defaultContent();
   ```

4. **Scroll to Element**
   ```java
   scrollToElement(element);
   element.click();
   ```

5. **Use JavaScript Click**
   ```java
   clickUsingJS(element);
   ```

6. **Increase Timeout**
   ```properties
   # In config.properties
   explicit.wait=30
   page.load.timeout=60
   ```

7. **Check for Overlays**
   ```java
   waitHelper.waitForElementInvisible(overlay);
   element.click();
   ```

8. **Run in Non-Headless Mode**
   ```properties
   headless=false
   ```

9. **Add Debug Logging**
   ```java
   logger.info("URL: " + driver.getCurrentUrl());
   logger.info("Displayed: " + element.isDisplayed());
   logger.info("Enabled: " + element.isEnabled());
   ```

10. **Take Screenshot**
    ```java
    ScreenshotHelper.takeScreenshot(driver, "debug");
    ```

---

## Utility Classes Quick Reference

### RandomDataGenerator

```java
RandomDataGenerator dataGen = new RandomDataGenerator();

String email = dataGen.generateEmail();
String firstName = dataGen.generateFirstName();
String lastName = dataGen.generateLastName();
String phone = dataGen.generatePhoneNumber();
String password = dataGen.generatePassword();

// Or use timestamp
String uniqueEmail = "test_" + System.currentTimeMillis() + "@example.com";
```

### DropdownHelper

```java
DropdownHelper dropdown = new DropdownHelper(driver);

// Select by visible text
dropdown.selectByVisibleText(element, "Option 1");

// Select by value attribute
dropdown.selectByValue(element, "value1");

// Select by index (0-based)
dropdown.selectByIndex(element, 0);

// Get selected option
String selected = dropdown.getSelectedOption(element);
```

### AlertHelper

```java
AlertHelper alertHelper = new AlertHelper(driver);

// Accept alert (click OK)
alertHelper.acceptAlert();

// Dismiss alert (click Cancel)
alertHelper.dismissAlert();

// Get alert text
String text = alertHelper.getAlertText();

// Type in alert (for prompt)
alertHelper.sendKeysToAlert("text");
```

### FileUploadHelper

```java
FileUploadHelper fileHelper = new FileUploadHelper(driver);

// Upload file
fileHelper.uploadFile(fileInputElement, "C:\\path\\to\\file.pdf");

// Or direct sendKeys
fileInputElement.sendKeys("C:\\path\\to\\file.pdf");
```

### ScreenshotHelper

```java
// Take screenshot
ScreenshotHelper.takeScreenshot(driver, "screenshot_name");

// Screenshots saved to: target/screenshots/
```

---

## Gherkin Keywords

```gherkin
Feature: High-level description of functionality
  
  Background: Steps that run before EACH scenario
  
  Scenario: Individual test case
  
  Scenario Outline: Parameterized scenario with examples
  
  Examples: Data table for Scenario Outline
  
  Given: Precondition (setup)
  When: Action performed
  Then: Expected result (verification)
  And: Additional step of same type
  But: Negative condition
  
  @tag: For organizing/filtering tests
  
  #: Comment
  
  """: Multi-line string
  
  |: Data table
```

---

## Tags Reference

### Common Tags

```gherkin
@smoke       # Critical/most important tests
@regression  # Full regression suite
@login       # Login functionality
@signup      # Signup functionality
@negative    # Negative test cases
@wip         # Work in progress
@skip        # Skip this test
```

### Tag Combinations

```bash
# Run smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# Run smoke AND login tests
mvn test -Dcucumber.filter.tags="@smoke and @login"

# Run all EXCEPT negative
mvn test -Dcucumber.filter.tags="not @negative"

# Run smoke OR regression
mvn test -Dcucumber.filter.tags="@smoke or @regression"

# Complex: (smoke OR regression) AND NOT wip
mvn test -Dcucumber.filter.tags="(@smoke or @regression) and not @wip"
```

---

## Report Locations

```
target/
├── cucumber-reports/
│   ├── cucumber.html      # HTML report (open in browser)
│   ├── cucumber.json      # JSON report
│   └── cucumber.xml       # XML report
├── extent-reports/        # Enhanced HTML reports
└── screenshots/           # Failure screenshots

logs/
└── application.log        # Application logs
```

### View Reports

```bash
# After running tests, open in browser:
# Windows:
start target/cucumber-reports/cucumber.html

# Mac:
open target/cucumber-reports/cucumber.html

# Linux:
xdg-open target/cucumber-reports/cucumber.html
```

---

## Keyboard Shortcuts (IntelliJ)

```
Ctrl + Shift + F10    # Run test at cursor
Ctrl + Shift + F9     # Debug test at cursor
Shift + F10           # Run last test
Shift + F9            # Debug last test
Ctrl + F2             # Stop running test

Ctrl + B              # Go to declaration
Ctrl + Alt + B        # Go to implementation
Ctrl + Shift + T      # Create/Go to test

Alt + Enter           # Quick fix/suggestions
Ctrl + Space          # Code completion
Ctrl + /              # Comment/Uncomment line
Ctrl + Shift + /      # Comment/Uncomment block

F8                    # Step over (debug)
F7                    # Step into (debug)
Shift + F8            # Step out (debug)
F9                    # Resume (debug)
```

---

## Best Practices Checklist

### Code Quality
- ✅ Use Page Object Model
- ✅ Use explicit waits, avoid hard waits
- ✅ Use meaningful variable/method names
- ✅ Add logging for important steps
- ✅ Keep step definitions simple
- ✅ Don't put assertions in Page Objects
- ✅ Don't expose WebElements from Page Objects

### Test Design
- ✅ Write independent test scenarios
- ✅ Use tags for test organization
- ✅ Use TestContext for sharing data
- ✅ Generate unique test data
- ✅ Clean up test data after tests

### Locators
- ✅ Use ID when available
- ✅ Use relative XPath, avoid absolute
- ✅ Test locators in browser console
- ✅ Use multiple attributes in XPath

### Debugging
- ✅ Take screenshots on failure
- ✅ Use descriptive assertion messages
- ✅ Add debug logging
- ✅ Run in non-headless mode when debugging

---

## Common Patterns

### Login Pattern

```java
// Page Object
public void login(String email, String password) {
    enterEmail(email);
    enterPassword(password);
    clickLoginButton();
}

// Step Definition
@When("I login with {string} and {string}")
public void i_login(String email, String password) {
    loginPage.login(email, password);
}
```

### Form Fill Pattern

```java
// Page Object
public void fillForm(String name, String email, String phone) {
    enterName(name);
    enterEmail(email);
    enterPhone(phone);
}

// Step Definition
@When("I fill the form with valid data")
public void i_fill_form() {
    String name = RandomDataGenerator.generateName();
    String email = RandomDataGenerator.generateEmail();
    String phone = RandomDataGenerator.generatePhone();
    
    formPage.fillForm(name, email, phone);
    
    // Store for later verification
    testContext.setContext("USER_NAME", name);
    testContext.setContext("USER_EMAIL", email);
}
```

### Verification Pattern

```java
// Page Object
public boolean isSuccessMessageDisplayed() {
    return waitHelper.isElementDisplayed(successMessage);
}

public String getSuccessMessage() {
    waitHelper.waitForElementVisible(successMessage);
    return getText(successMessage);
}

// Step Definition
@Then("I should see success message {string}")
public void i_should_see_success_message(String expectedMsg) {
    Assert.assertTrue("Success message should be displayed",
        page.isSuccessMessageDisplayed());
    
    String actualMsg = page.getSuccessMessage();
    Assert.assertTrue("Message should contain: " + expectedMsg,
        actualMsg.contains(expectedMsg));
}
```

---

## Need More Help?

📖 **Full Tutorial**: See `AUTOMATION_TUTORIAL.md`  
🔍 **Explore Code**: Look at existing tests  
👥 **Ask Team**: Reach out to senior members  
🌐 **Documentation**: 
- [Selenium Docs](https://www.selenium.dev/documentation/)
- [Cucumber Docs](https://cucumber.io/docs/cucumber/)
- [Maven Docs](https://maven.apache.org/guides/)

---

**Last Updated**: December 2025  
**Framework**: Selenium + Cucumber + Java + Maven
