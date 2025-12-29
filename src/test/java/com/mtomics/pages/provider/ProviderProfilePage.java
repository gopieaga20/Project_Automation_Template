package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * ProviderProfilePage - Page Factory implementation for Provider Profile page
 * Based on: apps/web/src/features/profile/pages/profile.tsx
 * 
 * Handles provider profile management including:
 * - Personal information updates
 * - Contact details
 * - Professional credentials
 * - Profile picture upload
 * - Password changes
 * - MFA settings
 * - Timezone updates
 */
public class ProviderProfilePage extends BasePage {

    // Profile Navigation Buttons
    @FindBy(xpath = "//button[contains(text(),'Profile Information')]")
    private WebElement profileInformationButton;

    @FindBy(xpath = "//button[contains(text(),'Security')]")
    private WebElement securityButton;

    @FindBy(xpath = "//button[contains(text(),'Notification Preferences')]")
    private WebElement notificationPreferencesButton;

    @FindBy(xpath = "//button[contains(text(),'Tags')]")
    private WebElement tagsButton;

    @FindBy(xpath = "//button[contains(text(),'Subscription')]")
    private WebElement subscriptionButton;

    // Profile Form Elements - Personal Information
    @FindBy(xpath = "//input[@name='firstName' or @placeholder='Enter first name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastName' or @placeholder='Enter last name']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@name='email' or @placeholder='Enter email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='phone' or @placeholder='Enter phone number']")
    private WebElement phoneInput;

    @FindBy(xpath = "//input[@name='dateOfBirth' or @placeholder='Select date of birth']")
    private WebElement dateOfBirthInput;

    // Address Information
    @FindBy(xpath = "//input[@name='address' or @placeholder='Enter address']")
    private WebElement addressInput;

    @FindBy(xpath = "//input[@name='city' or @placeholder='Enter city']")
    private WebElement cityInput;

    @FindBy(xpath = "//input[@name='state' or @placeholder='Enter state']")
    private WebElement stateInput;

    @FindBy(xpath = "//input[@name='zipCode' or @placeholder='Enter zip code']")
    private WebElement zipCodeInput;

    @FindBy(xpath = "//select[@name='country' or @placeholder='Select country']")
    private WebElement countrySelect;

    // Professional Information
    @FindBy(xpath = "//input[@name='licenseNumber' or @placeholder='Enter license number']")
    private WebElement licenseNumberInput;

    @FindBy(xpath = "//input[@name='specialization' or @placeholder='Enter specialization']")
    private WebElement specializationInput;

    @FindBy(xpath = "//textarea[@name='bio' or @placeholder='Enter bio']")
    private WebElement bioTextarea;

    // Profile Picture
    @FindBy(xpath = "//input[@type='file' and contains(@accept, 'image')]")
    private WebElement profilePictureInput;

    @FindBy(xpath = "//button[contains(text(),'Upload Picture')]")
    private WebElement uploadPictureButton;

    @FindBy(xpath = "//button[contains(text(),'Remove Picture')]")
    private WebElement removePictureButton;

    // Timezone
    @FindBy(xpath = "//select[@name='timezone' or contains(text(),'timezone')]")
    private WebElement timezoneSelect;

    // Security Form Elements
    @FindBy(xpath = "//button[contains(text(),'Change Password')]")
    private WebElement changePasswordButton;

    @FindBy(xpath = "//button[contains(text(),'Enable MFA')]")
    private WebElement enableMFAButton;

    @FindBy(xpath = "//button[contains(text(),'Disable MFA')]")
    private WebElement disableMFAButton;

    // Password Change
    @FindBy(xpath = "//input[@name='currentPassword' or @placeholder='Enter current password']")
    private WebElement currentPasswordInput;

    @FindBy(xpath = "//input[@name='newPassword' or @placeholder='Enter new password']")
    private WebElement newPasswordInput;

    @FindBy(xpath = "//input[@name='confirmPassword' or @placeholder='Confirm new password']")
    private WebElement confirmPasswordInput;

    // Form Buttons
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Update')]")
    private WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // Success/Error Messages
    @FindBy(xpath = "//div[contains(text(),'Profile updated successfully')]")
    private WebElement profileUpdatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Password changed successfully')]")
    private WebElement passwordChangedMessage;

    @FindBy(xpath = "//div[contains(text(),'Error updating profile')]")
    private WebElement errorMessage;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public ProviderProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("ProviderProfilePage initialized");
    }

    /**
     * Check if profile page is displayed
     *
     * @return boolean
     */
    public boolean isProfilePageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(profileInformationButton);
        } catch (Exception e) {
            logger.debug("Profile page not displayed");
            return false;
        }
    }

    /**
     * Navigate to profile information section
     */
    public void navigateToProfileInformation() {
        LogHelper.logStep("Navigating to Profile Information");
        waitHelper.waitForElementClickable(profileInformationButton);
        click(profileInformationButton);
    }

    /**
     * Navigate to security section
     */
    public void navigateToSecurity() {
        LogHelper.logStep("Navigating to Security section");
        waitHelper.waitForElementClickable(securityButton);
        click(securityButton);
    }

    /**
     * Navigate to notification preferences section
     */
    public void navigateToNotificationPreferences() {
        LogHelper.logStep("Navigating to Notification Preferences");
        waitHelper.waitForElementClickable(notificationPreferencesButton);
        click(notificationPreferencesButton);
    }

    /**
     * Navigate to tags section
     */
    public void navigateToTags() {
        LogHelper.logStep("Navigating to Tags");
        waitHelper.waitForElementClickable(tagsButton);
        click(tagsButton);
    }

    /**
     * Navigate to subscription section
     */
    public void navigateToSubscription() {
        LogHelper.logStep("Navigating to Subscription");
        waitHelper.waitForElementClickable(subscriptionButton);
        click(subscriptionButton);
    }

    /**
     * Update first name
     *
     * @param firstName First name
     */
    public void updateFirstName(String firstName) {
        LogHelper.logStep("Updating first name to: " + firstName);
        waitHelper.waitForElementVisible(firstNameInput);
        enterText(firstNameInput, firstName);
    }

    /**
     * Update last name
     *
     * @param lastName Last name
     */
    public void updateLastName(String lastName) {
        LogHelper.logStep("Updating last name to: " + lastName);
        waitHelper.waitForElementVisible(lastNameInput);
        enterText(lastNameInput, lastName);
    }

    /**
     * Update phone number
     *
     * @param phone Phone number
     */
    public void updatePhoneNumber(String phone) {
        LogHelper.logStep("Updating phone number to: " + phone);
        waitHelper.waitForElementVisible(phoneInput);
        enterText(phoneInput, phone);
    }

    /**
     * Update date of birth
     *
     * @param dateOfBirth Date of birth
     */
    public void updateDateOfBirth(String dateOfBirth) {
        LogHelper.logStep("Updating date of birth to: " + dateOfBirth);
        waitHelper.waitForElementVisible(dateOfBirthInput);
        enterText(dateOfBirthInput, dateOfBirth);
    }

    /**
     * Update address
     *
     * @param address Address
     */
    public void updateAddress(String address) {
        LogHelper.logStep("Updating address to: " + address);
        waitHelper.waitForElementVisible(addressInput);
        enterText(addressInput, address);
    }

    /**
     * Update city
     *
     * @param city City
     */
    public void updateCity(String city) {
        LogHelper.logStep("Updating city to: " + city);
        waitHelper.waitForElementVisible(cityInput);
        enterText(cityInput, city);
    }

    /**
     * Update state
     *
     * @param state State
     */
    public void updateState(String state) {
        LogHelper.logStep("Updating state to: " + state);
        waitHelper.waitForElementVisible(stateInput);
        enterText(stateInput, state);
    }

    /**
     * Update zip code
     *
     * @param zipCode Zip code
     */
    public void updateZipCode(String zipCode) {
        LogHelper.logStep("Updating zip code to: " + zipCode);
        waitHelper.waitForElementVisible(zipCodeInput);
        enterText(zipCodeInput, zipCode);
    }

    /**
     * Update license number
     *
     * @param licenseNumber License number
     */
    public void updateLicenseNumber(String licenseNumber) {
        LogHelper.logStep("Updating license number to: " + licenseNumber);
        waitHelper.waitForElementVisible(licenseNumberInput);
        enterText(licenseNumberInput, licenseNumber);
    }

    /**
     * Update specialization
     *
     * @param specialization Specialization
     */
    public void updateSpecialization(String specialization) {
        LogHelper.logStep("Updating specialization to: " + specialization);
        waitHelper.waitForElementVisible(specializationInput);
        enterText(specializationInput, specialization);
    }

    /**
     * Update bio
     *
     * @param bio Bio text
     */
    public void updateBio(String bio) {
        LogHelper.logStep("Updating bio");
        waitHelper.waitForElementVisible(bioTextarea);
        enterText(bioTextarea, bio);
    }

    /**
     * Upload profile picture
     *
     * @param filePath File path of picture
     */
    public void uploadProfilePicture(String filePath) {
        LogHelper.logStep("Uploading profile picture from: " + filePath);
        waitHelper.waitForElementVisible(profilePictureInput);
        profilePictureInput.sendKeys(filePath);
    }

    /**
     * Click remove picture button
     */
    public void removeProfilePicture() {
        LogHelper.logStep("Removing profile picture");
        waitHelper.waitForElementClickable(removePictureButton);
        click(removePictureButton);
    }

    /**
     * Update timezone
     *
     * @param timezone Timezone
     */
    public void updateTimezone(String timezone) {
        LogHelper.logStep("Updating timezone to: " + timezone);
        waitHelper.waitForElementVisible(timezoneSelect);
        click(timezoneSelect);
        // Select the option (implementation depends on dropdown structure)
    }

    /**
     * Click change password button
     */
    public void clickChangePasswordButton() {
        LogHelper.logStep("Clicking Change Password button");
        waitHelper.waitForElementClickable(changePasswordButton);
        click(changePasswordButton);
    }

    /**
     * Change password
     *
     * @param currentPassword Current password
     * @param newPassword New password
     */
    public void changePassword(String currentPassword, String newPassword) {
        LogHelper.logStep("Changing password");
        waitHelper.waitForElementVisible(currentPasswordInput);
        enterText(currentPasswordInput, currentPassword);
        enterText(newPasswordInput, newPassword);
        enterText(confirmPasswordInput, newPassword);
        click(saveButton);
    }

    /**
     * Enable MFA
     */
    public void enableMFA() {
        LogHelper.logStep("Enabling MFA");
        waitHelper.waitForElementClickable(enableMFAButton);
        click(enableMFAButton);
    }

    /**
     * Disable MFA
     */
    public void disableMFA() {
        LogHelper.logStep("Disabling MFA");
        waitHelper.waitForElementClickable(disableMFAButton);
        click(disableMFAButton);
    }

    /**
     * Click save button
     */
    public void clickSaveButton() {
        LogHelper.logStep("Clicking Save button");
        waitHelper.waitForElementClickable(saveButton);
        click(saveButton);
    }

    /**
     * Click cancel button
     */
    public void clickCancelButton() {
        LogHelper.logStep("Clicking Cancel button");
        waitHelper.waitForElementClickable(cancelButton);
        click(cancelButton);
    }

    /**
     * Check if profile updated message is displayed
     *
     * @return boolean
     */
    public boolean isProfileUpdatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(profileUpdatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if password changed message is displayed
     *
     * @return boolean
     */
    public boolean isPasswordChangedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(passwordChangedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Update personal information
     *
     * @param firstName First name
     * @param lastName Last name
     * @param phone Phone number
     */
    public void updatePersonalInformation(String firstName, String lastName, String phone) {
        LogHelper.logStep("Updating personal information");
        updateFirstName(firstName);
        updateLastName(lastName);
        updatePhoneNumber(phone);
    }

    /**
     * Update contact details
     *
     * @param phone Phone number
     * @param address Address
     * @param city City
     * @param state State
     * @param zipCode Zip code
     */
    public void updateContactDetails(String phone, String address, String city, String state, String zipCode) {
        LogHelper.logStep("Updating contact details");
        updatePhoneNumber(phone);
        updateAddress(address);
        updateCity(city);
        updateState(state);
        updateZipCode(zipCode);
    }

    /**
     * Update professional credentials
     *
     * @param licenseNumber License number
     * @param specialization Specialization
     */
    public void updateProfessionalCredentials(String licenseNumber, String specialization) {
        LogHelper.logStep("Updating professional credentials");
        updateLicenseNumber(licenseNumber);
        updateSpecialization(specialization);
    }
}
