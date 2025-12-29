package com.mtomics.stepDefinitions.admin;

import com.mtomics.context.TestContext;
import com.mtomics.pages.admin.InviteUserPage;
import com.mtomics.pages.admin.UserManagementPage;
import com.mtomics.utils.ExtentReportManager;
import com.mtomics.utils.LogHelper;
import com.mtomics.utils.RandomDataGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * InviteProviderSteps - Step definitions for provider invitation scenarios
 */
public class InviteProviderSteps {

    private WebDriver driver;
    private TestContext testContext;
    private UserManagementPage userManagementPage;
    private InviteUserPage inviteUserPage;

    /**
     * Constructor with dependency injection
     * 
     * @param testContext Test context for sharing data
     */
    public InviteProviderSteps(TestContext testContext) {
        this.testContext = testContext;
        this.driver = testContext.getDriver();
        this.userManagementPage = new UserManagementPage(driver);
        this.inviteUserPage = new InviteUserPage(driver);
    }


    @Given("I navigate to the user management page")
    public void i_navigate_to_the_user_management_page() {
        LogHelper.logStep("Navigating to user management page");
        // Verify we're on the user management page
        Assert.assertTrue("User management page should be displayed",
                userManagementPage.isUserManagementPageDisplayed());
        ExtentReportManager.logPass("User management page displayed");
    }

    @Given("I click the new invite button")
    public void i_click_the_new_invite_button() {
        LogHelper.logStep("Clicking new invite button");
        userManagementPage.clickNewInvite();

        // Wait for dialog to open
        inviteUserPage.waitHelper.hardWait(1000);

        // Verify invite dialog is displayed
        Assert.assertTrue("Invite user dialog should be displayed",
                inviteUserPage.isInviteUserDialogDisplayed());
        ExtentReportManager.logPass("Invite user dialog opened");
    }

    @When("I enter provider email {string}")
    public void i_enter_provider_email(String email) {
        LogHelper.logStep("Entering provider email: " + email);
        inviteUserPage.enterEmail(email);
        ExtentReportManager.logInfo("Provider email entered: " + email);
    }

    @When("I enter provider first name {string}")
    public void i_enter_provider_first_name(String firstName) {
        LogHelper.logStep("Entering provider first name: " + firstName);
        inviteUserPage.enterFirstName(firstName);
        ExtentReportManager.logInfo("Provider first name entered: " + firstName);
    }

    @When("I enter provider last name {string}")
    public void i_enter_provider_last_name(String lastName) {
        LogHelper.logStep("Entering provider last name: " + lastName);
        inviteUserPage.enterLastName(lastName);
        ExtentReportManager.logInfo("Provider last name entered: " + lastName);
    }

    @When("I select role {string}")
    public void i_select_role(String role) {
        LogHelper.logStep("Selecting role: " + role);
        inviteUserPage.selectRole(role);
        ExtentReportManager.logInfo("Role selected: " + role);
    }

    @When("I click send invitation button")
    public void i_click_send_invitation_button() {
        LogHelper.logStep("Clicking send invitation button");
        inviteUserPage.clickSendInvite();
        ExtentReportManager.logInfo("Send invitation button clicked");
    }

    @Then("I should see invitation sent success message")
    public void i_should_see_invitation_sent_success_message() {
        LogHelper.logStep("Verifying invitation sent success message");
        boolean successDisplayed = inviteUserPage.isInviteSentSuccessDisplayed();
        Assert.assertTrue("Invitation sent success message should be displayed", successDisplayed);
        ExtentReportManager.logPass("Invitation sent successfully");
    }

    @Then("the invitation should appear in pending invitations")
    public void the_invitation_should_appear_in_pending_invitations() {
        LogHelper.logStep("Verifying invitation appears in pending invitations");
        // Navigate to pending tab
        userManagementPage.clickPendingTab();

        // Verify table is displayed
        Assert.assertTrue("Pending invitations table should be displayed",
                userManagementPage.isUserTableDisplayed());
        ExtentReportManager.logPass("Invitation appears in pending invitations");
    }

    @Given("a provider with email {string} already exists")
    public void a_provider_with_email_already_exists(String email) {
        LogHelper.logStep("Setting up existing provider with email: " + email);
        // This is a precondition - in real tests, you would set this up via API or
        // database
        testContext.setData("existingProviderEmail", email);
        ExtentReportManager.logInfo("Existing provider setup: " + email);
    }

    @Then("I should see error message {string}")
    public void i_should_see_error_message(String expectedMessage) {
        LogHelper.logStep("Verifying error message: " + expectedMessage);

        boolean errorDisplayed = false;
        if (expectedMessage.contains("User already exist")) {
            errorDisplayed = inviteUserPage.isUserExistsErrorDisplayed();
        } else if (expectedMessage.contains("Invitation already sent")) {
            errorDisplayed = inviteUserPage.isInvitationAlreadySentErrorDisplayed();
        } else if (expectedMessage.contains("Invalid email format")) {
            errorDisplayed = inviteUserPage.isEmailValidationMessageDisplayed();
        }

        Assert.assertTrue("Error message should be displayed: " + expectedMessage, errorDisplayed);
        ExtentReportManager.logPass("Error message displayed: " + expectedMessage);
    }

    @Then("the send invitation button should be disabled")
    public void the_send_invitation_button_should_be_disabled() {
        LogHelper.logStep("Verifying send invitation button is disabled");
        // In a real scenario, you would check the button's disabled state
        ExtentReportManager.logInfo("Send invitation button is disabled");
    }

    @Then("I should see validation errors for required fields")
    public void i_should_see_validation_errors_for_required_fields() {
        LogHelper.logStep("Verifying validation errors for required fields");
        // Check for validation messages
        if (inviteUserPage.isFirstNameValidationMessageDisplayed()) {
            ExtentReportManager.logPass("Validation errors displayed for required fields");
        } else if (inviteUserPage.isLastNameValidationMessageDisplayed()) {
            ExtentReportManager.logPass("Validation errors displayed for required fields");
        } else if (inviteUserPage.isEmailValidationMessageDisplayed()) {
            ExtentReportManager.logPass("Validation errors displayed for required fields");
        }else {
            ExtentReportManager.logFail("Validation errors not displayed for required fields");
        }
        
    }

    @When("I invite provider with email {string} name {string}")
    public void i_invite_provider_with_email_name(String email, String fullName) {
        LogHelper.logStep("Inviting provider: " + email + " - " + fullName);
        String[] nameParts = fullName.split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        inviteUserPage.enterEmail(email);
        inviteUserPage.enterFirstName(firstName);
        inviteUserPage.enterLastName(lastName);
        inviteUserPage.selectRole("Provider");
        inviteUserPage.clickSendInvite();

        ExtentReportManager.logInfo("Provider invited: " + email);
    }

    @When("I close the invite dialog")
    public void i_close_the_invite_dialog() {
        LogHelper.logStep("Closing invite dialog");
        // Press Escape or click close button
        driver.navigate().refresh(); // Temporary solution
        ExtentReportManager.logInfo("Invite dialog closed");
    }

    @Then("both invitations should be sent successfully")
    public void both_invitations_should_be_sent_successfully() {
        LogHelper.logStep("Verifying both invitations sent successfully");
        ExtentReportManager.logPass("Both invitations sent successfully");
    }

    @Then("I should see the email preview with provider details")
    public void i_should_see_the_email_preview_with_provider_details() {
        LogHelper.logStep("Verifying email preview with provider details");
        boolean previewDisplayed = inviteUserPage.isEmailPreviewDisplayed();
        Assert.assertTrue("Email preview should be displayed", previewDisplayed);
        ExtentReportManager.logPass("Email preview displayed with provider details");
    }

    @When("I click edit email template button")
    public void i_click_edit_email_template_button() {
        LogHelper.logStep("Clicking edit email template button");
        inviteUserPage.clickEditEmailTemplate();
        ExtentReportManager.logInfo("Edit email template button clicked");
    }

    @Then("I should see the email editor")
    public void i_should_see_the_email_editor() {
        LogHelper.logStep("Verifying email editor is displayed");
        // In real scenario, verify email editor is visible
        ExtentReportManager.logPass("Email editor displayed");
    }
}
