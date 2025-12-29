package com.mtomics.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * AlertHelper class provides methods for handling JavaScript alerts
 */
public class AlertHelper {

    private static final Logger logger = LogManager.getLogger(AlertHelper.class);
    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public AlertHelper(WebDriver driver) {
        this.driver = driver;
        ConfigReader configReader = new ConfigReader();
        int timeout = Integer.parseInt(configReader.getProperty("explicit.wait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    /**
     * Check if alert is present
     * 
     * @return boolean
     */
    public boolean isAlertPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            logger.debug("Alert not present");
            return false;
        }
    }

    /**
     * Accept alert (click OK)
     */
    public void acceptAlert() {
        try {
            logger.info("Accepting alert");
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            logger.debug("Alert accepted");
        } catch (Exception e) {
            logger.error("Failed to accept alert: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Dismiss alert (click Cancel)
     */
    public void dismissAlert() {
        try {
            logger.info("Dismissing alert");
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
            logger.debug("Alert dismissed");
        } catch (Exception e) {
            logger.error("Failed to dismiss alert: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Get alert text
     * 
     * @return Alert text
     */
    public String getAlertText() {
        try {
            logger.info("Getting alert text");
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();
            logger.debug("Alert text: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get alert text: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Enter text in alert prompt
     * 
     * @param text Text to enter
     */
    public void enterTextInAlert(String text) {
        try {
            logger.info("Entering text in alert: {}", text);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.sendKeys(text);
            logger.debug("Text entered in alert");
        } catch (Exception e) {
            logger.error("Failed to enter text in alert: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Accept alert and get text
     * 
     * @return Alert text
     */
    public String acceptAlertAndGetText() {
        try {
            logger.info("Accepting alert and getting text");
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();
            alert.accept();
            logger.debug("Alert text: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to accept alert and get text: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Dismiss alert and get text
     * 
     * @return Alert text
     */
    public String dismissAlertAndGetText() {
        try {
            logger.info("Dismissing alert and getting text");
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();
            alert.dismiss();
            logger.debug("Alert text: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to dismiss alert and get text: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Handle alert if present (accept)
     * 
     * @return true if alert was present and handled
     */
    public boolean handleAlertIfPresent() {
        try {
            if (isAlertPresent()) {
                acceptAlert();
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Failed to handle alert: {}", e.getMessage());
            return false;
        }
    }
}
