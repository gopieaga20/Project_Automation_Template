package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * ChargebeePricingPage - Page Factory implementation for Chargebee Pricing/Subscription page
 * Based on: apps/web/src/features/pricing/pages/list-all-pricing.tsx
 * 
 * Handles provider subscription and billing management including:
 * - Plan selection (Free, Navigator plans)
 * - Payment details entry
 * - Subscription activation
 * - Billing history
 * - Plan upgrades/downgrades
 * - Subscription cancellation
 */
public class ChargebeePricingPage extends BasePage {

    // Plan Cards
    @FindBy(xpath = "//div[contains(text(),'Free Plan')]")
    private WebElement freePlanCard;

    @FindBy(xpath = "//div[contains(text(),'Navigator Plan')]")
    private WebElement navigatorPlanCard;

    @FindBy(xpath = "//div[contains(text(),'Professional Plan')]")
    private WebElement professionalPlanCard;

    @FindBy(xpath = "//div[contains(text(),'Enterprise Plan')]")
    private WebElement enterprisePlanCard;

    // Plan Selection Buttons
    @FindBy(xpath = "//button[contains(text(),'Choose Free Plan')]")
    private WebElement chooseFreePlanButton;

    @FindBy(xpath = "//button[contains(text(),'Choose Navigator Plan')]")
    private WebElement chooseNavigatorPlanButton;

    @FindBy(xpath = "//button[contains(text(),'Get Started')]")
    private WebElement getStartedButton;

    @FindBy(xpath = "//button[contains(text(),'Subscribe')]")
    private WebElement subscribeButton;

    @FindBy(xpath = "//button[contains(text(),'Upgrade Plan')]")
    private WebElement upgradePlanButton;

    @FindBy(xpath = "//button[contains(text(),'Downgrade Plan')]")
    private WebElement downgradePlanButton;

    // Payment Form Elements
    @FindBy(xpath = "//input[@name='cardNumber' or @placeholder='Card number']")
    private WebElement cardNumberInput;

    @FindBy(xpath = "//input[@name='cardholderName' or @placeholder='Cardholder name']")
    private WebElement cardholderNameInput;

    @FindBy(xpath = "//input[@name='expiryDate' or @placeholder='MM/YY']")
    private WebElement expiryDateInput;

    @FindBy(xpath = "//input[@name='cvv' or @placeholder='CVV']")
    private WebElement cvvInput;

    @FindBy(xpath = "//input[@name='billingAddress' or @placeholder='Billing address']")
    private WebElement billingAddressInput;

    @FindBy(xpath = "//input[@name='zipCode' or @placeholder='Zip code']")
    private WebElement zipCodeInput;

    // Plan Features
    @FindBy(xpath = "//div[contains(text(),'Client invitations')]")
    private WebElement clientInvitationsFeature;

    @FindBy(xpath = "//div[contains(text(),'Document upload')]")
    private WebElement documentUploadFeature;

    @FindBy(xpath = "//div[contains(text(),'Lab file processing')]")
    private WebElement labFileProcessingFeature;

    @FindBy(xpath = "//div[contains(text(),'Appointment scheduling')]")
    private WebElement appointmentSchedulingFeature;

    // Pricing Information
    @FindBy(xpath = "//span[contains(text(),'$')]")
    private WebElement priceDisplay;

    @FindBy(xpath = "//div[contains(text(),'per month')]")
    private WebElement billingPeriod;

    @FindBy(xpath = "//div[contains(text(),'Annual billing')]")
    private WebElement annualBillingOption;

    @FindBy(xpath = "//div[contains(text(),'Monthly billing')]")
    private WebElement monthlyBillingOption;

    // Subscription Management
    @FindBy(xpath = "//button[contains(text(),'View Billing History')]")
    private WebElement viewBillingHistoryButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel Subscription')]")
    private WebElement cancelSubscriptionButton;

    @FindBy(xpath = "//button[contains(text(),'Update Payment Method')]")
    private WebElement updatePaymentMethodButton;

    @FindBy(xpath = "//div[contains(text(),'Current Plan:')]")
    private WebElement currentPlanDisplay;

    @FindBy(xpath = "//div[contains(text(),'Next billing date:')]")
    private WebElement nextBillingDateDisplay;

    // Billing History
    @FindBy(xpath = "//table[contains(@class,'billing')]")
    private WebElement billingHistoryTable;

    @FindBy(xpath = "//tr[contains(text(),'Invoice')]")
    private WebElement invoiceRow;

    @FindBy(xpath = "//a[contains(text(),'Download Invoice')]")
    private WebElement downloadInvoiceLink;

    // Success/Error Messages
    @FindBy(xpath = "//div[contains(text(),'Subscription activated successfully')]")
    private WebElement subscriptionActivatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Plan upgraded successfully')]")
    private WebElement planUpgradedMessage;

    @FindBy(xpath = "//div[contains(text(),'Plan downgraded successfully')]")
    private WebElement planDowngradedMessage;

    @FindBy(xpath = "//div[contains(text(),'Subscription cancelled')]")
    private WebElement subscriptionCancelledMessage;

    @FindBy(xpath = "//div[contains(text(),'Payment failed')]")
    private WebElement paymentFailedMessage;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public ChargebeePricingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("ChargebeePricingPage initialized");
    }

    /**
     * Check if pricing page is displayed
     *
     * @return boolean
     */
    public boolean isPricingPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(freePlanCard) || waitHelper.isElementDisplayed(navigatorPlanCard);
        } catch (Exception e) {
            logger.debug("Pricing page not displayed");
            return false;
        }
    }

    /**
     * Select free plan
     */
    public void selectFreePlan() {
        LogHelper.logStep("Selecting Free Plan");
        waitHelper.waitForElementClickable(chooseFreePlanButton);
        click(chooseFreePlanButton);
    }

    /**
     * Select navigator plan
     */
    public void selectNavigatorPlan() {
        LogHelper.logStep("Selecting Navigator Plan");
        waitHelper.waitForElementClickable(chooseNavigatorPlanButton);
        click(chooseNavigatorPlanButton);
    }

    /**
     * Click subscribe button
     */
    public void clickSubscribeButton() {
        LogHelper.logStep("Clicking Subscribe button");
        waitHelper.waitForElementClickable(subscribeButton);
        click(subscribeButton);
    }

    /**
     * Enter card number
     *
     * @param cardNumber Card number
     */
    public void enterCardNumber(String cardNumber) {
        LogHelper.logStep("Entering card number");
        waitHelper.waitForElementVisible(cardNumberInput);
        enterText(cardNumberInput, cardNumber);
    }

    /**
     * Enter cardholder name
     *
     * @param cardholderName Cardholder name
     */
    public void enterCardholderName(String cardholderName) {
        LogHelper.logStep("Entering cardholder name: " + cardholderName);
        waitHelper.waitForElementVisible(cardholderNameInput);
        enterText(cardholderNameInput, cardholderName);
    }

    /**
     * Enter expiry date
     *
     * @param expiryDate Expiry date (MM/YY format)
     */
    public void enterExpiryDate(String expiryDate) {
        LogHelper.logStep("Entering expiry date");
        waitHelper.waitForElementVisible(expiryDateInput);
        enterText(expiryDateInput, expiryDate);
    }

    /**
     * Enter CVV
     *
     * @param cvv CVV code
     */
    public void enterCVV(String cvv) {
        LogHelper.logStep("Entering CVV");
        waitHelper.waitForElementVisible(cvvInput);
        enterText(cvvInput, cvv);
    }

    /**
     * Enter billing address
     *
     * @param address Billing address
     */
    public void enterBillingAddress(String address) {
        LogHelper.logStep("Entering billing address: " + address);
        waitHelper.waitForElementVisible(billingAddressInput);
        enterText(billingAddressInput, address);
    }

    /**
     * Enter zip code
     *
     * @param zipCode Zip code
     */
    public void enterZipCode(String zipCode) {
        LogHelper.logStep("Entering zip code: " + zipCode);
        waitHelper.waitForElementVisible(zipCodeInput);
        enterText(zipCodeInput, zipCode);
    }

    /**
     * Enter payment details
     *
     * @param cardNumber Card number
     * @param cardholderName Cardholder name
     * @param expiryDate Expiry date
     * @param cvv CVV code
     */
    public void enterPaymentDetails(String cardNumber, String cardholderName, String expiryDate, String cvv) {
        LogHelper.logStep("Entering payment details");
        enterCardNumber(cardNumber);
        enterCardholderName(cardholderName);
        enterExpiryDate(expiryDate);
        enterCVV(cvv);
    }

    /**
     * Check if subscription activated message is displayed
     *
     * @return boolean
     */
    public boolean isSubscriptionActivatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(subscriptionActivatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if plan upgraded message is displayed
     *
     * @return boolean
     */
    public boolean isPlanUpgradedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(planUpgradedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if plan downgraded message is displayed
     *
     * @return boolean
     */
    public boolean isPlanDowngradedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(planDowngradedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if payment failed message is displayed
     *
     * @return boolean
     */
    public boolean isPaymentFailedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(paymentFailedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click upgrade plan button
     */
    public void clickUpgradePlanButton() {
        LogHelper.logStep("Clicking Upgrade Plan button");
        waitHelper.waitForElementClickable(upgradePlanButton);
        click(upgradePlanButton);
    }

    /**
     * Click downgrade plan button
     */
    public void clickDowngradePlanButton() {
        LogHelper.logStep("Clicking Downgrade Plan button");
        waitHelper.waitForElementClickable(downgradePlanButton);
        click(downgradePlanButton);
    }

    /**
     * Click view billing history button
     */
    public void clickViewBillingHistoryButton() {
        LogHelper.logStep("Clicking View Billing History button");
        waitHelper.waitForElementClickable(viewBillingHistoryButton);
        click(viewBillingHistoryButton);
    }

    /**
     * Click cancel subscription button
     */
    public void clickCancelSubscriptionButton() {
        LogHelper.logStep("Clicking Cancel Subscription button");
        waitHelper.waitForElementClickable(cancelSubscriptionButton);
        click(cancelSubscriptionButton);
    }

    /**
     * Check if billing history table is displayed
     *
     * @return boolean
     */
    public boolean isBillingHistoryDisplayed() {
        try {
            return waitHelper.isElementDisplayed(billingHistoryTable);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify free plan features are available
     *
     * @return boolean
     */
    public boolean verifyFreePlanFeatures() {
        LogHelper.logStep("Verifying free plan features");
        return isDisplayed(clientInvitationsFeature) && isDisplayed(documentUploadFeature);
    }

    /**
     * Verify navigator plan features are available
     *
     * @return boolean
     */
    public boolean verifyNavigatorPlanFeatures() {
        LogHelper.logStep("Verifying navigator plan features");
        return isDisplayed(labFileProcessingFeature) && isDisplayed(appointmentSchedulingFeature);
    }
}
