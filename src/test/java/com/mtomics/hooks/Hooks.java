package com.mtomics.hooks;

import com.mtomics.context.TestContext;
import com.mtomics.utils.ConfigReader;
import com.mtomics.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Hooks class for Cucumber Before and After scenarios
 */
public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private TestContext context;
    private ConfigReader configReader;

    /**
     * Constructor with Dependency Injection
     * 
     * @param context TestContext
     */
    public Hooks(TestContext context) {
        this.context = context;
        this.configReader = new ConfigReader();
    }

    /**
     * Before scenario hook
     * 
     * @param scenario Cucumber scenario
     */
    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("========================================");
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());
        logger.info("========================================");

        // Initialize driver
        DriverManager.initializeDriver();
        WebDriver driver = DriverManager.getDriver();
        context.setDriver(driver);

        // Navigate to base URL
        String baseUrl = configReader.getBaseUrl();
        logger.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);
    }

    /**
     * After scenario hook
     * 
     * @param scenario Cucumber scenario
     */
    @After
    public void afterScenario(Scenario scenario) {
        logger.info("========================================");
        logger.info("Finishing scenario: {}", scenario.getName());
        logger.info("Status: {}", scenario.getStatus());
        logger.info("========================================");

        // Take screenshot if scenario failed
        if (scenario.isFailed()) {
            takeScreenshot(scenario);
        }

        // Clear context
        context.clearContext();

        // Quit driver
        DriverManager.quitDriver();
    }

    /**
     * Take screenshot and attach to report
     * 
     * @param scenario Cucumber scenario
     */
    private void takeScreenshot(Scenario scenario) {
        try {
            WebDriver driver = context.getDriver();
            if (driver != null) {
                logger.info("Taking screenshot for failed scenario: {}", scenario.getName());
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
                logger.info("Screenshot attached to report");
            }
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
        }
    }
}
