package org.aiagency.agents;

import org.aiagency.constants.FrameworkConstants;
import org.aiagency.core.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ClickAgent implements Agent{
    @Override
    public void execute(String instruction) {

        String xpath = instruction.replace(instruction.split(" ")[0],"").trim();
        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(FrameworkConstants.MAX_WAIT_TIME)
        );
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))
                .click();

    }
}
