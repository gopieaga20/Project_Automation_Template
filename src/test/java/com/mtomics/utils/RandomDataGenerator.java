package com.mtomics.utils;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.UUID;

/**
 * RandomDataGenerator class provides methods for generating random test data
 */
public class RandomDataGenerator {

    private static final Logger logger = LogManager.getLogger(RandomDataGenerator.class);
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    /**
     * Generate random first name
     * 
     * @return Random first name
     */
    public static String getRandomFirstName() {
        String firstName = faker.name().firstName();
        logger.debug("Generated first name: {}", firstName);
        return firstName;
    }

    /**
     * Generate random last name
     * 
     * @return Random last name
     */
    public static String getRandomLastName() {
        String lastName = faker.name().lastName();
        logger.debug("Generated last name: {}", lastName);
        return lastName;
    }

    /**
     * Generate random full name
     * 
     * @return Random full name
     */
    public static String getRandomFullName() {
        String fullName = faker.name().fullName();
        logger.debug("Generated full name: {}", fullName);
        return fullName;
    }

    /**
     * Generate random email
     * 
     * @return Random email
     */
    public static String getRandomEmail() {
        String email = faker.internet().emailAddress();
        logger.debug("Generated email: {}", email);
        return email;
    }

    /**
     * Generate random email with domain
     * 
     * @param domain Email domain
     * @return Random email with specified domain
     */
    public static String getRandomEmailWithDomain(String domain) {
        String username = faker.name().username();
        String email = username + "@" + domain;
        logger.debug("Generated email: {}", email);
        return email;
    }

    /**
     * Generate random email for testing (mailinator)
     * 
     * @return Random test email
     */
    public static String getRandomTestEmail() {
        String username = faker.name().username() + getRandomNumber(1000, 9999);
        String email = username + "@mailinator.com";
        logger.debug("Generated test email: {}", email);
        return email;
    }

    /**
     * Generate random password
     * 
     * @param length              Password length
     * @param includeSpecialChars Include special characters
     * @return Random password
     */
    public static String getRandomPassword(int length, boolean includeSpecialChars) {
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*";

        String allChars = upperCase + lowerCase + numbers;
        if (includeSpecialChars) {
            allChars += specialChars;
        }

        StringBuilder password = new StringBuilder();

        // Ensure at least one of each type
        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        if (includeSpecialChars) {
            password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        }

        // Fill remaining length
        for (int i = password.length(); i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle the password
        String shuffled = shuffleString(password.toString());
        logger.debug("Generated password of length: {}", length);
        return shuffled;
    }

    /**
     * Generate random phone number
     * 
     * @return Random phone number
     */
    public static String getRandomPhoneNumber() {
        String phoneNumber = faker.phoneNumber().phoneNumber();
        logger.debug("Generated phone number: {}", phoneNumber);
        return phoneNumber;
    }

    /**
     * Generate random US phone number
     * 
     * @return Random US phone number (format: XXX-XXX-XXXX)
     */
    public static String getRandomUSPhoneNumber() {
        String phoneNumber = String.format("%03d-%03d-%04d",
                random.nextInt(900) + 100,
                random.nextInt(900) + 100,
                random.nextInt(9000) + 1000);
        logger.debug("Generated US phone number: {}", phoneNumber);
        return phoneNumber;
    }

    /**
     * Generate random address
     * 
     * @return Random address
     */
    public static String getRandomAddress() {
        String address = faker.address().streetAddress();
        logger.debug("Generated address: {}", address);
        return address;
    }

    /**
     * Generate random city
     * 
     * @return Random city
     */
    public static String getRandomCity() {
        return faker.address().city();
    }

    /**
     * Generate random state
     * 
     * @return Random state
     */
    public static String getRandomState() {
        return faker.address().state();
    }

    /**
     * Generate random zip code
     * 
     * @return Random zip code
     */
    public static String getRandomZipCode() {
        return faker.address().zipCode();
    }

    /**
     * Generate random number within range
     * 
     * @param min Minimum value
     * @param max Maximum value
     * @return Random number
     */
    public static int getRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generate random alphanumeric string
     * 
     * @param length String length
     * @return Random alphanumeric string
     */
    public static String getRandomAlphanumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    /**
     * Generate UUID
     * 
     * @return UUID string
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate random company name
     * 
     * @return Random company name
     */
    public static String getRandomCompanyName() {
        return faker.company().name();
    }

    /**
     * Generate random job title
     * 
     * @return Random job title
     */
    public static String getRandomJobTitle() {
        return faker.job().title();
    }

    /**
     * Generate random text
     * 
     * @param wordCount Number of words
     * @return Random text
     */
    public static String getRandomText(int wordCount) {
        return faker.lorem().sentence(wordCount);
    }

    /**
     * Generate random paragraph
     * 
     * @return Random paragraph
     */
    public static String getRandomParagraph() {
        return faker.lorem().paragraph();
    }

    /**
     * Generate random boolean
     * 
     * @return Random boolean
     */
    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    /**
     * Generate random date of birth (18-80 years old)
     * 
     * @param format Date format
     * @return Random DOB string
     */
    public static String getRandomDateOfBirth(String format) {
        int yearsAgo = getRandomNumber(18, 80);
        return DateHelper.getPastDateByMonths(yearsAgo * 12, format);
    }

    /**
     * Shuffle string
     * 
     * @param input Input string
     * @return Shuffled string
     */
    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    /**
     * Select random item from array
     * 
     * @param items Array of items
     * @param <T>   Type
     * @return Random item
     */
    public static <T> T getRandomItemFromArray(T[] items) {
        return items[random.nextInt(items.length)];
    }

    /**
     * Generate random username
     * 
     * @return Random username
     */
    public static String getRandomUsername() {
        return faker.name().username() + getRandomNumber(100, 999);
    }
}
