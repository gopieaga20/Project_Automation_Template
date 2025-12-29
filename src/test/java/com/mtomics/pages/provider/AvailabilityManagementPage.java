package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * AvailabilityManagementPage - Page Factory implementation for Provider Availability Management page
 * Based on: apps/web/src/features/appointments
 * 
 * Handles provider availability management workflows including:
 * - New availability schedule creation
 * - Weekly recurring hours configuration
 * - Multiple time slots per day
 * - Timezone configuration
 * - Schedule overrides for specific dates
 * - Availability editing
 * - Availability deletion
 * - Default schedule setting
 */
public class AvailabilityManagementPage extends BasePage {

    // Availability Creation Form Elements
    @FindBy(xpath = "//button[contains(text(),'Create Schedule')] | //button[contains(text(),'Add Availability')]")
    private WebElement createScheduleButton;

    @FindBy(xpath = "//input[@name='scheduleName' or @placeholder='Schedule name']")
    private WebElement scheduleNameInput;

    // Day Selection
    @FindBy(xpath = "//input[@type='checkbox' and @value='monday']")
    private WebElement mondayCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @value='tuesday']")
    private WebElement tuesdayCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @value='wednesday']")
    private WebElement wednesdayCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @value='thursday']")
    private WebElement thursdayCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @value='friday']")
    private WebElement fridayCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @value='saturday']")
    private WebElement saturdayCheckbox;

    @FindBy(xpath = "//input[@type='checkbox' and @value='sunday']")
    private WebElement sundayCheckbox;

    // Time Slot Elements
    @FindBy(xpath = "//input[@name='startTime' or @placeholder='Start time']")
    private WebElement startTimeInput;

    @FindBy(xpath = "//input[@name='endTime' or @placeholder='End time']")
    private WebElement endTimeInput;

    @FindBy(xpath = "//button[contains(text(),'Add Time Slot')] | //button[contains(text(),'Add Another Time Slot')]")
    private WebElement addTimeSlotButton;

    @FindBy(xpath = "//button[contains(text(),'Remove Time Slot')]")
    private WebElement removeTimeSlotButton;

    // Timezone Selection
    @FindBy(xpath = "//select[@name='timezone' or contains(text(),'Timezone')]")
    private WebElement timezoneSelect;

    // Schedule Override Elements
    @FindBy(xpath = "//button[contains(text(),'Create Override')] | //button[contains(text(),'Add Override')]")
    private WebElement createOverrideButton;

    @FindBy(xpath = "//input[@type='date' and @name='overrideDate']")
    private WebElement overrideDateInput;

    @FindBy(xpath = "//input[@name='overrideStartTime' or @placeholder='Override start time']")
    private WebElement overrideStartTimeInput;

    @FindBy(xpath = "//input[@name='overrideEndTime' or @placeholder='Override end time']")
    private WebElement overrideEndTimeInput;

    @FindBy(xpath = "//textarea[@name='overrideReason' or @placeholder='Reason for override']")
    private WebElement overrideReasonInput;

    // Schedule Actions
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Save Schedule')]")
    private WebElement saveScheduleButton;

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Update Schedule')]")
    private WebElement updateScheduleButton;

    @FindBy(xpath = "//button[contains(text(),'Edit Schedule')]")
    private WebElement editScheduleButton;

    @FindBy(xpath = "//button[contains(text(),'Delete Schedule')]")
    private WebElement deleteScheduleButton;

    @FindBy(xpath = "//button[contains(text(),'Set as Default')]")
    private WebElement setAsDefaultButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // Schedule List
    @FindBy(xpath = "//table[contains(@class,'schedules')] | //div[contains(@class,'schedule-list')]")
    private WebElement schedulesTable;

    @FindBy(xpath = "//tr[contains(@class,'schedule-row')] | //div[contains(@class,'schedule-item')]")
    private WebElement scheduleRow;

    @FindBy(xpath = "//span[contains(text(),'Default')]")
    private WebElement defaultBadge;

    // Search and Filter
    @FindBy(xpath = "//input[@placeholder='Search schedules']")
    private WebElement searchInput;

    @FindBy(xpath = "//select[contains(@name,'status')]")
    private WebElement statusFilterSelect;

    // Success/Error Messages
    @FindBy(xpath = "//div[contains(text(),'Schedule created successfully')]")
    private WebElement scheduleCreatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Schedule updated successfully')]")
    private WebElement scheduleUpdatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Schedule deleted successfully')]")
    private WebElement scheduleDeletedMessage;

    @FindBy(xpath = "//div[contains(text(),'Default schedule set successfully')]")
    private WebElement defaultScheduleSetMessage;

    @FindBy(xpath = "//div[contains(text(),'Override created successfully')]")
    private WebElement overrideCreatedMessage;

    @FindBy(xpath = "//div[contains(text(),'Invalid time range')]")
    private WebElement invalidTimeRangeError;

    @FindBy(xpath = "//div[contains(text(),'Schedule conflicts detected')]")
    private WebElement scheduleConflictsError;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public AvailabilityManagementPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("AvailabilityManagementPage initialized");
    }

    /**
     * Check if availability management page is displayed
     *
     * @return boolean
     */
    public boolean isAvailabilityManagementPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(createScheduleButton);
        } catch (Exception e) {
            logger.debug("Availability management page not displayed");
            return false;
        }
    }

    /**
     * Click create schedule button
     */
    public void clickCreateScheduleButton() {
        LogHelper.logStep("Clicking Create Schedule button");
        waitHelper.waitForElementClickable(createScheduleButton);
        click(createScheduleButton);
    }

    /**
     * Enter schedule name
     *
     * @param scheduleName Schedule name
     */
    public void enterScheduleName(String scheduleName) {
        LogHelper.logStep("Entering schedule name: " + scheduleName);
        waitHelper.waitForElementVisible(scheduleNameInput);
        enterText(scheduleNameInput, scheduleName);
    }

    /**
     * Select day for availability
     *
     * @param day Day name (monday, tuesday, etc.)
     */
    public void selectDay(String day) {
        LogHelper.logStep("Selecting day: " + day);
        switch (day.toLowerCase()) {
            case "monday":
                waitHelper.waitForElementClickable(mondayCheckbox);
                click(mondayCheckbox);
                break;
            case "tuesday":
                waitHelper.waitForElementClickable(tuesdayCheckbox);
                click(tuesdayCheckbox);
                break;
            case "wednesday":
                waitHelper.waitForElementClickable(wednesdayCheckbox);
                click(wednesdayCheckbox);
                break;
            case "thursday":
                waitHelper.waitForElementClickable(thursdayCheckbox);
                click(thursdayCheckbox);
                break;
            case "friday":
                waitHelper.waitForElementClickable(fridayCheckbox);
                click(fridayCheckbox);
                break;
            case "saturday":
                waitHelper.waitForElementClickable(saturdayCheckbox);
                click(saturdayCheckbox);
                break;
            case "sunday":
                waitHelper.waitForElementClickable(sundayCheckbox);
                click(sundayCheckbox);
                break;
            default:
                logger.warn("Unknown day: " + day);
        }
    }

    /**
     * Select multiple days for recurring availability
     *
     * @param days Array of day names
     */
    public void selectMultipleDays(String... days) {
        LogHelper.logStep("Selecting multiple days for recurring availability");
        for (String day : days) {
            selectDay(day);
        }
    }

    /**
     * Enter start time for availability
     *
     * @param startTime Start time (HH:MM format)
     */
    public void enterStartTime(String startTime) {
        LogHelper.logStep("Entering start time: " + startTime);
        waitHelper.waitForElementVisible(startTimeInput);
        enterText(startTimeInput, startTime);
    }

    /**
     * Enter end time for availability
     *
     * @param endTime End time (HH:MM format)
     */
    public void enterEndTime(String endTime) {
        LogHelper.logStep("Entering end time: " + endTime);
        waitHelper.waitForElementVisible(endTimeInput);
        enterText(endTimeInput, endTime);
    }

    /**
     * Add time slot for the selected days
     *
     * @param startTime Start time
     * @param endTime End time
     */
    public void addTimeSlot(String startTime, String endTime) {
        LogHelper.logStep("Adding time slot from " + startTime + " to " + endTime);
        enterStartTime(startTime);
        enterEndTime(endTime);
        waitHelper.waitForElementClickable(addTimeSlotButton);
        click(addTimeSlotButton);
    }

    /**
     * Add multiple time slots per day
     *
     * @param timeSlots 2D array of time slots (startTime, endTime)
     */
    public void addMultipleTimeSlots(String[][] timeSlots) {
        LogHelper.logStep("Adding multiple time slots");
        for (int i = 0; i < timeSlots.length; i++) {
            String startTime = timeSlots[i][0];
            String endTime = timeSlots[i][1];
            addTimeSlot(startTime, endTime);
        }
    }

    /**
     * Select timezone
     *
     * @param timezone Timezone (e.g., EST, PST, UTC)
     */
    public void selectTimezone(String timezone) {
        LogHelper.logStep("Selecting timezone: " + timezone);
        waitHelper.waitForElementClickable(timezoneSelect);
        click(timezoneSelect);
    }

    /**
     * Click save schedule button
     */
    public void clickSaveScheduleButton() {
        LogHelper.logStep("Clicking Save Schedule button");
        waitHelper.waitForElementClickable(saveScheduleButton);
        click(saveScheduleButton);
    }

    /**
     * Create new availability schedule with basic details
     *
     * @param scheduleName Schedule name
     * @param days Array of days
     * @param startTime Start time
     * @param endTime End time
     * @param timezone Timezone
     */
    public void createAvailabilitySchedule(String scheduleName, String[] days, String startTime, String endTime, String timezone) {
        LogHelper.logStep("Creating availability schedule");
        clickCreateScheduleButton();
        enterScheduleName(scheduleName);
        selectMultipleDays(days);
        addTimeSlot(startTime, endTime);
        selectTimezone(timezone);
        clickSaveScheduleButton();
    }

    /**
     * Create schedule override for specific date
     *
     * @param date Override date
     * @param startTime Start time
     * @param endTime End time
     */
    public void createScheduleOverride(String date, String startTime, String endTime) {
        LogHelper.logStep("Creating schedule override for date: " + date);
        waitHelper.waitForElementClickable(createOverrideButton);
        click(createOverrideButton);
        waitHelper.waitForElementVisible(overrideDateInput);
        enterText(overrideDateInput, date);
        enterText(overrideStartTimeInput, startTime);
        enterText(overrideEndTimeInput, endTime);
        clickSaveScheduleButton();
    }

    /**
     * Create schedule override with reason
     *
     * @param date Override date
     * @param startTime Start time
     * @param endTime End time
     * @param reason Reason for override
     */
    public void createScheduleOverrideWithReason(String date, String startTime, String endTime, String reason) {
        LogHelper.logStep("Creating schedule override with reason");
        waitHelper.waitForElementClickable(createOverrideButton);
        click(createOverrideButton);
        waitHelper.waitForElementVisible(overrideDateInput);
        enterText(overrideDateInput, date);
        enterText(overrideStartTimeInput, startTime);
        enterText(overrideEndTimeInput, endTime);
        enterText(overrideReasonInput, reason);
        clickSaveScheduleButton();
    }

    /**
     * Click edit schedule button
     */
    public void clickEditScheduleButton() {
        LogHelper.logStep("Clicking Edit Schedule button");
        waitHelper.waitForElementClickable(editScheduleButton);
        click(editScheduleButton);
    }

    /**
     * Click delete schedule button
     */
    public void clickDeleteScheduleButton() {
        LogHelper.logStep("Clicking Delete Schedule button");
        waitHelper.waitForElementClickable(deleteScheduleButton);
        click(deleteScheduleButton);
    }

    /**
     * Set current schedule as default
     */
    public void setScheduleAsDefault() {
        LogHelper.logStep("Setting schedule as default");
        waitHelper.waitForElementClickable(setAsDefaultButton);
        click(setAsDefaultButton);
    }

    /**
     * Check if schedule created message is displayed
     *
     * @return boolean
     */
    public boolean isScheduleCreatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(scheduleCreatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if schedule updated message is displayed
     *
     * @return boolean
     */
    public boolean isScheduleUpdatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(scheduleUpdatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if schedule deleted message is displayed
     *
     * @return boolean
     */
    public boolean isScheduleDeletedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(scheduleDeletedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if default schedule set message is displayed
     *
     * @return boolean
     */
    public boolean isDefaultScheduleSetMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(defaultScheduleSetMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if override created message is displayed
     *
     * @return boolean
     */
    public boolean isOverrideCreatedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(overrideCreatedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if invalid time range error is displayed
     *
     * @return boolean
     */
    public boolean isInvalidTimeRangeErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(invalidTimeRangeError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if schedule conflicts error is displayed
     *
     * @return boolean
     */
    public boolean isScheduleConflictsErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(scheduleConflictsError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * View list of schedules
     *
     * @return boolean - true if schedules table is displayed
     */
    public boolean viewSchedulesList() {
        LogHelper.logStep("Viewing schedules list");
        try {
            return waitHelper.isElementDisplayed(schedulesTable);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Search schedules
     *
     * @param searchTerm Search term
     */
    public void searchSchedules(String searchTerm) {
        LogHelper.logStep("Searching schedules: " + searchTerm);
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
     * Click update schedule button
     */
    public void clickUpdateScheduleButton() {
        LogHelper.logStep("Clicking Update Schedule button");
        waitHelper.waitForElementClickable(updateScheduleButton);
        click(updateScheduleButton);
    }
}
