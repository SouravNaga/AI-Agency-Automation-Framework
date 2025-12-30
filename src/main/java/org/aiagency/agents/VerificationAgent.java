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
    private WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.MAX_WAIT_TIME));

    // args → [instruction, element/xpath, expected_value]
    @Override
    public void execute(String... args) {

        String instruction = args[0];   // heading_text / current_url / element
        String element = args[1];       // xpath OR N/A
        String expValue = args[2];      // text / url / visible

        switch (instruction) {

            case "heading_text":
                handleHeadingVerification(element, expValue);
                break;

            case "current_url":
                verifyUrl(expValue);
                break;

            case "element":
                handleElementVerification(element, expValue);
                break;

            default:
                throw new IllegalArgumentException("Unknown instruction: " + instruction);
        }
    }

    /* ---------------- HEADING TEXT ---------------- */

    private void handleHeadingVerification(String xpath, String expectedText) {
        WebElement element = driver.findElement(By.xpath(xpath));
        String actualText = element.getText().trim();

        if (!actualText.equals(expectedText.replace("\"", ""))) {
            throw new AssertionError(
                    "Expected text: " + expectedText + " but found: " + actualText);
        }
    }

    /* ---------------- URL ---------------- */

    private void verifyUrl(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        if (!actualUrl.equals(expectedUrl)) {
            throw new AssertionError(
                    "Expected URL: " + expectedUrl + " but found: " + actualUrl);
        }
    }

    /* ---------------- ELEMENT STATE ---------------- */

    private void handleElementVerification(String xpath, String condition) {

        WebElement element = driver.findElement(By.xpath(xpath));

        switch (condition.toLowerCase()) {

            case "visible":
                if (!element.isDisplayed()) {
                    throw new AssertionError("Element is not visible: " + xpath);
                }
                break;

            case "not visible":
                if (element.isDisplayed()) {
                    throw new AssertionError("Element is visible: " + xpath);
                }
                break;

            case "enable":
                if (!element.isEnabled()) {
                    throw new AssertionError("Element is not enabled: " + xpath);
                }
                break;

            case "disable":
                if (element.isEnabled()) {
                    throw new AssertionError("Element is enabled: " + xpath);
                }
                break;

            case "clickable":
                wait.until(ExpectedConditions.elementToBeClickable(element));
                break;

            case "not clickable":
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    throw new AssertionError("Element is clickable: " + xpath);
                } catch (Exception e) {
                    // expected
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown condition: " + condition);
        }
    }
}
