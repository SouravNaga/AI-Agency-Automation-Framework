package org.aiagency.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    public static WebDriver driver;
    private DriverManager()
    {
        //prevent object creation
    }
    public static  WebDriver getDriver(){
        if(driver==null)
        {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            // Incognito mode
            options.addArguments("--incognito");

            // Optional: pop-ups and notifications disable
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
        return driver;
    }
    public static  void quitDriver()
    {
        if(driver!=null)
        {
            driver.quit();
            driver=null;
        }
    }
}
