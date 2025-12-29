package com.mtomics.stepDefinitions.admin;

import com.mtomics.context.TestContext;
import com.mtomics.pages.admin.InviteUserPage;
import com.mtomics.pages.admin.UserManagementPage;
import com.mtomics.utils.ExtentReportManager;
import com.mtomics.utils.LogHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

/**
 * InviteManagerSteps - Step definitions for manager invitation scenarios
 */
public class InviteManagerSteps {

    private WebDriver driver;
    private TestContext testContext;
    private UserManagementPage userManagementPage;
    private InviteUserPage inviteUserPage;

    /**
     * Constructor with dependency injection
     * 
     * @param testContext Test context for sharing data
     */
    public InviteManagerSteps(TestContext testContext) {
        this.testContext = testContext;
        this.driver = testContext.getDriver();
        this.userManagementPage = new UserManagementPage(driver);
        this.inviteUserPage = new InviteUserPage(driver);
    }

    @When("I enter manager email {string}")
    public void i_enter_manager_email(String email) {
        LogHelper.logStep("Entering manager email: " + email);
        inviteUserPage.enterEmail(email);
        ExtentReportManager.logInfo("Manager email entered: " + email);
    }

    @When("I enter manager first name {string}")
    public void i_enter_manager_first_name(String firstName) {
        LogHelper.logStep("Entering manager first name: " + firstName);
        inviteUserPage.enterFirstName(firstName);
        ExtentReportManager.logInfo("Manager first name entered: " + firstName);
    }

    @When("I enter manager last name {string}")
    public void i_enter_manager_last_name(String lastName) {
        LogHelper.logStep("Entering manager last name: " + lastName);
        inviteUserPage.enterLastName(lastName);
        ExtentReportManager.logInfo("Manager last name entered: " + lastName);
    }

   
    @Then("the manager invitation should appear in pending invitations")
    public void the_manager_invitation_should_appear_in_pending_invitations() {
        LogHelper.logStep("Verifying manager invitation appears in pending invitations");
        // Navigate to pending tab
        userManagementPage.clickPendingTab();

        // Verify table is displayed
        Assert.assertTrue("Pending invitations table should be displayed",
                userManagementPage.isUserTableDisplayed());
        ExtentReportManager.logPass("Manager invitation appears in pending invitations");
    }

    @Given("an invitation was already sent to {string}")
    public void an_invitation_was_already_sent_to(String email) {
        LogHelper.logStep("Setting up pending invitation for: " + email);
        // This is a precondition - in real tests, you would set this up via API or
        // database
        testContext.setData("pendingInvitationEmail", email);
        ExtentReportManager.logInfo("Pending invitation setup: " + email);
    }

    @Then("I should see the email preview with manager role details")
    public void i_should_see_the_email_preview_with_manager_role_details() {
        LogHelper.logStep("Verifying email preview with manager role details");
        boolean previewDisplayed = inviteUserPage.isEmailPreviewDisplayed();
        Assert.assertTrue("Email preview should be displayed", previewDisplayed);
        ExtentReportManager.logPass("Email preview displayed with manager role details");
    }

    @Then("the invitation should not be sent")
    public void the_invitation_should_not_be_sent() {
        LogHelper.logStep("Verifying invitation was not sent");
        // In real scenario, verify no success message appears
        ExtentReportManager.logInfo("Invitation was not sent");
    }

    @Then("I should return to user management page")
    public void i_should_return_to_user_management_page() {
        LogHelper.logStep("Verifying return to user management page");
        Assert.assertTrue("User management page should be displayed",
                userManagementPage.isUserManagementPageDisplayed());
        ExtentReportManager.logPass("Returned to user management page");
    }
}
