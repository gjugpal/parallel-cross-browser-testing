package com.geek.framework;

import org.openqa.selenium.WebDriver;

import static org.junit.Assert.fail;

public abstract class DriverGenerator {

    private static final ThreadLocal<WebDriver> THREAD_DRIVER = new ThreadLocal<WebDriver>();
    private static final DriverFactory DRIVER_FACTORY = new DriverFactory();
    private SupportedBrowser browser = null;

    public WebDriver getDriver() {
        return THREAD_DRIVER.get();
    }

    public void setBrowser(SupportedBrowser browser){
        this.browser = browser;
    }

    protected void setup() {
        try {
            final WebDriver driver = browser == null ? DRIVER_FACTORY.buildDriver() : DRIVER_FACTORY.buildDriver(browser);
            THREAD_DRIVER.set(driver);

        } catch (final Exception e) {
            fail("driver generation problem encountered");
        }
    }

    protected void tearDown() {
        final WebDriver driver = getDriver();

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