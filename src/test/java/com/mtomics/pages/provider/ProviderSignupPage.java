package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * ProviderSignupPage - Page Factory implementation for Provider Signup page
 * Based on: apps/web/src/features/auth/pages/sign-up/index.tsx
 * 
 * Handles provider registration workflows including:
 * - Email registration
 * - Password creation
 * - Email verification
 * - MFA setup
 */
public class ProviderSignupPage extends BasePage {

    // Signup Form Elements
    @FindBy(xpath = "//input[@placeholder='Enter your email' or @name='email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@placeholder='Enter your password' or @name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@placeholder='Confirm your password' or @name='confirmPassword']")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//input[@placeholder='Enter your first name' or @name='firstName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder='Enter your last name' or @name='lastName']")
    private WebElement lastNameInput;

    // Form Buttons
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Sign Up')]")
    private WebElement signUpButton;

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Verify Code')]")
    private WebElement verifyCodeButton;

    @FindBy(xpath = "//button[contains(text(),'Resend Code')]")
    private WebElement resendCodeButton;

    // Verification Elements
    @FindBy(xpath = "//div[contains(text(),'Enter verification code')]")
    private WebElement verificationCodeHeading;

    @FindBy(xpath = "//input[@placeholder='Enter verification code']")
    private WebElement verificationCodeInput;

    // Checkbox Elements
    @FindBy(xpath = "//input[@type='checkbox' and @name='terms']")
    private WebElement termsCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @name='privacy']")
    private WebElement privacyCheckbox;

    // Links
    @FindBy(xpath = "//a[contains(text(),'Terms of Use')]")
    private WebElement termsOfUseLink;

    @FindBy(xpath = "//a[contains(text(),'Privacy Policy')]")
    private WebElement privacyPolicyLink;

    @FindBy(xpath = "//a[contains(text(),'Sign In')]")
    private WebElement signInLink;

    // Error/Success Messages
    @FindBy(xpath = "//div[contains(text(),'Email already exists')]")
    private WebElement emailExistsError;

    @FindBy(xpath = "//div[contains(text(),'Password does not meet requirements')]")
    private WebElement passwordRequirementsError;

    @FindBy(xpath = "//div[contains(text(),'Verification code has been sent')]")
    private WebElement verificationCodeSentMessage;

    @FindBy(xpath = "//div[contains(text(),'Account verified successfully')]")
    private WebElement accountVerifiedMessage;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public ProviderSignupPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("ProviderSignupPage initialized");
    }

    /**
     * Check if signup page is displayed
     *
     * @return boolean
     */
    public boolean isSignupPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(signUpButton);
        } catch (Exception e) {
            logger.debug("Signup page not displayed");
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
        enterText(emailInput, email);
    }

    /**
     * Enter first name
     *
     * @param firstName First name
     */
    public void enterFirstName(String firstName) {
        LogHelper.logStep("Entering first name: " + firstName);
        waitHelper.waitForElementVisible(firstNameInput);
        enterText(firstNameInput, firstName);
    }

    /**
     * Enter last name
     *
     * @param lastName Last name
     */
    public void enterLastName(String lastName) {
        LogHelper.logStep("Entering last name: " + lastName);
        waitHelper.waitForElementVisible(lastNameInput);
        enterText(lastNameInput, lastName);
    }

    /**
     * Enter password
     *
     * @param password Password
     */
    public void enterPassword(String password) {
        LogHelper.logStep("Entering password");
        waitHelper.waitForElementVisible(passwordInput);
        enterText(passwordInput, password);
    }

    /**
     * Confirm password
     *
     * @param password Password
     */
    public void confirmPassword(String password) {
        LogHelper.logStep("Confirming password");
        waitHelper.waitForElementVisible(confirmPasswordInput);
        enterText(confirmPasswordInput, password);
    }

    /**
     * Accept terms and conditions
     */
    public void acceptTermsAndConditions() {
        LogHelper.logStep("Accepting terms and conditions");
        waitHelper.waitForElementClickable(termsCheckbox);
        if (!termsCheckbox.isSelected()) {
            click(termsCheckbox);
        }
    }

    /**
     * Accept privacy policy
     */
    public void acceptPrivacyPolicy() {
        LogHelper.logStep("Accepting privacy policy");
        waitHelper.waitForElementClickable(privacyCheckbox);
        if (!privacyCheckbox.isSelected()) {
            click(privacyCheckbox);
        }
    }

    /**
     * Click sign up button
     */
    public void clickSignUpButton() {
        LogHelper.logStep("Clicking Sign Up button");
        waitHelper.waitForElementClickable(signUpButton);
        click(signUpButton);
    }

    /**
     * Enter verification code
     *
     * @param code Verification code
     */
    public void enterVerificationCode(String code) {
        LogHelper.logStep("Entering verification code");
        waitHelper.waitForElementVisible(verificationCodeInput);
        enterText(verificationCodeInput, code);
    }

    /**
     * Click verify code button
     */
    public void clickVerifyCodeButton() {
        LogHelper.logStep("Clicking Verify Code button");
        waitHelper.waitForElementClickable(verifyCodeButton);
        click(verifyCodeButton);
    }

    /**
     * Click resend code button
     */
    public void clickResendCodeButton() {
        LogHelper.logStep("Clicking Resend Code button");
        waitHelper.waitForElementClickable(resendCodeButton);
        click(resendCodeButton);
    }

    /**
     * Check if verification code screen is displayed
     *
     * @return boolean
     */
    public boolean isVerificationCodeScreenDisplayed() {
        try {
            return waitHelper.isElementDisplayed(verificationCodeHeading);
        } catch (Exception e) {
            logger.debug("Verification code screen not displayed");
            return false;
        }
    }

    /**
     * Check if email already exists error is displayed
     *
     * @return boolean
     */
    public boolean isEmailExistsErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(emailExistsError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if verification code sent message is displayed
     *
     * @return boolean
     */
    public boolean isVerificationCodeSentMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(verificationCodeSentMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if account verified message is displayed
     *
     * @return boolean
     */
    public boolean isAccountVerifiedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(accountVerifiedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click sign in link
     */
    public void clickSignInLink() {
        LogHelper.logStep("Clicking Sign In link");
        waitHelper.waitForElementClickable(signInLink);
        click(signInLink);
    }

    /**
     * Complete provider signup with invitation link
     *
     * @param firstName First name
     * @param lastName Last name
     * @param password Password
     */
    public void completeSignupWithInvitation(String firstName, String lastName, String password) {
        LogHelper.logStep("Completing signup with invitation link");
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPassword(password);
        confirmPassword(password);
        acceptTermsAndConditions();
        acceptPrivacyPolicy();
        clickSignUpButton();
    }

    /**
     * Complete provider signup with registration details
     *
     * @param email Email address
     * @param firstName First name
     * @param lastName Last name
     * @param password Password
     */
    public void completeSignup(String email, String firstName, String lastName, String password) {
        LogHelper.logStep("Completing provider signup");
        enterEmail(email);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPassword(password);
        confirmPassword(password);
        acceptTermsAndConditions();
        acceptPrivacyPolicy();
        clickSignUpButton();
    }

    /**
     * Verify email with code
     *
     * @param code Verification code
     */
    public void verifyEmailWithCode(String code) {
        LogHelper.logStep("Verifying email with code");
        enterVerificationCode(code);
        clickVerifyCodeButton();
    }
}
