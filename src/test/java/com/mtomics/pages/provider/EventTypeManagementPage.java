package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * EventTypeManagementPage - Page Factory implementation for Provider Event Type Management page
 * Based on: apps/web/src/features/appointments/event-types
 * 
 * Handles event type management workflows including:
 * - New event type creation
 * - Event duration configuration (15, 30, 60 minutes)
 * - Event location configuration (video, phone, in-person)
 * - Booking rules (minimum notice, buffer times)
 * - Custom booking fields
 * - Event type enable/disable
 * - Confirmation requirements
 * - Event color configuration
 * - Event type editing
 * - Event type deletion
 * - Booking link copy
 */
public class EventTypeManagementPage extends BasePage {

    // Event Type Creation Form Elements
    @FindBy(xpath = "//button[contains(text(),'Create Event Type')] | //button[contains(text(),'Add Event Type')]")
    private WebElement createEventTypeButton;

    @FindBy(xpath = "//input[@name='eventTypeName' or @placeholder='Event type name']")
    private WebElement eventTypeNameInput;

    @FindBy(xpath = "//textarea[@name='description' or @placeholder='Event description']")
    private WebElement descriptionInput;

    // Duration Selection
    @FindBy(xpath = "//select[@name='duration' or contains(text(),'Duration')]")
    private WebElement durationSelect;

    @FindBy(xpath = "//input[@type='radio' and @value='15']")
    private WebElement duration15MinutesRadio;

    @FindBy(xpath = "//input[@type='radio' and @value='30']")
    private WebElement duration30MinutesRadio;

    @FindBy(xpath = "//input[@type='radio' and @value='60']")
    private WebElement duration60MinutesRadio;

    @FindBy(xpath = "//input[@type='radio' and @value='custom']")
    private WebElement durationCustomRadio;

    @FindBy(xpath = "//input[@name='customDuration' or @placeholder='Custom duration (minutes)']")
    private WebElement customDurationInput;

    // Location Selection
    @FindBy(xpath = "//input[@type='checkbox' and @name='video']")
    private WebElement videoLocationCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @name='phone']")
    private WebElement phoneLocationCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @name='inPerson']")
    private WebElement inPersonLocationCheckbox;

    // Booking Rules
    @FindBy(xpath = "//input[@name='minimumNotice' or @placeholder='Minimum notice (hours)']")
    private WebElement minimumNoticeInput;

    @FindBy(xpath = "//input[@name='bufferTime' or @placeholder='Buffer time (minutes)']")
    private WebElement bufferTimeInput;

    @FindBy(xpath = "//input[@name='maximumBooking' or @placeholder='Maximum booking window (days)']")
    private WebElement maximumBookingInput;

    // Custom Booking Fields
    @FindBy(xpath = "//button[contains(text(),'Add Custom Field')]")
    private WebElement addCustomFieldButton;

    @FindBy(xpath = "//input[@name='fieldName' or @placeholder='Field name']")
    private WebElement customFieldNameInput;

    @FindBy(xpath = "//select[@name='fieldType' or contains(text(),'Field Type')]")
    private WebElement customFieldTypeSelect;

    @FindBy(xpath = "//input[@type='checkbox' and @name='fieldRequired']")
    private WebElement customFieldRequiredCheckbox;

    // Confirmation Settings
    @FindBy(xpath = "//input[@type='checkbox' and @name='requireConfirmation']")
    private WebElement requireConfirmationCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @name='sendReminder']")
    private WebElement sendReminderCheckbox;

    @FindBy(xpath = "//input[@name='reminderTime' or @placeholder='Send reminder (hours before)']")
    private WebElement reminderTimeInput;

    // Color Configuration
    @FindBy(xpath = "//input[@type='color' and @name='eventColor']")
    private WebElement eventColorPicker;

    @FindBy(xpath = "//div[contains(@class,'color-preview')]")
    private WebElement colorPreview;

    // Event Status
    @FindBy(xpath = "//input[@type='checkbox' and @name='enabled']")
    private WebElement enabledCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @name='disabled']")
    private WebElement disabledCheckbox;

    // Action Buttons
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Create Event Type')]")
    private WebElement createButton;

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Update Event Type')]")
    private WebElement updateButton;

    @FindBy(xpath = "//button[contains(text(),'Edit Event Type')]")
    private WebElement editEventTypeButton;

    @FindBy(xpath = "//button[contains(text(),'Delete Event Type')]")
    private WebElement deleteEventTypeButton;

    @FindBy(xpath = "//button[contains(text(),'Copy Booking Link')]")
    private WebElement copyBookingLinkButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // Event Type List
    @FindBy(xpath = "//table[contains(@class,'event-types')] | //div[contains(@class,'event-type-list')]")
    private WebElement eventTypesTable;

    @FindBy(xpath = "//tr[contains(@class,'event-type-row')] | //div[contains(@class,'event-type-item')]")
    private WebElement eventTypeRow;

    // Search and Filter
    @FindBy(xpath = "//input[@placeholder='Search event types']")
    private WebElement searchInput;

    @FindBy(xpath = "//select[contains(@name,'status')]")
    private WebElement statusFilterSelect;

    // Success/Error Messages
    @FindBy(xpath = "//div[contains(text(),'Event type created successfully')]")
    private WebElement eventTypeCreatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Event type updated successfully')]")
    private WebElement eventTypeUpdatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Event type deleted successfully')]")
    private WebElement eventTypeDeletedMessage;

    @FindBy(xpath = "//div[contains(text(),'Booking link copied to clipboard')]")
    private WebElement bookingLinkCopiedMessage;

    @FindBy(xpath = "//div[contains(text(),'Event type enabled successfully')]")
    private WebElement eventTypeEnabledMessage;

    @FindBy(xpath = "//div[contains(text(),'Event type disabled successfully')]")
    private WebElement eventTypeDisabledMessage;

    @FindBy(xpath = "//div[contains(text(),'Invalid event type name')]")
    private WebElement invalidEventTypeNameError;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public EventTypeManagementPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("EventTypeManagementPage initialized");
    }

    /**
     * Check if event type management page is displayed
     *
     * @return boolean
     */
    public boolean isEventTypeManagementPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(createEventTypeButton);
        } catch (Exception e) {
            logger.debug("Event type management page not displayed");
            return false;
        }
    }

    /**
     * Click create event type button
     */
    public void clickCreateEventTypeButton() {
        LogHelper.logStep("Clicking Create Event Type button");
        waitHelper.waitForElementClickable(createEventTypeButton);
        click(createEventTypeButton);
    }

    /**
     * Enter event type name
     *
     * @param eventTypeName Event type name
     */
    public void enterEventTypeName(String eventTypeName) {
        LogHelper.logStep("Entering event type name: " + eventTypeName);
        waitHelper.waitForElementVisible(eventTypeNameInput);
        enterText(eventTypeNameInput, eventTypeName);
    }

    /**
     * Enter event description
     *
     * @param description Event description
     */
    public void enterDescription(String description) {
        LogHelper.logStep("Entering event description");
        waitHelper.waitForElementVisible(descriptionInput);
        enterText(descriptionInput, description);
    }

    /**
     * Select event duration
     *
     * @param duration Duration in minutes (15, 30, 60, or custom)
     */
    public void selectEventDuration(String duration) {
        LogHelper.logStep("Selecting event duration: " + duration);
        switch (duration.toLowerCase()) {
            case "15":
                waitHelper.waitForElementClickable(duration15MinutesRadio);
                click(duration15MinutesRadio);
                break;
            case "30":
                waitHelper.waitForElementClickable(duration30MinutesRadio);
                click(duration30MinutesRadio);
                break;
            case "60":
                waitHelper.waitForElementClickable(duration60MinutesRadio);
                click(duration60MinutesRadio);
                break;
            default:
                waitHelper.waitForElementClickable(durationCustomRadio);
                click(durationCustomRadio);
                enterText(customDurationInput, duration);
        }
    }

    /**
     * Select event location
     *
     * @param location Location type (video, phone, in-person)
     */
    public void selectEventLocation(String location) {
        LogHelper.logStep("Selecting event location: " + location);
        switch (location.toLowerCase()) {
            case "video":
                waitHelper.waitForElementClickable(videoLocationCheckbox);
                if (!videoLocationCheckbox.isSelected()) {
                    click(videoLocationCheckbox);
                }
                break;
            case "phone":
                waitHelper.waitForElementClickable(phoneLocationCheckbox);
                if (!phoneLocationCheckbox.isSelected()) {
                    click(phoneLocationCheckbox);
                }
                break;
            case "in-person":
                waitHelper.waitForElementClickable(inPersonLocationCheckbox);
                if (!inPersonLocationCheckbox.isSelected()) {
                    click(inPersonLocationCheckbox);
                }
                break;
            default:
                logger.warn("Unknown location type: " + location);
        }
    }

    /**
     * Select multiple event locations
     *
     * @param locations Array of location types
     */
    public void selectMultipleEventLocations(String... locations) {
        LogHelper.logStep("Selecting multiple event locations");
        for (String location : locations) {
            selectEventLocation(location);
        }
    }

    /**
     * Set minimum notice for booking
     *
     * @param hours Minimum notice in hours
     */
    public void setMinimumNotice(String hours) {
        LogHelper.logStep("Setting minimum notice: " + hours + " hours");
        waitHelper.waitForElementVisible(minimumNoticeInput);
        enterText(minimumNoticeInput, hours);
    }

    /**
     * Set buffer time between appointments
     *
     * @param minutes Buffer time in minutes
     */
    public void setBufferTime(String minutes) {
        LogHelper.logStep("Setting buffer time: " + minutes + " minutes");
        waitHelper.waitForElementVisible(bufferTimeInput);
        enterText(bufferTimeInput, minutes);
    }

    /**
     * Set maximum booking window
     *
     * @param days Maximum booking window in days
     */
    public void setMaximumBookingWindow(String days) {
        LogHelper.logStep("Setting maximum booking window: " + days + " days");
        waitHelper.waitForElementVisible(maximumBookingInput);
        enterText(maximumBookingInput, days);
    }

    /**
     * Add custom booking field
     *
     * @param fieldName Field name
     * @param fieldType Field type (text, email, phone, dropdown, etc.)
     * @param required Whether field is required
     */
    public void addCustomBookingField(String fieldName, String fieldType, boolean required) {
        LogHelper.logStep("Adding custom booking field: " + fieldName);
        waitHelper.waitForElementClickable(addCustomFieldButton);
        click(addCustomFieldButton);
        waitHelper.waitForElementVisible(customFieldNameInput);
        enterText(customFieldNameInput, fieldName);
        waitHelper.waitForElementClickable(customFieldTypeSelect);
        click(customFieldTypeSelect);
        if (required) {
            waitHelper.waitForElementClickable(customFieldRequiredCheckbox);
            if (!customFieldRequiredCheckbox.isSelected()) {
                click(customFieldRequiredCheckbox);
            }
        }
    }

    /**
     * Enable confirmation requirement
     */
    public void enableConfirmationRequirement() {
        LogHelper.logStep("Enabling confirmation requirement");
        waitHelper.waitForElementClickable(requireConfirmationCheckbox);
        if (!requireConfirmationCheckbox.isSelected()) {
            click(requireConfirmationCheckbox);
        }
    }

    /**
     * Enable reminder notifications
     *
     * @param hours Hours before appointment to send reminder
     */
    public void enableReminder(String hours) {
        LogHelper.logStep("Enabling reminder notification");
        waitHelper.waitForElementClickable(sendReminderCheckbox);
        if (!sendReminderCheckbox.isSelected()) {
            click(sendReminderCheckbox);
        }
        waitHelper.waitForElementVisible(reminderTimeInput);
        enterText(reminderTimeInput, hours);
    }

    /**
     * Set event color
     *
     * @param color Color hex code (e.g., #FF0000)
     */
    public void setEventColor(String color) {
        LogHelper.logStep("Setting event color: " + color);
        waitHelper.waitForElementVisible(eventColorPicker);
        eventColorPicker.sendKeys(color);
    }

    /**
     * Enable event type
     */
    public void enableEventType() {
        LogHelper.logStep("Enabling event type");
        waitHelper.waitForElementClickable(enabledCheckbox);
        if (!enabledCheckbox.isSelected()) {
            click(enabledCheckbox);
        }
    }

    /**
     * Disable event type
     */
    public void disableEventType() {
        LogHelper.logStep("Disabling event type");
        waitHelper.waitForElementClickable(disabledCheckbox);
        if (!disabledCheckbox.isSelected()) {
            click(disabledCheckbox);
        }
    }

    /**
     * Click create button
     */
    public void clickCreateButton() {
        LogHelper.logStep("Clicking Create button");
        waitHelper.waitForElementClickable(createButton);
        click(createButton);
    }

    /**
     * Create new event type with basic details
     *
     * @param eventTypeName Event type name
     * @param duration Duration in minutes
     * @param locations Array of location types
     */
    public void createEventType(String eventTypeName, String duration, String... locations) {
        LogHelper.logStep("Creating event type: " + eventTypeName);
        clickCreateEventTypeButton();
        enterEventTypeName(eventTypeName);
        selectEventDuration(duration);
        selectMultipleEventLocations(locations);
        clickCreateButton();
    }

    /**
     * Check if event type created message is displayed
     *
     * @return boolean
     */
    public boolean isEventTypeCreatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(eventTypeCreatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click edit event type button
     */
    public void clickEditEventTypeButton() {
        LogHelper.logStep("Clicking Edit Event Type button");
        waitHelper.waitForElementClickable(editEventTypeButton);
        click(editEventTypeButton);
    }

    /**
     * Click update button
     */
    public void clickUpdateButton() {
        LogHelper.logStep("Clicking Update button");
        waitHelper.waitForElementClickable(updateButton);
        click(updateButton);
    }

    /**
     * Check if event type updated message is displayed
     *
     * @return boolean
     */
    public boolean isEventTypeUpdatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(eventTypeUpdatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click delete event type button
     */
    public void clickDeleteEventTypeButton() {
        LogHelper.logStep("Clicking Delete Event Type button");
        waitHelper.waitForElementClickable(deleteEventTypeButton);
        click(deleteEventTypeButton);
    }

    /**
     * Check if event type deleted message is displayed
     *
     * @return boolean
     */
    public boolean isEventTypeDeletedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(eventTypeDeletedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click copy booking link button
     */
    public void clickCopyBookingLinkButton() {
        LogHelper.logStep("Clicking Copy Booking Link button");
        waitHelper.waitForElementClickable(copyBookingLinkButton);
        click(copyBookingLinkButton);
    }

    /**
     * Check if booking link copied message is displayed
     *
     * @return boolean
     */
    public boolean isBookingLinkCopiedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(bookingLinkCopiedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * View list of event types
     *
     * @return boolean - true if event types table is displayed
     */
    public boolean viewEventTypesList() {
        LogHelper.logStep("Viewing event types list");
        try {
            return waitHelper.isElementDisplayed(eventTypesTable);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Search event types
     *
     * @param searchTerm Search term
     */
    public void searchEventTypes(String searchTerm) {
        LogHelper.logStep("Searching event types: " + searchTerm);
        waitHelper.waitForElementVisible(searchInput);
        enterText(searchInput, searchTerm);
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
     * Check if event type enabled message is displayed
     *
     * @return boolean
     */
    public boolean isEventTypeEnabledMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(eventTypeEnabledMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if event type disabled message is displayed
     *
     * @return boolean
     */
    public boolean isEventTypeDisabledMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(eventTypeDisabledMessage);
        } catch (Exception e) {
            return false;
        }
    }
}
