package com.mtomics.pages.provider;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * AppointmentBookingPage - Page Factory implementation for Provider Appointment Booking page
 * Based on: apps/web/src/features/appointments
 * 
 * Handles appointment booking and management workflows including:
 * - Book appointment for client
 * - Event type selection
 * - Available time slot selection
 * - Appointment notes
 * - Appointment confirmation
 * - View booked appointments
 * - Reschedule appointment
 * - Cancel appointment
 * - Appointment outcome notes
 * - Calendar integration verification
 */
public class AppointmentBookingPage extends BasePage {

    // Appointment Booking Form Elements
    @FindBy(xpath = "//button[contains(text(),'Book Appointment')] | //button[contains(text(),'Schedule Appointment')]")
    private WebElement bookAppointmentButton;

    @FindBy(xpath = "//select[@name='clientId' or contains(text(),'Select Client')]")
    private WebElement clientSelectDropdown;

    @FindBy(xpath = "//input[@placeholder='Search for client']")
    private WebElement clientSearchInput;

    @FindBy(xpath = "//div[contains(@class,'client-list')]")
    private WebElement clientListContainer;

    // Event Type Selection
    @FindBy(xpath = "//select[@name='eventTypeId' or contains(text(),'Select Event Type')]")
    private WebElement eventTypeSelectDropdown;

    @FindBy(xpath = "//div[contains(@class,'event-type-list')]")
    private WebElement eventTypeListContainer;

    @FindBy(xpath = "//div[contains(@class,'event-type-card')]")
    private WebElement eventTypeCard;

    // Date and Time Selection
    @FindBy(xpath = "//input[@type='date' and @name='appointmentDate']")
    private WebElement appointmentDateInput;

    @FindBy(xpath = "//div[contains(@class,'calendar')]")
    private WebElement datePickerCalendar;

    @FindBy(xpath = "//select[@name='time' or contains(text(),'Select Time')]")
    private WebElement timeSelectDropdown;

    @FindBy(xpath = "//div[contains(@class,'time-slots')]")
    private WebElement timeSlotsContainer;

    @FindBy(xpath = "//button[contains(@class,'time-slot')]")
    private WebElement timeSlotButton;

    // Appointment Details
    @FindBy(xpath = "//textarea[@name='notes' or @placeholder='Appointment notes']")
    private WebElement appointmentNotesInput;

    @FindBy(xpath = "//textarea[@name='outcomeNotes' or @placeholder='Appointment outcome notes']")
    private WebElement outcomeNotesInput;

    // Additional Information
    @FindBy(xpath = "//input[@name='title' or @placeholder='Appointment title']")
    private WebElement appointmentTitleInput;

    @FindBy(xpath = "//input[@type='email' and @name='clientEmail']")
    private WebElement clientEmailInput;

    @FindBy(xpath = "//input[@type='tel' and @name='clientPhone']")
    private WebElement clientPhoneInput;

    // Action Buttons
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Book Appointment')]")
    private WebElement confirmBookingButton;

    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Schedule Appointment')]")
    private WebElement scheduleButton;

    @FindBy(xpath = "//button[contains(text(),'Send Confirmation')]")
    private WebElement sendConfirmationButton;

    @FindBy(xpath = "//button[contains(text(),'Reschedule')]")
    private WebElement rescheduleButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel Appointment')]")
    private WebElement cancelAppointmentButton;

    @FindBy(xpath = "//button[contains(text(),'Add Outcome')]")
    private WebElement addOutcomeButton;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    // Appointment List
    @FindBy(xpath = "//table[contains(@class,'appointments')] | //div[contains(@class,'appointment-list')]")
    private WebElement appointmentsTable;

    @FindBy(xpath = "//tr[contains(@class,'appointment-row')] | //div[contains(@class,'appointment-item')]")
    private WebElement appointmentRow;

    // Calendar View
    @FindBy(xpath = "//div[contains(@class,'calendar-view')]")
    private WebElement calendarView;

    @FindBy(xpath = "//div[contains(@class,'calendar-grid')]")
    private WebElement calendarGrid;

    @FindBy(xpath = "//button[contains(text(),'List View')]")
    private WebElement listViewButton;

    @FindBy(xpath = "//button[contains(text(),'Calendar View')]")
    private WebElement calendarViewButton;

    // Search and Filter
    @FindBy(xpath = "//input[@placeholder='Search appointments']")
    private WebElement searchInput;

    @FindBy(xpath = "//input[@type='date' and @name='dateFrom']")
    private WebElement dateFromFilter;

    @FindBy(xpath = "//input[@type='date' and @name='dateTo']")
    private WebElement dateToFilter;

    @FindBy(xpath = "//select[contains(@name,'status')]")
    private WebElement statusFilterSelect;

    // Appointment Status
    @FindBy(xpath = "//span[contains(text(),'Scheduled')]")
    private WebElement scheduledStatus;

    @FindBy(xpath = "//span[contains(text(),'Completed')]")
    private WebElement completedStatus;

    @FindBy(xpath = "//span[contains(text(),'Cancelled')]")
    private WebElement cancelledStatus;

    @FindBy(xpath = "//span[contains(text(),'Rescheduled')]")
    private WebElement rescheduledStatus;

    // Success/Error Messages
    @FindBy(xpath = "//div[contains(text(),'Appointment booked successfully')]")
    private WebElement appointmentBookedMessage;

    @FindBy(xpath = "//div[contains(text(),'Appointment scheduled successfully')]")
    private WebElement appointmentScheduledMessage;

    @FindBy(xpath = "//div[contains(text(),'Confirmation sent to client')]")
    private WebElement confirmationSentMessage;

    @FindBy(xpath = "//div[contains(text(),'Appointment rescheduled successfully')]")
    private WebElement appointmentRescheduledMessage;

    @FindBy(xpath = "//div[contains(text(),'Appointment cancelled successfully')]")
    private WebElement appointmentCancelledMessage;

    @FindBy(xpath = "//div[contains(text(),'Outcome notes added successfully')]")
    private WebElement outcomeNotesAddedMessage;

    @FindBy(xpath = "//div[contains(text(),'No available time slots')]")
    private WebElement noAvailableSlotsError;

    @FindBy(xpath = "//div[contains(text(),'Appointment already exists')]")
    private WebElement appointmentAlreadyExistsError;

    /**
     * Constructor - initializes Page Factory elements
     *
     * @param driver WebDriver instance
     */
    public AppointmentBookingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("AppointmentBookingPage initialized");
    }

    /**
     * Check if appointment booking page is displayed
     *
     * @return boolean
     */
    public boolean isAppointmentBookingPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(bookAppointmentButton);
        } catch (Exception e) {
            logger.debug("Appointment booking page not displayed");
            return false;
        }
    }

    /**
     * Click book appointment button
     */
    public void clickBookAppointmentButton() {
        LogHelper.logStep("Clicking Book Appointment button");
        waitHelper.waitForElementClickable(bookAppointmentButton);
        click(bookAppointmentButton);
    }

    /**
     * Search and select client by name
     *
     * @param clientName Client name
     */
    public void selectClientByName(String clientName) {
        LogHelper.logStep("Selecting client: " + clientName);
        waitHelper.waitForElementVisible(clientSearchInput);
        enterText(clientSearchInput, clientName);
        // Wait for dropdown and select
        waitHelper.waitForElementVisible(clientListContainer);
    }

    /**
     * Select client from dropdown
     */
    public void clickClientSelectDropdown() {
        LogHelper.logStep("Clicking client select dropdown");
        waitHelper.waitForElementClickable(clientSelectDropdown);
        click(clientSelectDropdown);
    }

    /**
     * Select event type
     *
     * @param eventTypeName Event type name
     */
    public void selectEventType(String eventTypeName) {
        LogHelper.logStep("Selecting event type: " + eventTypeName);
        waitHelper.waitForElementClickable(eventTypeSelectDropdown);
        click(eventTypeSelectDropdown);
        waitHelper.waitForElementVisible(eventTypeListContainer);
    }

    /**
     * Select appointment date
     *
     * @param date Date in the format expected by the date picker
     */
    public void selectAppointmentDate(String date) {
        LogHelper.logStep("Selecting appointment date: " + date);
        waitHelper.waitForElementVisible(appointmentDateInput);
        enterText(appointmentDateInput, date);
    }

    /**
     * Select available time slot
     *
     * @param time Time in the format expected (e.g., "09:00 AM")
     */
    public void selectTimeSlot(String time) {
        LogHelper.logStep("Selecting time slot: " + time);
        waitHelper.waitForElementClickable(timeSelectDropdown);
        click(timeSelectDropdown);
        waitHelper.waitForElementVisible(timeSlotsContainer);
    }

    /**
     * Add appointment notes
     *
     * @param notes Appointment notes
     */
    public void addAppointmentNotes(String notes) {
        LogHelper.logStep("Adding appointment notes");
        waitHelper.waitForElementVisible(appointmentNotesInput);
        enterText(appointmentNotesInput, notes);
    }

    /**
     * Add appointment outcome notes
     *
     * @param notes Outcome notes
     */
    public void addOutcomeNotes(String notes) {
        LogHelper.logStep("Adding outcome notes");
        waitHelper.waitForElementVisible(outcomeNotesInput);
        enterText(outcomeNotesInput, notes);
    }

    /**
     * Enter appointment title
     *
     * @param title Appointment title
     */
    public void enterAppointmentTitle(String title) {
        LogHelper.logStep("Entering appointment title: " + title);
        waitHelper.waitForElementVisible(appointmentTitleInput);
        enterText(appointmentTitleInput, title);
    }

    /**
     * Click confirm booking button
     */
    public void clickConfirmBookingButton() {
        LogHelper.logStep("Clicking Confirm Booking button");
        waitHelper.waitForElementClickable(confirmBookingButton);
        click(confirmBookingButton);
    }

    /**
     * Click schedule button
     */
    public void clickScheduleButton() {
        LogHelper.logStep("Clicking Schedule button");
        waitHelper.waitForElementClickable(scheduleButton);
        click(scheduleButton);
    }

    /**
     * Book appointment for client
     *
     * @param clientName Client name
     * @param eventType Event type
     * @param date Appointment date
     * @param time Appointment time
     */
    public void bookAppointmentForClient(String clientName, String eventType, String date, String time) {
        LogHelper.logStep("Booking appointment for client: " + clientName);
        clickBookAppointmentButton();
        selectClientByName(clientName);
        selectEventType(eventType);
        selectAppointmentDate(date);
        selectTimeSlot(time);
        clickConfirmBookingButton();
    }

    /**
     * Book appointment with notes
     *
     * @param clientName Client name
     * @param eventType Event type
     * @param date Appointment date
     * @param time Appointment time
     * @param notes Appointment notes
     */
    public void bookAppointmentWithNotes(String clientName, String eventType, String date, String time, String notes) {
        LogHelper.logStep("Booking appointment with notes");
        clickBookAppointmentButton();
        selectClientByName(clientName);
        selectEventType(eventType);
        selectAppointmentDate(date);
        selectTimeSlot(time);
        addAppointmentNotes(notes);
        clickConfirmBookingButton();
    }

    /**
     * Check if appointment booked message is displayed
     *
     * @return boolean
     */
    public boolean isAppointmentBookedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(appointmentBookedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if appointment scheduled message is displayed
     *
     * @return boolean
     */
    public boolean isAppointmentScheduledMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(appointmentScheduledMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click send confirmation button
     */
    public void clickSendConfirmationButton() {
        LogHelper.logStep("Clicking Send Confirmation button");
        waitHelper.waitForElementClickable(sendConfirmationButton);
        click(sendConfirmationButton);
    }

    /**
     * Send appointment confirmation
     */
    public void sendAppointmentConfirmation() {
        LogHelper.logStep("Sending appointment confirmation");
        clickSendConfirmationButton();
    }

    /**
     * Check if confirmation sent message is displayed
     *
     * @return boolean
     */
    public boolean isConfirmationSentMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(confirmationSentMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * View booked appointments
     *
     * @return boolean - true if appointments table is displayed
     */
    public boolean viewBookedAppointments() {
        LogHelper.logStep("Viewing booked appointments");
        try {
            return waitHelper.isElementDisplayed(appointmentsTable);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click reschedule button
     */
    public void clickRescheduleButton() {
        LogHelper.logStep("Clicking Reschedule button");
        waitHelper.waitForElementClickable(rescheduleButton);
        click(rescheduleButton);
    }

    /**
     * Reschedule appointment
     *
     * @param newDate New appointment date
     * @param newTime New appointment time
     */
    public void rescheduleAppointment(String newDate, String newTime) {
        LogHelper.logStep("Rescheduling appointment to " + newDate + " at " + newTime);
        clickRescheduleButton();
        selectAppointmentDate(newDate);
        selectTimeSlot(newTime);
        clickConfirmBookingButton();
    }

    /**
     * Check if appointment rescheduled message is displayed
     *
     * @return boolean
     */
    public boolean isAppointmentRescheduledMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(appointmentRescheduledMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click cancel appointment button
     */
    public void clickCancelAppointmentButton() {
        LogHelper.logStep("Clicking Cancel Appointment button");
        waitHelper.waitForElementClickable(cancelAppointmentButton);
        click(cancelAppointmentButton);
    }

    /**
     * Cancel appointment
     */
    public void cancelAppointment() {
        LogHelper.logStep("Cancelling appointment");
        clickCancelAppointmentButton();
    }

    /**
     * Check if appointment cancelled message is displayed
     *
     * @return boolean
     */
    public boolean isAppointmentCancelledMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(appointmentCancelledMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click add outcome button
     */
    public void clickAddOutcomeButton() {
        LogHelper.logStep("Clicking Add Outcome button");
        waitHelper.waitForElementClickable(addOutcomeButton);
        click(addOutcomeButton);
    }

    /**
     * Add appointment outcome
     *
     * @param outcomeNotes Outcome notes
     */
    public void addAppointmentOutcome(String outcomeNotes) {
        LogHelper.logStep("Adding appointment outcome");
        clickAddOutcomeButton();
        addOutcomeNotes(outcomeNotes);
        clickConfirmBookingButton();
    }

    /**
     * Check if outcome notes added message is displayed
     *
     * @return boolean
     */
    public boolean isOutcomeNotesAddedMessageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(outcomeNotesAddedMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Switch to calendar view
     */
    public void switchToCalendarView() {
        LogHelper.logStep("Switching to calendar view");
        waitHelper.waitForElementClickable(calendarViewButton);
        click(calendarViewButton);
    }

    /**
     * Switch to list view
     */
    public void switchToListView() {
        LogHelper.logStep("Switching to list view");
        waitHelper.waitForElementClickable(listViewButton);
        click(listViewButton);
    }

    /**
     * Check if calendar is displayed
     *
     * @return boolean
     */
    public boolean isCalendarViewDisplayed() {
        try {
            return waitHelper.isElementDisplayed(calendarView);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Search appointments
     *
     * @param searchTerm Search term
     */
    public void searchAppointments(String searchTerm) {
        LogHelper.logStep("Searching appointments: " + searchTerm);
        waitHelper.waitForElementVisible(searchInput);
        enterText(searchInput, searchTerm);
    }

    /**
     * Filter appointments by status
     *
     * @param status Status to filter (Scheduled, Completed, Cancelled, etc.)
     */
    public void filterAppointmentsByStatus(String status) {
        LogHelper.logStep("Filtering appointments by status: " + status);
        waitHelper.waitForElementClickable(statusFilterSelect);
        click(statusFilterSelect);
    }

    /**
     * Check if no available slots error is displayed
     *
     * @return boolean
     */
    public boolean isNoAvailableSlotsErrorDisplayed() {
        try {
            return waitHelper.isElementDisplayed(noAvailableSlotsError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click cancel button
     */
    public void clickCancelButton() {
        LogHelper.logStep("Clicking Cancel button");
        waitHelper.waitForElementClickable(cancelButton);
        click(cancelButton);
    }
}
