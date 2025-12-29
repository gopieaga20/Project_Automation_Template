package com.mtomics.context;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * TestContext class for sharing data between step definitions
 * Implements Dependency Injection pattern for Cucumber
 */
public class TestContext {

    private WebDriver driver;
    private Map<String, Object> scenarioContext;

    /**
     * Constructor
     */
    public TestContext() {
        this.scenarioContext = new HashMap<>();
    }

    /**
     * Get WebDriver instance
     * 
     * @return WebDriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Set WebDriver instance
     * 
     * @param driver WebDriver
     */
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Set scenario context value
     * 
     * @param key   Context key
     * @param value Context value
     */
    public void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    /**
     * Get scenario context value
     * 
     * @param key Context key
     * @return Context value
     */
    public Object getContext(String key) {
        return scenarioContext.get(key);
    }

    /**
     * Check if context contains key
     * 
     * @param key Context key
     * @return boolean
     */
    public boolean containsContext(String key) {
        return scenarioContext.containsKey(key);
    }

    /**
     * Clear scenario context
     */
    public void clearContext() {
        scenarioContext.clear();
    }

    /**
     * Get context as String
     * 
     * @param key Context key
     * @return String value
     */
    public String getContextAsString(String key) {
        Object value = scenarioContext.get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * Get context as Integer
     * 
     * @param key Context key
     * @return Integer value
     */
    public Integer getContextAsInteger(String key) {
        Object value = scenarioContext.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }

    /**
     * Get context as Boolean
     * 
     * @param key Context key
     * @return Boolean value
     */
    public Boolean getContextAsBoolean(String key) {
        Object value = scenarioContext.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return null;
    }

    /**
     * Set data (alias for setContext for convenience)
     * 
     * @param key   Data key
     * @param value Data value
     */
    public void setData(String key, String value) {
        setContext(key, value);
    }

    /**
     * Get data (alias for getContext for convenience)
     * 
     * @param key Data key
     * @return Data value
     */
    public Object getData(String key) {
        return getContext(key);
    }
}
