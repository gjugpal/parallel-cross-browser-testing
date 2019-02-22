package com.geek.movies.browser;

import com.google.common.base.Strings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class BrowserFactory {

    protected synchronized WebDriver buildBrowser(SupportedBrowsers browser) {
        return build(browser);
    }

    protected synchronized WebDriver buildBrowser() {
        String propertyVal = System.getProperty("BROWSER");
        final SupportedBrowsers browser =  Strings.isNullOrEmpty(propertyVal) ? SupportedBrowsers.FIREFOX : SupportedBrowsers.valueOf(propertyVal);
        return build(browser);
    }

    private synchronized WebDriver build(SupportedBrowsers browser) {
        WebDriver driver = null;

        switch (browser) {
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
                driver = new FirefoxDriver();
                break;
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
                driver = new ChromeDriver();
                break;
            case INTERNET_EXPLORER:
                driver = new InternetExplorerDriver();
                break;
        }

        driver.manage().window().fullscreen();
        return driver;
    }
}
