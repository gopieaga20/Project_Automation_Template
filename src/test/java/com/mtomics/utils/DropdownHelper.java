package com.mtomics.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * DropdownHelper class provides methods for handling dropdown elements
 */
public class DropdownHelper {

    private static final Logger logger = LogManager.getLogger(DropdownHelper.class);
    private WebDriver driver;
    private WaitHelper waitHelper;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public DropdownHelper(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    /**
     * Select dropdown option by visible text
     * 
     * @param locator Dropdown locator
     * @param text    Visible text
     */
    public void selectByVisibleText(WebElement element, String text) {
        try {
            logger.info("Selecting dropdown option by text: {}", text);
            Select select = new Select(element);
            select.selectByVisibleText(text);
            logger.debug("Option selected successfully");
        } catch (Exception e) {
            logger.error("Failed to select option by text: {}", text);
            throw e;
        }
    }

    /**
     * Select dropdown option by value
     * 
     * @param locator Dropdown locator
     * @param value   Option value
     */
    public void selectByValue(WebElement element, String value) {
        try {
            logger.info("Selecting dropdown option by value: {}", value);
            Select select = new Select(element);
            select.selectByValue(value);
            logger.debug("Option selected successfully");
        } catch (Exception e) {
            logger.error("Failed to select option by value: {}", value);
            throw e;
        }
    }

    /**
     * Select dropdown option by index
     * 
     * @param locator Dropdown locator
     * @param index   Option index
     */
    public void selectByIndex(WebElement element, int index) {
        try {
            logger.info("Selecting dropdown option by index: {}", index);
            Select select = new Select(element);
            select.selectByIndex(index);
            logger.debug("Option selected successfully");
        } catch (Exception e) {
            logger.error("Failed to select option by index: {}", index);
            throw e;
        }
    }

    /**
     * Get selected option text
     * 
     * @param locator Dropdown locator
     * @return Selected option text
     */
    public String getSelectedOptionText(WebElement element) {
        try {
            logger.info("Getting selected option text");
            Select select = new Select(element);
            String text = select.getFirstSelectedOption().getText();
            logger.debug("Selected option: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get selected option text");
            throw e;
        }
    }

    /**
     * Get selected option value
     * 
     * @param locator Dropdown locator
     * @return Selected option value
     */
    public String getSelectedOptionValue(WebElement element) {
        try {
            logger.info("Getting selected option value");
            Select select = new Select(element);
            String value = select.getFirstSelectedOption().getAttribute("value");
            logger.debug("Selected value: {}", value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get selected option value");
            throw e;
        }
    }

    /**
     * Get all dropdown options
     * 
     * @param locator Dropdown locator
     * @return List of option texts
     */
    public List<String> getAllOptions(WebElement element) {
        try {
            logger.info("Getting all dropdown options");
            Select select = new Select(element);
            List<WebElement> options = select.getOptions();
            List<String> optionTexts = new ArrayList<>();
            for (WebElement option : options) {
                optionTexts.add(option.getText());
            }
            logger.debug("Total options: {}", optionTexts.size());
            return optionTexts;
        } catch (Exception e) {
            logger.error("Failed to get all options");
            throw e;
        }
    }

    /**
     * Get dropdown options count
     * 
     * @param locator Dropdown locator
     * @return Number of options
     */
    public int getOptionsCount(WebElement element) {
        try {
            logger.info("Getting dropdown options count");
            Select select = new Select(element);
            int count = select.getOptions().size();
            logger.debug("Options count: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Failed to get options count");
            throw e;
        }
    }

    /**
     * Check if dropdown is multi-select
     * 
     * @param locator Dropdown locator
     * @return boolean
     */
    public boolean isMultiSelect(WebElement element) {
        try {
            Select select = new Select(element);
            return select.isMultiple();
        } catch (Exception e) {
            logger.error("Failed to check if dropdown is multi-select");
            throw e;
        }
    }

    /**
     * Deselect all options (for multi-select dropdowns)
     * 
     * @param locator Dropdown locator
     */
    public void deselectAll(WebElement element) {
        try {
            logger.info("Deselecting all options");
            Select select = new Select(element);
            if (select.isMultiple()) {
                select.deselectAll();
                logger.debug("All options deselected");
            } else {
                logger.warn("Cannot deselect all - dropdown is not multi-select");
            }
        } catch (Exception e) {
            logger.error("Failed to deselect all options");
            throw e;
        }
    }

    /**
     * Deselect option by visible text
     * 
     * @param locator Dropdown locator
     * @param text    Visible text
     */
    public void deselectByVisibleText(WebElement element, String text) {
        try {
            logger.info("Deselecting option by text: {}", text);
            Select select = new Select(element);
            select.deselectByVisibleText(text);
            logger.debug("Option deselected successfully");
        } catch (Exception e) {
            logger.error("Failed to deselect option by text: {}", text);
            throw e;
        }
    }

    /**
     * Check if option exists in dropdown
     * 
     * @param locator    Dropdown locator
     * @param optionText Option text to check
     * @return boolean
     */
    public boolean isOptionPresent(WebElement element, String optionText) {
        try {
            List<String> options = getAllOptions(element);
            return options.contains(optionText);
        } catch (Exception e) {
            logger.error("Failed to check if option exists");
            return false;
        }
    }
}
