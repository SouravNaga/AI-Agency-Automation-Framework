package org.aiagency.agents;

import org.aiagency.constants.FrameworkConstants;
import org.aiagency.core.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VerificationAgent implements Agent {

    private WebDriver driver = DriverManager.getDriver();
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.MAX_WAIT_TIME));

    @Override
    public void execute(String instruction) {
        instruction = instruction.trim();

        if (instruction.contains("heading")) {
            handleHeadingVerification(instruction.replace("Verify the heading is", "").trim());
        } else if (instruction.contains("current url")) {
            verifyUrl(instruction.replace("Verify the current url is", "").trim());
        } else if (instruction.contains("Verify the element")) {
            handleElementVerification(instruction.replace("Verify the element", "").trim());
        } else {
            throw new IllegalArgumentException("Unknown instruction: " + instruction);
        }
    }

    private void handleHeadingVerification(String instruction) {
        // instruction example: Verify the heading for //div[text()='Swag Labs'] is Swag Labs
        String[] parts = instruction.split(" is ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid heading verification instruction: " + instruction);
        }
        String[] parts2 = parts[0].split(" for ");
        String xpath = parts2[1].trim(); // "Verify the heading for //div[text()='Swag Labs']"
        String expectedText = parts[1].trim(); // "Swag Labs"

        WebElement element = driver.findElement(By.xpath(xpath));
        String actualText = element.getText();

        if (!actualText.equals(expectedText)) {
            throw new AssertionError("Expected text: " + expectedText + " but found: " + actualText);
        }
    }


    private void verifyUrl(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        if (!actualUrl.equals(expectedUrl)) {
            throw new AssertionError("Expected URL: " + expectedUrl + " but found: " + actualUrl);
        }
    }

    private void handleElementVerification(String instruction) {
        // instruction example: "//button[text()='Back Home'] is visible"
        String[] parts = instruction.split(" is ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid element verification instruction: " + instruction);
        }

        String xpath = parts[0].trim();
        String condition = parts[1].trim();

        WebElement element = driver.findElement(By.xpath(xpath));

        if (condition.contains("not visible")) {
            if (element.isDisplayed()) {
                throw new AssertionError("Element " + xpath + " is not visible");
            }
        } else if (condition.contains("visible")) {
            if (!element.isDisplayed()) {
                throw new AssertionError("Element " + xpath + " is visible but should not be");
            }
        } else if (condition.contains("enable")) {
            if (!element.isEnabled()) {
                throw new AssertionError("Element " + xpath + " is not enabled");
            }
        } else if (condition.contains("disable")) {
            if (element.isEnabled()) {
                throw new AssertionError("Element " + xpath + " is enabled but should be disabled");
            }
        }
        else if (condition.contains("not clickable")) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
            } catch (Exception e) {
                throw new AssertionError("Element " + xpath + " is not clickable");
            }
        } else if (condition.contains("clickable")) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                throw new AssertionError("Element " + xpath + " is clickable but should not be");
            } catch (Exception e) {
                // expected: element not clickable
            }
        }
        else {
            throw new IllegalArgumentException("Unknown verification condition: " + condition);
        }
    }
}
