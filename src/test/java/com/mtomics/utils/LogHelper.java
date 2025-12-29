package com.mtomics.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LogHelper class provides centralized logging methods
 */
public class LogHelper {

    private static final Logger logger = LogManager.getLogger(LogHelper.class);

    /**
     * Log info message
     * 
     * @param message Log message
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Log info message with parameters
     * 
     * @param message Log message with placeholders
     * @param params  Parameters
     */
    public static void info(String message, Object... params) {
        logger.info(message, params);
    }

    /**
     * Log debug message
     * 
     * @param message Log message
     */
    public static void debug(String message) {
        logger.debug(message);
    }

    /**
     * Log debug message with parameters
     * 
     * @param message Log message with placeholders
     * @param params  Parameters
     */
    public static void debug(String message, Object... params) {
        logger.debug(message, params);
    }

    /**
     * Log warning message
     * 
     * @param message Log message
     */
    public static void warn(String message) {
        logger.warn(message);
    }

    /**
     * Log warning message with parameters
     * 
     * @param message Log message with placeholders
     * @param params  Parameters
     */
    public static void warn(String message, Object... params) {
        logger.warn(message, params);
    }

    /**
     * Log error message
     * 
     * @param message Log message
     */
    public static void error(String message) {
        logger.error(message);
    }

    /**
     * Log error message with parameters
     * 
     * @param message Log message with placeholders
     * @param params  Parameters
     */
    public static void error(String message, Object... params) {
        logger.error(message, params);
    }

    /**
     * Log error with exception
     * 
     * @param message   Log message
     * @param throwable Exception
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Log fatal message
     * 
     * @param message Log message
     */
    public static void fatal(String message) {
        logger.fatal(message);
    }

    /**
     * Log fatal message with exception
     * 
     * @param message   Log message
     * @param throwable Exception
     */
    public static void fatal(String message, Throwable throwable) {
        logger.fatal(message, throwable);
    }

    /**
     * Log test step
     * 
     * @param stepDescription Step description
     */
    public static void logStep(String stepDescription) {
        logger.info("STEP: {}", stepDescription);
        ExtentReportManager.logInfo("STEP: " + stepDescription);
    }

    /**
     * Log test assertion
     * 
     * @param assertionDescription Assertion description
     * @param passed               Pass/fail status
     */
    public static void logAssertion(String assertionDescription, boolean passed) {
        if (passed) {
            logger.info("ASSERTION PASSED: {}", assertionDescription);
            ExtentReportManager.logPass("ASSERTION PASSED: " + assertionDescription);
        } else {
            logger.error("ASSERTION FAILED: {}", assertionDescription);
            ExtentReportManager.logFail("ASSERTION FAILED: " + assertionDescription);
        }
    }

    /**
     * Log test start
     * 
     * @param testName Test name
     */
    public static void logTestStart(String testName) {
        logger.info("========================================");
        logger.info("TEST STARTED: {}", testName);
        logger.info("========================================");
    }

    /**
     * Log test end
     * 
     * @param testName Test name
     * @param status   Test status
     */
    public static void logTestEnd(String testName, String status) {
        logger.info("========================================");
        logger.info("TEST ENDED: {} - Status: {}", testName, status);
        logger.info("========================================");
    }

    /**
     * Log scenario start
     * 
     * @param scenarioName Scenario name
     */
    public static void logScenarioStart(String scenarioName) {
        logger.info(">>> SCENARIO STARTED: {}", scenarioName);
    }

    /**
     * Log scenario end
     * 
     * @param scenarioName Scenario name
     * @param status       Scenario status
     */
    public static void logScenarioEnd(String scenarioName, String status) {
        logger.info("<<< SCENARIO ENDED: {} - Status: {}", scenarioName, status);
    }

    /**
     * Log API request
     * 
     * @param method HTTP method
     * @param url    Request URL
     */
    public static void logApiRequest(String method, String url) {
        logger.info("API REQUEST: {} {}", method, url);
    }

    /**
     * Log API response
     * 
     * @param statusCode   Response status code
     * @param responseTime Response time in ms
     */
    public static void logApiResponse(int statusCode, long responseTime) {
        logger.info("API RESPONSE: Status Code: {}, Response Time: {}ms", statusCode, responseTime);
    }

    /**
     * Log database query
     * 
     * @param query SQL query
     */
    public static void logDatabaseQuery(String query) {
        logger.debug("DATABASE QUERY: {}", query);
    }

    /**
     * Log configuration value
     * 
     * @param key   Configuration key
     * @param value Configuration value
     */
    public static void logConfig(String key, String value) {
        logger.debug("CONFIG: {} = {}", key, value);
    }
}
