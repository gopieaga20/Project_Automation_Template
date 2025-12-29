package com.mtomics.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitHelper class provides explicit wait methods
 */
public class WaitHelper {

    private static final Logger logger = LogManager.getLogger(WaitHelper.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private int explicitWaitTime;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        ConfigReader configReader = new ConfigReader();
        this.explicitWaitTime = Integer.parseInt(configReader.getProperty("explicit.wait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
    }

    /**
     * Wait for element to be visible
     * 
     * @param editEmailTemplateButton Element locator
     * @return WebElement
     */
    public WebElement waitForElementVisible(WebElement editEmailTemplateButton) {
        try {
            logger.debug("Waiting for element to be visible: {}", editEmailTemplateButton);
            return wait.until(ExpectedConditions.visibilityOf(editEmailTemplateButton));
        } catch (Exception e) {
            logger.error("Element not visible: {}", editEmailTemplateButton);
            throw e;
        }
    }

    /**
     * Wait for element to be clickable
     * 
     * @param locator Element locator
     * @return WebElement
     */
    public WebElement waitForElementClickable(WebElement locator) {
        try {
            logger.debug("Waiting for element to be clickable: {}", locator);
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable: {}", locator);
            throw e;
        }
    }

    /**
     * Wait for element to be present
     * 
     * @param locator Element locator
     * @return WebElement
     */
    public WebElement waitForElementPresent(WebElement locator) {
        try {
            logger.debug("Waiting for element to be present: {}", locator);
            return wait.until(ExpectedConditions.visibilityOf(locator));
        } catch (Exception e) {
            logger.error("Element not present: {}", locator);
            throw e;
        }
    }

    /**
     * Wait for element to be invisible
     * 
     * @param locator Element locator
     * @return boolean
     */
    public boolean waitForElementInvisible(WebElement locator) {
        try {
            logger.debug("Waiting for element to be invisible: {}", locator);
            return wait.until(ExpectedConditions.invisibilityOf(locator));
        } catch (Exception e) {
            logger.error("Element still visible: {}", locator);
            return false;
        }
    }

    /**
     * Wait for text to be present in element
     * 
     * @param locator Element locator
     * @param text    Expected text
     * @return boolean
     */
    public boolean waitForTextPresent(WebElement locator, String text) {
        try {
            logger.debug("Waiting for text '{}' in element: {}", text, locator);
            return wait.until(ExpectedConditions.textToBePresentInElement(locator, text));
        } catch (Exception e) {
            logger.error("Text '{}' not found in element: {}", text, locator);
            return false;
        }
    }

    /**
     * Wait for alert to be present
     * 
     * @return Alert
     */
    public org.openqa.selenium.Alert waitForAlert() {
        try {
            logger.debug("Waiting for alert");
            return wait.until(ExpectedConditions.alertIsPresent());
        } catch (Exception e) {
            logger.error("Alert not present");
            throw e;
        }
    }

    /**
     * Custom wait with specific timeout
     * 
     * @param locator          Element locator
     * @param timeoutInSeconds Timeout in seconds
     * @return WebElement
     */
    public WebElement waitForElement(WebElement locator, int timeoutInSeconds) {
        try {
            logger.debug("Waiting for element with custom timeout {} seconds: {}", timeoutInSeconds, locator);
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return customWait.until(ExpectedConditions.visibilityOf(locator));
        } catch (Exception e) {
            logger.error("Element not found with timeout {} seconds: {}", timeoutInSeconds, locator);
            throw e;
        }
    }

    /**
     * Check if element is displayed
     * 
     * @param locator Element locator
     * @return boolean
     */
    public boolean isElementDisplayed(WebElement locator) {
        try {
            return waitForElementVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Hard wait (use sparingly)
     * 
     * @param milliseconds Time in milliseconds
     */
    public void hardWait(long milliseconds) {
        try {
            logger.warn("Using hard wait for {} milliseconds", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Hard wait interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
