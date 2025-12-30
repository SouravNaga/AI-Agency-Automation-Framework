package org.aiagency.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static String testFolderPath;
    private static int stepCounter = 0;   // AUTO STEP COUNTER

    /**
     * Call ONCE per test (from TestRunner)
     */
    public static void initTestFolder() {

        String timestamp = new SimpleDateFormat("MM-dd-yyyy_h-mm a")
                .format(new Date())
                .replace(" ", "");

        testFolderPath = "Screenshots/Test_Snaps_" + timestamp;

        File folder = new File(testFolderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        stepCounter = 0;

        System.out.println("Screenshot folder created: " + testFolderPath);
    }

    /**
     * Screenshot with element highlight
     */
    public static void takeScreenshotWithHighlight(
            WebDriver driver,
            WebElement element
    ) {

        try {
            stepCounter++;

            String fileName = "Step" + stepCounter + ".png";

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // 🔴 Highlight element
            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});" +
                            "arguments[0].style.border='3px solid blue';" +
                            "arguments[0].style.backgroundColor='rgba(255,0,0,0.15)';",
                    element
            );

            Thread.sleep(300);

            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            File dest = new File(
                    testFolderPath + File.separator + fileName
            );

            FileHandler.copy(src, dest);

            // 🧹 Remove highlight
            js.executeScript(
                    "arguments[0].style.border='';" +
                            "arguments[0].style.backgroundColor='';",
                    element
            );

            System.out.println("Saved: " + fileName);

        } catch (Exception e) {
            System.err.println("Screenshot failed");
            e.printStackTrace();
        }
    }

    /**
     * Full page screenshot (for navigation steps)
     */
    public static void takeFullPageScreenshot(WebDriver driver) {

        try {
            stepCounter++;

            String fileName = "Step" + stepCounter + ".png";

            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            File dest = new File(
                    testFolderPath + File.separator + fileName
            );

            FileHandler.copy(src, dest);

            System.out.println("Saved: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
