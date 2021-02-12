package com.identity.test.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.logging.Level;


public class BasePage {

    public static WebDriver driver;

    TestContext testContext = TestContext.getInstance();

    public void initialiseDriver() {
        if(driver!=null){
            return;
        }
        getLocalDriver();
    }

    private void getLocalDriver() {
        String browserType = testContext.getProperty("browser");
        switch (browserType.toUpperCase()) {
            case "CHROME":
                startChromeDriver();
                break;
            case "FIREFOX":
                startFireFoxDriver();
                break;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private static void startFireFoxDriver() {
        FirefoxOptions options = getFireFoxOptions();
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
    }

    private static void startChromeDriver() {
        ChromeOptions options = getChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    private static ChromeOptions getChromeOptions() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.DRIVER, Level.OFF);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--test-type");
        chromeOptions.addArguments("allow-running-insecure-content");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.setCapability("acceptSslCerts", true);
        return chromeOptions;
    }

    private static FirefoxOptions getFireFoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setPreference("network.cookie.cookieBehavior", 1);
        profile.setPreference("startup.homepage_welcome_url.additional", "");
        profile.setPreference("network.proxy.type", 0);
        options.setCapability(FirefoxDriver.PROFILE, profile);
        options.setCapability("marionette", true);
        options.setCapability("platform", "ANY");
        options.setCapability("disable-restore-session-state", true);
        options.setCapability("acceptInsecureCerts", true);
        return options;
    }

    public boolean isAlertPresent()
    {
        try
        {
            driver.switchTo().alert();
            return true;
        }
        catch (NoAlertPresentException Ex)
        {
            return false;
        }
    }
}
