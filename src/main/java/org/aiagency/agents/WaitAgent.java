package org.aiagency.agents;

import org.aiagency.core.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitAgent implements Agent {

    @Override
    public void execute(String... args) {
        // Example: Wait for //button[@id='login']
        String xpath = args[0];

        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(10)
        );

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
}
