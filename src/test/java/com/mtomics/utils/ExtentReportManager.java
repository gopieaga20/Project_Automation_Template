package com.mtomics.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * ExtentReportManager class manages ExtentReports generation
 */
public class ExtentReportManager {

    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ConfigReader configReader = new ConfigReader();

    /**
     * Initialize ExtentReports
     */
    public static void initializeReport() {
        if (extentReports == null) {
            try {
                logger.info("Initializing ExtentReports");

                String reportPath = configReader.getProperty("report.path");
                String reportName = configReader.getProperty("extent.report.name", "ExtentReport.html");

                // Create report directory if not exists
                File reportDir = new File(reportPath);
                if (!reportDir.exists()) {
                    reportDir.mkdirs();
                }

                String fullReportPath = reportPath + reportName;

                // Create ExtentSparkReporter
                ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fullReportPath);

                // Configure reporter
                sparkReporter.config().setDocumentTitle("MTOmics Automation Test Report");
                sparkReporter.config().setReportName("MTOmics Test Execution Report");
                sparkReporter.config().setTheme(Theme.STANDARD);
                sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
                sparkReporter.config().setEncoding("UTF-8");

                // Create ExtentReports instance
                extentReports = new ExtentReports();
                extentReports.attachReporter(sparkReporter);

                // Set system information
                extentReports.setSystemInfo("Application", "MTOmics");
                extentReports.setSystemInfo("Environment", configReader.getEnvironment());
                extentReports.setSystemInfo("Browser", configReader.getBrowser());
                extentReports.setSystemInfo("OS", System.getProperty("os.name"));
                extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
                extentReports.setSystemInfo("User", System.getProperty("user.name"));

                logger.info("ExtentReports initialized successfully: {}", fullReportPath);

            } catch (Exception e) {
                logger.error("Failed to initialize ExtentReports: {}", e.getMessage());
                throw new RuntimeException("ExtentReports initialization failed", e);
            }
        }
    }

    /**
     * Create a new test in the report
     * 
     * @param testName Test name
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName) {
        logger.info("Creating test in report: {}", testName);
        ExtentTest test = extentReports.createTest(testName);
        extentTest.set(test);
        return test;
    }

    /**
     * Create a new test with description
     * 
     * @param testName    Test name
     * @param description Test description
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description) {
        logger.info("Creating test in report: {} - {}", testName, description);
        ExtentTest test = extentReports.createTest(testName, description);
        extentTest.set(test);
        return test;
    }

    /**
     * Get current test
     * 
     * @return Current ExtentTest instance
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /**
     * Log info message
     * 
     * @param message Log message
     */
    public static void logInfo(String message) {
        if (extentTest.get() != null) {
            extentTest.get().log(Status.INFO, message);
        }
    }

    /**
     * Log pass message
     * 
     * @param message Log message
     */
    public static void logPass(String message) {
        if (extentTest.get() != null) {
            extentTest.get().log(Status.PASS, message);
        }
    }

    /**
     * Log fail message
     * 
     * @param message Log message
     */
    public static void logFail(String message) {
        if (extentTest.get() != null) {
            extentTest.get().log(Status.FAIL, message);
        }
    }

    /**
     * Log warning message
     * 
     * @param message Log message
     */
    public static void logWarning(String message) {
        if (extentTest.get() != null) {
            extentTest.get().log(Status.WARNING, message);
        }
    }

    /**
     * Log skip message
     * 
     * @param message Log message
     */
    public static void logSkip(String message) {
        if (extentTest.get() != null) {
            extentTest.get().log(Status.SKIP, message);
        }
    }

    /**
     * Attach screenshot to report
     * 
     * @param screenshotPath Screenshot file path
     */
    public static void attachScreenshot(String screenshotPath) {
        try {
            if (extentTest.get() != null) {
                extentTest.get().addScreenCaptureFromPath(screenshotPath);
                logger.debug("Screenshot attached to report: {}", screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Failed to attach screenshot: {}", e.getMessage());
        }
    }

    /**
     * Attach screenshot with title
     * 
     * @param screenshotPath Screenshot file path
     * @param title          Screenshot title
     */
    public static void attachScreenshot(String screenshotPath, String title) {
        try {
            if (extentTest.get() != null) {
                extentTest.get().addScreenCaptureFromPath(screenshotPath, title);
                logger.debug("Screenshot attached to report: {} - {}", title, screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Failed to attach screenshot: {}", e.getMessage());
        }
    }

    /**
     * Assign category to test
     * 
     * @param category Category name
     */
    public static void assignCategory(String category) {
        if (extentTest.get() != null) {
            extentTest.get().assignCategory(category);
        }
    }

    /**
     * Assign author to test
     * 
     * @param author Author name
     */
    public static void assignAuthor(String author) {
        if (extentTest.get() != null) {
            extentTest.get().assignAuthor(author);
        }
    }

    /**
     * Assign device to test
     * 
     * @param device Device name
     */
    public static void assignDevice(String device) {
        if (extentTest.get() != null) {
            extentTest.get().assignDevice(device);
        }
    }

    /**
     * Flush the report (write to file)
     */
    public static void flushReport() {
        if (extentReports != null) {
            logger.info("Flushing ExtentReports");
            extentReports.flush();
        }
    }

    /**
     * Remove current test from ThreadLocal
     */
    public static void removeTest() {
        extentTest.remove();
    }
}
