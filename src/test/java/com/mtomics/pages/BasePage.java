package com.mtomics.pages;

import com.mtomics.utils.WaitHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * BasePage class contains common methods for all page objects
 */
public class BasePage {

    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    public WaitHelper waitHelper; // Changed to public for step definition access
    protected JavascriptExecutor jsExecutor;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Click on element
     * 
     * @param locator Element locator
     */
    protected void click(WebElement element) {
        try {
            logger.info("Clicking on element: {}", element);
            waitHelper.waitForElementClickable(element).click();
        } catch (Exception e) {
            logger.error("Failed to click on element: {}", element);
            throw e;
        }
    }

    /**
     * Enter text in element
     * 
     * @param locator Element locator
     * @param text    Text to enter
     */
    protected void enterText(WebElement element, String text) {
        try {
            logger.info("Entering text '{}' in element: {}", text, element);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            logger.error("Failed to enter text in element: {}", element);
            throw e;
        }
    }

    /**
     * Get text from element
     * 
     * @param locator Element locator
     * @return Element text
     */
    protected String getText(WebElement element) {
        try {
            logger.info("Getting text from element: {}", element);
            return waitHelper.waitForElementVisible(element).getText();
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", element);
            throw e;
        }
    }

    /**
     * Check if element is displayed
     * 
     * @param locator Element locator
     * @return boolean
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            return waitHelper.isElementDisplayed(element);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is enabled
     * 
     * @param locator Element locator
     * @return boolean
     */
    protected boolean isEnabled(WebElement element) {
        try {
            return waitHelper.waitForElementVisible(element).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scroll to element
     * 
     * @param locator Element locator
     */
    protected void scrollToElement(WebElement element) {
        try {
            logger.info("Scrolling to element: {}", element);
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            logger.error("Failed to scroll to element: {}", element);
            throw e;
        }
    }

    /**
     * Click using JavaScript
     * 
     * @param locator Element locator
     */
    protected void clickUsingJS(WebElement element) {
        try {
            logger.info("Clicking element using JavaScript: {}", element);
            jsExecutor.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            logger.error("Failed to click using JavaScript: {}", element);
            throw e;
        }
    }

    /**
     * Get page title
     * 
     * @return Page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get current URL
     * 
     * @return Current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Navigate to URL
     * 
     * @param url URL to navigate
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    /**
     * Refresh page
     */
    protected void refreshPage() {
        logger.info("Refreshing page");
        driver.navigate().refresh();
    }

    /**
     * Navigate back
     */
    protected void navigateBack() {
        logger.info("Navigating back");
        driver.navigate().back();
    }

    /**
     * Get attribute value
     * 
     * @param locator   Element locator
     * @param attribute Attribute name
     * @return Attribute value
     */
    protected String getAttribute(WebElement element, String attribute) {
        try {
            return waitHelper.waitForElementVisible(element).getAttribute(attribute);
        } catch (Exception e) {
            logger.error("Failed to get attribute '{}' from element: {}", attribute, element);
            throw e;
        }
    }
}
