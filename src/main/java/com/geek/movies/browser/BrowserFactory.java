package com.geek.movies.browser;

import com.google.common.base.Strings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class BrowserFactory {

//    private ClassLoader classLoader = getClass().getClassLoader();

    protected synchronized WebDriver buildDriver(SupportedBrowser browser) {
        return build(browser);
    }

    protected synchronized WebDriver buildDriver() {
        String propertyVal = System.getProperty("BROWSER");
        final SupportedBrowser browser =  Strings.isNullOrEmpty(propertyVal) ? SupportedBrowser.FIREFOX : SupportedBrowser.valueOf(propertyVal);
        return build(browser);
    }

    private synchronized WebDriver build(SupportedBrowser browser) {
        WebDriver driver = null;

        switch (browser) {
            case FIREFOX:
//                String geckoDriverPath  = classLoader.getResource("geckodriver").getPath();
                System.setProperty("webdriver.gecko.driver", "/Users/gju01/Documents/github/factory-pattern-cross-browser-testing/geckodriver");
                driver = new FirefoxDriver();
                break;
            case CHROME:
//                String chromeDriverPath  = classLoader.getResource("chromedriver").getPath();
                System.setProperty("webdriver.chrome.driver", "/Users/gju01/Documents/github/factory-pattern-cross-browser-testing/chromedriver");
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
