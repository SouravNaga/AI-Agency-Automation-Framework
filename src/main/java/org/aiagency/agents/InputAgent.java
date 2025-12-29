package org.aiagency.agents;

import org.aiagency.constants.FrameworkConstants;
import org.aiagency.core.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InputAgent implements Agent {

    @Override
    public void execute(String instruction) {
        // Example: Enter admin as //input[@id='username']
        String data = instruction.replace("Enter", "").trim();
        String value = data.split(" as ")[0].trim();
        String xpath = data.split(" as ")[1].trim();

        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(FrameworkConstants.MAX_WAIT_TIME)
        );
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
                .sendKeys(value);
    }
}
