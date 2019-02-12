package com.geek.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class DriverFactory {

    protected synchronized WebDriver buildDriver(SupportedBrowser browser) {
        return build(browser);
    }

    protected synchronized WebDriver buildDriver() {
        final SupportedBrowser browser = SupportedBrowser.valueOf(System.getProperty("BROWSER"));
        return build(browser);
    }

    private synchronized WebDriver build(SupportedBrowser browser) {
        WebDriver driver = null;

        switch (browser) {
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case CHROME:
                driver = new ChromeDriver();
                break;
            case INTERNET_EXPLORER:
                driver = new InternetExplorerDriver();
                break;
        }

        return driver;
    }
}
