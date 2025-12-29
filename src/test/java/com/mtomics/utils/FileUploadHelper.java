package com.mtomics.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

/**
 * FileUploadHelper class provides methods for file upload operations
 */
public class FileUploadHelper {

    private static final Logger logger = LogManager.getLogger(FileUploadHelper.class);
    private WebDriver driver;
    private WaitHelper waitHelper;
    private ConfigReader configReader;

    /**
     * Constructor
     * 
     * @param driver WebDriver instance
     */
    public FileUploadHelper(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        this.configReader = new ConfigReader();
    }

    /**
     * Upload file using sendKeys
     * 
     * @param locator  File input locator
     * @param filePath Absolute file path
     */
    public void uploadFile(WebElement locator, String filePath) {
        try {
            logger.info("Uploading file: {}", filePath);

            // Verify file exists
            File file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("File not found: " + filePath);
            }

            WebElement fileInput = waitHelper.waitForElementPresent(locator);
            fileInput.sendKeys(file.getAbsolutePath());
            logger.info("File uploaded successfully: {}", file.getName());
        } catch (Exception e) {
            logger.error("Failed to upload file: {}", filePath);
            throw e;
        }
    }

    /**
     * Upload document from test files directory
     * 
     * @param locator  File input locator
     * @param fileName File name (without path)
     */
    public void uploadDocument(WebElement locator, String fileName) {
        try {
            String documentPath = configReader.getProperty("test.document.path") + fileName;
            logger.info("Uploading document from test files: {}", fileName);
            uploadFile(locator, documentPath);
        } catch (Exception e) {
            logger.error("Failed to upload document: {}", fileName);
            throw e;
        }
    }

    /**
     * Upload lab file from test files directory
     * 
     * @param locator  File input locator
     * @param fileName File name (without path)
     */
    public void uploadLabFile(WebElement locator, String fileName) {
        try {
            String labFilePath = configReader.getProperty("test.lab.file.path") + fileName;
            logger.info("Uploading lab file from test files: {}", fileName);
            uploadFile(locator, labFilePath);
        } catch (Exception e) {
            logger.error("Failed to upload lab file: {}", fileName);
            throw e;
        }
    }

    /**
     * Upload multiple files
     * 
     * @param locator   File input locator
     * @param filePaths Array of file paths
     */
    public void uploadMultipleFiles(WebElement locator, String[] filePaths) {
        try {
            logger.info("Uploading multiple files: {} files", filePaths.length);

            StringBuilder allPaths = new StringBuilder();
            for (int i = 0; i < filePaths.length; i++) {
                File file = new File(filePaths[i]);
                if (!file.exists()) {
                    throw new RuntimeException("File not found: " + filePaths[i]);
                }
                allPaths.append(file.getAbsolutePath());
                if (i < filePaths.length - 1) {
                    allPaths.append("\n");
                }
            }

            WebElement fileInput = waitHelper.waitForElementPresent(locator);
            fileInput.sendKeys(allPaths.toString());
            logger.info("Multiple files uploaded successfully");
        } catch (Exception e) {
            logger.error("Failed to upload multiple files");
            throw e;
        }
    }

    /**
     * Verify file is uploaded (check if file name appears)
     * 
     * @param fileNameLocator  Locator for uploaded file name display
     * @param expectedFileName Expected file name
     * @return boolean
     */
    public boolean isFileUploaded(WebElement fileNameLocator, String expectedFileName) {
        try {
            String displayedFileName = fileNameLocator.getText();
            boolean uploaded = displayedFileName.contains(expectedFileName);
            logger.info("File upload verification: {}", uploaded ? "SUCCESS" : "FAILED");
            return uploaded;
        } catch (Exception e) {
            logger.error("Failed to verify file upload");
            return false;   
        }
    }

    /**
     * Get file extension
     * 
     * @param fileName File name
     * @return File extension
     */
    public String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf + 1);
    }

    /**
     * Validate file type
     * 
     * @param fileName          File name
     * @param allowedExtensions Array of allowed extensions
     * @return boolean
     */
    public boolean isValidFileType(String fileName, String[] allowedExtensions) {
        String extension = getFileExtension(fileName).toLowerCase();
        for (String allowed : allowedExtensions) {
            if (extension.equals(allowed.toLowerCase())) {
                return true;
            }
        }
        logger.warn("Invalid file type: {}. Allowed: {}", extension, String.join(", ", allowedExtensions));
        return false;
    }

    /**
     * Get file size in MB
     * 
     * @param filePath File path
     * @return File size in MB
     */
    public double getFileSizeInMB(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            double sizeInMB = (double) file.length() / (1024 * 1024);
            logger.debug("File size: {} MB", String.format("%.2f", sizeInMB));
            return sizeInMB;
        }
        return 0;
    }

    /**
     * Validate file size
     * 
     * @param filePath    File path
     * @param maxSizeInMB Maximum allowed size in MB
     * @return boolean
     */
    public boolean isValidFileSize(String filePath, double maxSizeInMB) {
        double fileSize = getFileSizeInMB(filePath);
        boolean valid = fileSize <= maxSizeInMB;
        if (!valid) {
            logger.warn("File size {} MB exceeds maximum {} MB",
                    String.format("%.2f", fileSize), maxSizeInMB);
        }
        return valid;
    }
}
