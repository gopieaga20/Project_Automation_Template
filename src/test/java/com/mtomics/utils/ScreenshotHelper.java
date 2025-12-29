package com.mtomics.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/**
 * ScreenshotHelper class provides methods for capturing screenshots
 */
public class ScreenshotHelper {

    private static final Logger logger = LogManager.getLogger(ScreenshotHelper.class);
    private WebDriver driver;
    private ConfigReader configReader;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public ScreenshotHelper(WebDriver driver) {
        this.driver = driver;
        this.configReader = new ConfigReader();
    }

    /**
     * Take screenshot and save to file
     * 
     * @param fileName Screenshot file name (without extension)
     * @return Screenshot file path
     */
    public String takeScreenshot(String fileName) {
        try {
            logger.info("Taking screenshot: {}", fileName);

            // Get screenshot as file
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Create destination path
            String screenshotPath = configReader.getProperty("screenshot.path");
            String timestamp = DateHelper.getTimestamp();
            String destinationPath = screenshotPath + fileName + "_" + timestamp + ".png";

            // Create directory if not exists
            File directory = new File(screenshotPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Copy file to destination
            File destinationFile = new File(destinationPath);
            FileUtils.copyFile(sourceFile, destinationFile);

            logger.info("Screenshot saved: {}", destinationPath);
            return destinationPath;

        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
            throw new RuntimeException("Screenshot capture failed", e);
        }
    }

    /**
     * Take screenshot with scenario name
     * 
     * @param scenarioName Scenario name
     * @return Screenshot file path
     */
    public String takeScreenshotForScenario(String scenarioName) {
        String sanitizedName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");
        return takeScreenshot(sanitizedName);
    }

    /**
     * Take screenshot as byte array (for Cucumber reports)
     * 
     * @return Screenshot as byte array
     */
    public byte[] takeScreenshotAsBytes() {
        try {
            logger.debug("Taking screenshot as bytes");
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            return screenshot.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to take screenshot as bytes: {}", e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Take screenshot as Base64 string
     * 
     * @return Screenshot as Base64 string
     */
    public String takeScreenshotAsBase64() {
        try {
            logger.debug("Taking screenshot as Base64");
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            return screenshot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger.error("Failed to take screenshot as Base64: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Take screenshot on failure
     * 
     * @param testName Test name
     * @return Screenshot file path
     */
    public String takeScreenshotOnFailure(String testName) {
        logger.warn("Test failed, capturing screenshot: {}", testName);
        return takeScreenshot("FAILED_" + testName);
    }

    /**
     * Delete screenshot file
     * 
     * @param filePath Screenshot file path
     * @return boolean indicating success
     */
    public boolean deleteScreenshot(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (deleted) {
                    logger.info("Screenshot deleted: {}", filePath);
                }
                return deleted;
            }
            return false;
        } catch (Exception e) {
            logger.error("Failed to delete screenshot: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Clean up old screenshots
     * 
     * @param daysOld Delete screenshots older than specified days
     */
    public void cleanupOldScreenshots(int daysOld) {
        try {
            logger.info("Cleaning up screenshots older than {} days", daysOld);
            String screenshotPath = configReader.getProperty("screenshot.path");
            File directory = new File(screenshotPath);

            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    long cutoffTime = System.currentTimeMillis() - (daysOld * 24L * 60 * 60 * 1000);
                    int deletedCount = 0;

                    for (File file : files) {
                        if (file.lastModified() < cutoffTime) {
                            if (file.delete()) {
                                deletedCount++;
                            }
                        }
                    }
                    logger.info("Deleted {} old screenshots", deletedCount);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to cleanup old screenshots: {}", e.getMessage());
        }
    }
}
