package com.mtomics.pages.admin;

import com.mtomics.pages.BasePage;
import com.mtomics.utils.LogHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * UserManagementPage - Page Factory implementation for User Management
 * Based on: apps/web/src/features/user-management/pages/list-users.tsx
 */
public class UserManagementPage extends BasePage {

    // Page Factory elements
    @FindBy(xpath = "//input[@placeholder='Search']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[contains(text(),'New Invite') or contains(text(),'Add User')]")
    private WebElement newInviteButton;

    // Role tabs
    @FindBy(xpath = "//div[contains(@class, 'text-base capitalize') and contains(text(),'Provider')]")
    private WebElement providerTab;

    @FindBy(xpath = "//div[contains(@class, 'text-base capitalize') and contains(text(),'Manager')]")
    private WebElement managerTab;

    @FindBy(xpath = "//div[contains(@class, 'text-base capitalize') and contains(text(),'Admin')]")
    private WebElement adminTab;

    @FindBy(xpath = "//div[contains(@class, 'text-base capitalize') and contains(text(),'Client')]")
    private WebElement clientTab;

    @FindBy(xpath = "//div[contains(@class, 'text-base capitalize') and contains(text(),'Pending')]")
    private WebElement pendingTab;

    // User table
    @FindBy(xpath = "//table")
    private WebElement userTable;

    @FindBy(xpath = "//button[@aria-label='Go to next page']")
    private WebElement nextPageButton;

    @FindBy(xpath = "//button[@aria-label='Go to previous page']")
    private WebElement previousPageButton;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public UserManagementPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        logger.info("UserManagementPage initialized");
    }

    /**
     * Check if user management page is displayed
     * 
     * @return boolean
     */
    public boolean isUserManagementPageDisplayed() {
        try {
            return waitHelper.isElementDisplayed(searchInput) &&
                    waitHelper.isElementDisplayed(newInviteButton);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click new invite button
     */
    public void clickNewInvite() {
        LogHelper.logStep("Clicking New Invite button");
        waitHelper.waitForElementClickable(newInviteButton);
        newInviteButton.click();
        logger.info("New Invite button clicked");
    }

    /**
     * Search for user
     * 
     * @param searchText Search text
     */
    public void searchUser(String searchText) {
        LogHelper.logStep("Searching for user: " + searchText);
        waitHelper.waitForElementVisible(searchInput);
        searchInput.clear();
        searchInput.sendKeys(searchText);
        logger.debug("Search text entered: {}", searchText);
    }

    /**
     * Click provider tab
     */
    public void clickProviderTab() {
        LogHelper.logStep("Clicking Provider tab");
        waitHelper.waitForElementClickable(providerTab);
        providerTab.click();
        logger.info("Provider tab clicked");
    }

    /**
     * Click manager tab
     */
    public void clickManagerTab() {
        LogHelper.logStep("Clicking Manager tab");
        waitHelper.waitForElementClickable(managerTab);
        managerTab.click();
        logger.info("Manager tab clicked");
    }

    /**
     * Click admin tab
     */
    public void clickAdminTab() {
        LogHelper.logStep("Clicking Admin tab");
        waitHelper.waitForElementClickable(adminTab);
        adminTab.click();
        logger.info("Admin tab clicked");
    }

    /**
     * Click client tab
     */
    public void clickClientTab() {
        LogHelper.logStep("Clicking Client tab");
        waitHelper.waitForElementClickable(clientTab);
        clientTab.click();
        logger.info("Client tab clicked");
    }

    /**
     * Click pending invitations tab
     */
    public void clickPendingTab() {
        LogHelper.logStep("Clicking Pending tab");
        waitHelper.waitForElementClickable(pendingTab);
        pendingTab.click();
        logger.info("Pending tab clicked");
    }

    /**
     * Check if user table is displayed
     * 
     * @return boolean
     */
    public boolean isUserTableDisplayed() {
        try {
            return waitHelper.isElementDisplayed(userTable);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click next page button
     */
    public void clickNextPage() {
        LogHelper.logStep("Clicking next page button");
        waitHelper.waitForElementClickable(nextPageButton);
        nextPageButton.click();
        logger.info("Next page button clicked");
    }

    /**
     * Click previous page button
     */
    public void clickPreviousPage() {
        LogHelper.logStep("Clicking previous page button");
        waitHelper.waitForElementClickable(previousPageButton);
        previousPageButton.click();
        logger.info("Previous page button clicked");
    }
}
