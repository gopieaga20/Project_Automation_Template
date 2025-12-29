package com.mtomics.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * DriverManager class handles WebDriver initialization and management
 * Implements ThreadLocal for parallel execution support
 */
public class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ConfigReader configReader = new ConfigReader();

    /**
     * Initialize WebDriver based on browser configuration
     */
    public static void initializeDriver() {
        String browser = configReader.getProperty("browser");
        boolean headless = Boolean.parseBoolean(configReader.getProperty("headless"));
        boolean maximize = Boolean.parseBoolean(configReader.getProperty("maximize"));

        logger.info("Initializing {} driver in {} mode", browser, headless ? "headless" : "normal");

        try {
            WebDriver webDriver = null;

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless=new");
                    }
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    webDriver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    webDriver = new FirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headless) {
                        edgeOptions.addArguments("--headless");
                    }
                    webDriver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    logger.error("Invalid browser: {}. Defaulting to Chrome", browser);
                    WebDriverManager.chromedriver().setup();
                    webDriver = new ChromeDriver();
            }

            // Set timeouts
            int implicitWait = Integer.parseInt(configReader.getProperty("implicit.wait"));
            int pageLoadTimeout = Integer.parseInt(configReader.getProperty("page.load.timeout"));

            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));

            // Maximize window if configured
            if (maximize) {
                webDriver.manage().window().maximize();
            }

            driver.set(webDriver);
            logger.info("Driver initialized successfully");

        } catch (Exception e) {
            logger.error("Failed to initialize driver: {}", e.getMessage());
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    /**
     * Get the current WebDriver instance
     * 
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }

    /**
     * Quit the WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                logger.info("Quitting driver");
                driver.get().quit();
                driver.remove();
                logger.info("Driver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting driver: {}", e.getMessage());
            }
        }
    }
}
