package org.aiagency.agents;

import org.aiagency.constants.FrameworkConstants;
import org.aiagency.core.DriverManager;
import org.aiagency.utils.ScreenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.aiagency.core.DriverManager.driver;

public class ClickAgent implements Agent{
    @Override
    public void execute(String... args) {
        String xpath = args[0];
        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(FrameworkConstants.MAX_WAIT_TIME)
        );
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(xpath))
        );

        // 📸 Screenshot with highlight BEFORE click
        ScreenshotUtil.takeScreenshotWithHighlight(
                driver,
                element
        );

        element.click();

    }
}
