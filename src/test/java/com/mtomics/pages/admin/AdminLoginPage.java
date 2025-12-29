package com.mtomics.pages.admin;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * AdminLoginPage - Page Factory implementation for Admin Login page
 * Based on: apps/web/src/features/auth/pages/sign-in.tsx
 * 
 * Note: Page Factory elements are automatically initialized and don't require
 * explicit waits
 */
public class AdminLoginPage extends BasePage {

    // Page Factory elements using @FindBy annotation
    @FindBy(xpath = "//input[@placeholder='Enter your email' or @name='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@placeholder='Enter your password' or @name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Sign In')]")
    private WebElement signInButton;

    @FindBy(xpath = "//button[contains(text(),'Forgot password?')]")
    private WebElement forgotPasswordButton;

    @FindBy(xpath = "//p[contains(text(),'Sign In')]")
    private WebElement signInHeading;

    @FindBy(xpath = "/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/form/div[2]/div/button")
    private WebElement togglePasswordButton;

    @FindBy(xpath = "//a[contains(@href,'/sign-up')]//button[contains(text(),'Create now')]")
    private WebElement createAccountButton;

    // Error/Success messages (toast notifications)
    @FindBy(xpath = "//div[contains(text(),'Incorrect email or password')]")
    private WebElement incorrectCredentialsToast;

    @FindBy(xpath = "//div[contains(text(),'Verification code has been sent')]")
    private WebElement mfaCodeSentToast;

    /**
     * Constructor - initializes Page Factory elements
     * 
     * @param driver WebDriver instance
     */
    public AdminLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("AdminLoginPage initialized");
    }

    /**
     * Check if login page is displayed
     * 
     * @return boolean
     */
    public boolean isLoginPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(signInHeading);
        } catch (Exception e) {
            logger.debug("Login page not displayed");
            return false;
        }
    }

    /**
     * Enter email address
     * 
     * @param email Email address
     */
    public void enterEmail(String email) {
        LogHelper.logStep("Entering email: " + email);
        waitHelper.waitForElementVisible(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        logger.debug("Email entered: {}", email);
    }

    /**
     * Enter password
     * 
     * @param password Password
     */
    public void enterPassword(String password) {
        LogHelper.logStep("Entering password");
        waitHelper.waitForElementVisible(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        logger.debug("Password entered");
    }

    /**
     * Click sign in button
     */
    public void clickSignIn() {
        LogHelper.logStep("Clicking Sign In button");
        waitHelper.waitForElementClickable(signInButton);
        signInButton.click();
        logger.info("Sign In button clicked");
    }

    /**
     * Login with credentials
     * 
     * @param email    Email address
     * @param password Password
     */
    public void login(String email, String password) {
        LogHelper.logStep("Logging in with email: " + email);
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        logger.info("Login attempted");
    }

    /**
     * Click forgot password button
     */
    public void clickForgotPassword() {
        LogHelper.logStep("Clicking forgot password button");
        waitHelper.waitForElementClickable(forgotPasswordButton);
        forgotPasswordButton.click();
        logger.info("Forgot password button clicked");
    }

    /**
     * Toggle password visibility
     */
    public void togglePasswordVisibility() {
        LogHelper.logStep("Toggling password visibility");
        waitHelper.waitForElementClickable(togglePasswordButton);
        togglePasswordButton.click();
        logger.info("Password visibility toggled");
    }

    /**
     * Click create account button
     */
    public void clickCreateAccount() {
        LogHelper.logStep("Clicking create account button");
        waitHelper.waitForElementClickable(createAccountButton);
        createAccountButton.click();
        logger.info("Create account button clicked");
    }

    /**
     * Check if incorrect credentials error is displayed
     * 
     * @return boolean
     */
    public boolean isIncorrectCredentialsErrorDisplayed() {
        try {
            // Wait a bit for toast to appear
            waitHelper.hardWait(1000);
            return incorrectCredentialsToast.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if MFA code sent message is displayed
     * 
     * @return boolean
     */
    public boolean isMfaCodeSentMessageDisplayed() {
        try {
            // Wait a bit for toast to appear
            waitHelper.hardWait(1000);
            return mfaCodeSentToast.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get page title
     * 
     * @return Page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get current URL
     * 
     * @return Current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
