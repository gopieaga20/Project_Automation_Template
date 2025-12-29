package com.mtomics.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * JavaScriptHelper class provides JavaScript executor methods
 */
public class JavaScriptHelper {

    private static final Logger logger = LogManager.getLogger(JavaScriptHelper.class);
    private WebDriver driver;
    private JavascriptExecutor jsExecutor;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public JavaScriptHelper(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    /**
     * Execute JavaScript
     * 
     * @param script JavaScript code
     * @return Execution result
     */
    public Object executeScript(String script) {
        try {
            logger.debug("Executing JavaScript: {}", script);
            return jsExecutor.executeScript(script);
        } catch (Exception e) {
            logger.error("Failed to execute JavaScript: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Execute JavaScript with arguments
     * 
     * @param script JavaScript code
     * @param args   Arguments
     * @return Execution result
     */
    public Object executeScript(String script, Object... args) {
        try {
            logger.debug("Executing JavaScript with arguments: {}", script);
            return jsExecutor.executeScript(script, args);
        } catch (Exception e) {
            logger.error("Failed to execute JavaScript: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Click element using JavaScript
     * 
     * @param element WebElement
     */
    public void clickElement(WebElement element) {
        try {
            logger.info("Clicking element using JavaScript");
            jsExecutor.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            logger.error("Failed to click element using JavaScript");
            throw e;
        }
    }

    /**
     * Scroll to element
     * 
     * @param element WebElement
     */
    public void scrollToElement(WebElement element) {
        try {
            logger.info("Scrolling to element");
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            logger.error("Failed to scroll to element");
            throw e;
        }
    }

    /**
     * Scroll to bottom of page
     */
    public void scrollToBottom() {
        try {
            logger.info("Scrolling to bottom of page");
            jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (Exception e) {
            logger.error("Failed to scroll to bottom");
            throw e;
        }
    }

    /**
     * Scroll to top of page
     */
    public void scrollToTop() {
        try {
            logger.info("Scrolling to top of page");
            jsExecutor.executeScript("window.scrollTo(0, 0)");
        } catch (Exception e) {
            logger.error("Failed to scroll to top");
            throw e;
        }
    }

    /**
     * Scroll by pixels
     * 
     * @param x Horizontal pixels
     * @param y Vertical pixels
     */
    public void scrollByPixels(int x, int y) {
        try {
            logger.info("Scrolling by pixels: x={}, y={}", x, y);
            jsExecutor.executeScript("window.scrollBy(" + x + "," + y + ")");
        } catch (Exception e) {
            logger.error("Failed to scroll by pixels");
            throw e;
        }
    }

    /**
     * Highlight element (for debugging)
     * 
     * @param element WebElement
     */
    public void highlightElement(WebElement element) {
        try {
            logger.debug("Highlighting element");
            jsExecutor.executeScript(
                    "arguments[0].style.border='3px solid red'", element);
        } catch (Exception e) {
            logger.error("Failed to highlight element");
        }
    }

    /**
     * Remove highlight from element
     * 
     * @param element WebElement
     */
    public void removeHighlight(WebElement element) {
        try {
            logger.debug("Removing highlight from element");
            jsExecutor.executeScript(
                    "arguments[0].style.border=''", element);
        } catch (Exception e) {
            logger.error("Failed to remove highlight");
        }
    }

    /**
     * Get page title using JavaScript
     * 
     * @return Page title
     */
    public String getPageTitle() {
        return (String) jsExecutor.executeScript("return document.title;");
    }

    /**
     * Get inner text of element
     * 
     * @param element WebElement
     * @return Inner text
     */
    public String getInnerText(WebElement element) {
        return (String) jsExecutor.executeScript("return arguments[0].innerText;", element);
    }

    /**
     * Set value of element
     * 
     * @param element WebElement
     * @param value   Value to set
     */
    public void setValue(WebElement element, String value) {
        try {
            logger.info("Setting value using JavaScript: {}", value);
            jsExecutor.executeScript("arguments[0].value='" + value + "';", element);
        } catch (Exception e) {
            logger.error("Failed to set value using JavaScript");
            throw e;
        }
    }

    /**
     * Refresh page using JavaScript
     */
    public void refreshPage() {
        try {
            logger.info("Refreshing page using JavaScript");
            jsExecutor.executeScript("history.go(0)");
        } catch (Exception e) {
            logger.error("Failed to refresh page");
            throw e;
        }
    }

    /**
     * Get page height
     * 
     * @return Page height
     */
    public Long getPageHeight() {
        return (Long) jsExecutor.executeScript("return document.body.scrollHeight;");
    }

    /**
     * Get page width
     * 
     * @return Page width
     */
    public Long getPageWidth() {
        return (Long) jsExecutor.executeScript("return document.body.scrollWidth;");
    }

    /**
     * Check if page is loaded
     * 
     * @return boolean
     */
    public boolean isPageLoaded() {
        String state = (String) jsExecutor.executeScript("return document.readyState;");
        return "complete".equals(state);
    }

    /**
     * Open new tab
     */
    public void openNewTab() {
        try {
            logger.info("Opening new tab");
            jsExecutor.executeScript("window.open()");
        } catch (Exception e) {
            logger.error("Failed to open new tab");
            throw e;
        }
    }

    /**
     * Zoom page
     * 
     * @param percentage Zoom percentage (e.g., 80, 100, 120)
     */
    public void zoomPage(int percentage) {
        try {
            logger.info("Zooming page to {}%", percentage);
            jsExecutor.executeScript("document.body.style.zoom='" + percentage + "%'");
        } catch (Exception e) {
            logger.error("Failed to zoom page");
            throw e;
        }
    }
}
