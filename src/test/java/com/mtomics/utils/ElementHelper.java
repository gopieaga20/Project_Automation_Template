package com.mtomics.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * ElementHelper class provides common element interaction methods
 */
public class ElementHelper {

    private static final Logger logger = LogManager.getLogger(ElementHelper.class);
    private WebDriver driver;
    private WaitHelper waitHelper;
    private Actions actions;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public ElementHelper(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        this.actions = new Actions(driver);
    }

    /**
     * Click on element with wait
     * 
     * @param locator Element locator
     */
    public void click(WebElement element) {
        try {
            logger.info("Clicking element: {}", element);
            waitHelper.waitForElementClickable(element).click();
            logger.debug("Element clicked successfully");
        } catch (Exception e) {
            logger.error("Failed to click element: {}", element);
            throw e;
        }
    }

    /**
     * Double click on element
     * 
     * @param locator Element locator
     */
    public void doubleClick(WebElement element) {
        try {
            logger.info("Double clicking element: {}", element);
            actions.doubleClick(element).perform();
            logger.debug("Element double clicked successfully");
        } catch (Exception e) {
            logger.error("Failed to double click element: {}", element);
            throw e;
        }
    }

    /**
     * Right click on element
     * 
     * @param locator Element locator
     */
    public void rightClick(WebElement element) {
        try {
            logger.info("Right clicking element: {}", element);
            actions.contextClick(element).perform();
            logger.debug("Element right clicked successfully");
        } catch (Exception e) {
            logger.error("Failed to right click element: {}", element);
            throw e;
        }
    }

    /**
     * Enter text in element
     * 
     * @param locator Element locator
     * @param text    Text to enter
     */
    public void enterText(WebElement element, String text) {
        try {
            logger.info("Entering text '{}' in element: {}", text, element);
            element.clear();
            element.sendKeys(text);
            logger.debug("Text entered successfully");
        } catch (Exception e) {
            logger.error("Failed to enter text in element: {}", element);
            throw e;
        }
    }

    /**
     * Clear text from element
     * 
     * @param locator Element locator
     */
    public void clearText(WebElement element) {
        try {
            logger.info("Clearing text from element: {}", element);
            element.clear();
            logger.debug("Text cleared successfully");
        } catch (Exception e) {
            logger.error("Failed to clear text from element: {}", element);
            throw e;
        }
    }

    /**
     * Get text from element
     * 
     * @param locator Element locator
     * @return Element text
     */
    public String getText(WebElement element) {
        try {
            logger.info("Getting text from element: {}", element);
            String text = element.getText();
            logger.debug("Retrieved text: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", element);
            throw e;
        }
    }

    /**
     * Get attribute value from element
     * 
     * @param locator   Element locator
     * @param attribute Attribute name
     * @return Attribute value
     */
    public String getAttribute(WebElement element, String attribute) {
        try {
            logger.info("Getting attribute '{}' from element: {}", attribute, element);
            String value = element.getAttribute(attribute);
            logger.debug("Attribute value: {}", value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get attribute from element: {}", element);
            throw e;
        }
    }

    /**
     * Check if element is displayed
     * 
     * @param locator Element locator
     * @return boolean
     */
    public boolean isDisplayed(WebElement element) {
        try {
            return waitHelper.isElementDisplayed(element);
        } catch (Exception e) {
            logger.debug("Element not displayed: {}", element);
            return false;
        }
    }

    /**
     * Check if element is enabled
     * 
     * @param locator Element locator
     * @return boolean
     */
    public boolean isEnabled(WebElement element) {
        try {
            boolean enabled = element.isEnabled();
            logger.debug("Element enabled status: {}", enabled);
            return enabled;
        } catch (Exception e) {
            logger.debug("Element not enabled: {}", element);
            return false;
        }
    }

    /**
     * Check if element is selected (for checkboxes/radio buttons)
     * 
     * @param locator Element locator
     * @return boolean
     */
    public boolean isSelected(WebElement element) {
        try {
            boolean selected = element.isSelected();
            logger.debug("Element selected status: {}", selected);
            return selected;
        } catch (Exception e) {
            logger.debug("Element not selected: {}", element);
            return false;
        }
    }

    /**
     * Hover over element
     * 
     * @param locator Element locator
     */
    public void hoverOverElement(WebElement element) {
        try {
            logger.info("Hovering over element: {}", element);
            actions.moveToElement(element).perform();
            logger.debug("Hovered over element successfully");
        } catch (Exception e) {
            logger.error("Failed to hover over element: {}", element);
            throw e;
        }
    }

    /**
     * Drag and drop element
     * 
     * @param sourceLocator Source element locator
     * @param targetLocator Target element locator
     */
    public void dragAndDrop(WebElement sourceLocator, WebElement targetLocator) {
        try {
            logger.info("Dragging element {} to {}", sourceLocator, targetLocator);
            actions.dragAndDrop(sourceLocator, targetLocator).perform();
            logger.debug("Drag and drop completed successfully");
        } catch (Exception e) {
            logger.error("Failed to drag and drop elements");
            throw e;
        }
    }

    /**
     * Get CSS value from element
     * 
     * @param locator      Element locator
     * @param propertyName CSS property name
     * @return CSS value
     */
    public String getCssValue(WebElement element, String propertyName) {
        try {
            logger.info("Getting CSS value '{}' from element: {}", propertyName, element);
            String value = element.getCssValue(propertyName);
            logger.debug("CSS value: {}", value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get CSS value from element: {}", element);
            throw e;
        }
    }

    /**
     * Check if element exists in DOM
     * 
     * @param locator Element locator
     * @return boolean
     */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get element count
     * 
     * @param locator Element locator
     * @return Number of elements
     */
    public int getElementCount(By locator) {
        try {
            int count = driver.findElements(locator).size();
            logger.debug("Element count for {}: {}", locator, count);
            return count;
        } catch (Exception e) {
            logger.error("Failed to get element count: {}", locator);
            return 0;
        }
    }
}
