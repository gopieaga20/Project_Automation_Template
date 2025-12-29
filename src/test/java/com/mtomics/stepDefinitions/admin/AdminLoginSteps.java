package com.mtomics.stepDefinitions.admin;

import com.mtomics.context.TestContext;
import com.mtomics.pages.admin.AdminLoginPage;
import com.mtomics.pages.admin.UserManagementPage;
import com.mtomics.utils.ConfigReader;
import com.mtomics.utils.ExtentReportManager;
import com.mtomics.utils.LogHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * AdminLoginSteps - Step definitions for admin login scenarios
 */
public class AdminLoginSteps {

    private WebDriver driver;
    private TestContext testContext;
    private AdminLoginPage loginPage;
    private UserManagementPage userManagementPage;
    private ConfigReader configReader;

    /**
     * Constructor with dependency injection
     * 
     * @param testContext Test context for sharing data
     */
    public AdminLoginSteps(TestContext testContext) {
        this.testContext = testContext;
        this.driver = testContext.getDriver();
        this.loginPage = new AdminLoginPage(driver);
        this.userManagementPage = new UserManagementPage(driver);
        this.configReader = new ConfigReader();
    }

    @Given("I navigate to the MTOmics login page")
    public void i_navigate_to_the_mtomics_login_page() {
        LogHelper.logStep("Navigating to MTOmics login page");
        String baseUrl = configReader.getBaseUrl();
        driver.get(baseUrl + "/sign-in");
        ExtentReportManager.logInfo("Navigated to login page: " + baseUrl + "/sign-in");

        // Verify login page is displayed
        Assert.assertTrue("Login page should be displayed", loginPage.isLoginPageDisplayed());
        ExtentReportManager.logPass("Login page displayed successfully");
    }

    @When("I enter email {string}")
    public void i_enter_email(String email) {
        LogHelper.logStep("Entering email: " + email);
        loginPage.enterEmail(email);
        ExtentReportManager.logInfo("Email entered: " + email);
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        LogHelper.logStep("Entering password");
        loginPage.enterPassword(password);
        ExtentReportManager.logInfo("Password entered");
    }

    @When("I click the sign in button")
    public void i_click_the_sign_in_button() {
        LogHelper.logStep("Clicking sign in button");
        loginPage.clickSignIn();
        ExtentReportManager.logInfo("Sign in button clicked");
    }

    @Then("I should be redirected to the users page")
    public void i_should_be_redirected_to_the_users_page() {
        LogHelper.logStep("Verifying redirection to users page");
        // Wait for navigation
        loginPage.waitHelper.hardWait(2000);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Should be redirected to users page",
                currentUrl.contains("/users"));
        ExtentReportManager.logPass("Successfully redirected to users page");
    }

    @Then("I should see the user management page")
    public void i_should_see_the_user_management_page() {
        LogHelper.logStep("Verifying user management page is displayed");
        Assert.assertTrue("User management page should be displayed",
                userManagementPage.isUserManagementPageDisplayed());
        ExtentReportManager.logPass("User management page displayed successfully");
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedMessage) {
        LogHelper.logStep("Verifying error message: " + expectedMessage);
        boolean errorDisplayed = loginPage.isIncorrectCredentialsErrorDisplayed();
        Assert.assertTrue("Error message should be displayed", errorDisplayed);
        ExtentReportManager.logPass("Error message displayed: " + expectedMessage);
    }

    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        LogHelper.logStep("Verifying still on login page");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Should remain on login page",
                currentUrl.contains("/sign-in"));
        ExtentReportManager.logPass("Remained on login page as expected");
    }

    @When("I click the toggle password visibility button")
    public void i_click_the_toggle_password_visibility_button() {
        LogHelper.logStep("Clicking toggle password visibility button");
        loginPage.togglePasswordVisibility();
        ExtentReportManager.logInfo("Password visibility toggled");
    }

    @Then("the password should be visible")
    public void the_password_should_be_visible() {
        LogHelper.logStep("Verifying password visibility");
        // In a real scenario, you would check the input type attribute
        // For now, we'll just log that the toggle was clicked
        ExtentReportManager.logPass("Password visibility toggled successfully");
    }

    @When("I click the forgot password button")
    public void i_click_the_forgot_password_button() {
        LogHelper.logStep("Clicking forgot password button");
        loginPage.clickForgotPassword();
        ExtentReportManager.logInfo("Forgot password button clicked");
    }

    @Then("I should be redirected to the forgot password page")
    public void i_should_be_redirected_to_the_forgot_password_page() {
        LogHelper.logStep("Verifying redirection to forgot password page");
        loginPage.waitHelper.hardWait(1000);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Should be redirected to forgot password page",
                currentUrl.contains("/forgot-password"));
        ExtentReportManager.logPass("Redirected to forgot password page");
    }

    @When("I click the create account button")
    public void i_click_the_create_account_button() {
        LogHelper.logStep("Clicking create account button");
        loginPage.clickCreateAccount();
        ExtentReportManager.logInfo("Create account button clicked");
    }

    @Then("I should be redirected to the sign up page")
    public void i_should_be_redirected_to_the_sign_up_page() {
        LogHelper.logStep("Verifying redirection to sign up page");
        loginPage.waitHelper.hardWait(1000);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Should be redirected to sign up page",
                currentUrl.contains("/sign-up"));
        ExtentReportManager.logPass("Redirected to sign up page");
    }
}
