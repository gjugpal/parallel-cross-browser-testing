package com.geek.movies.browser;

import org.openqa.selenium.WebDriver;

import static org.junit.Assert.fail;

public abstract class BrowserManager {

    private static final ThreadLocal<WebDriver> THREAD_DRIVER = new ThreadLocal<WebDriver>();
    private static final BrowserFactory BROWSER_FACTORY = new BrowserFactory();
    private SupportedBrowsers browser = null;

    public static WebDriver getBrowser() {
        return THREAD_DRIVER.get();
    }

    public void setBrowser(SupportedBrowsers browser){
        this.browser = browser;
    }

    protected void setup() {
        try {
            final WebDriver driver = browser == null ? BROWSER_FACTORY.buildBrowser() : BROWSER_FACTORY.buildBrowser(browser);
            THREAD_DRIVER.set(driver);

        } catch (final Exception e) {
            fail("driver generation problem encountered " + e);
        }
    }

    protected void tearDown() {
        final WebDriver driver = getBrowser();

        if (driver != null) {
            driver.quit();
        }

        try {
            THREAD_DRIVER.remove();

        } catch (final Exception e) {
            fail("error: unable to remove driver object from thread local");
        }
    }

}