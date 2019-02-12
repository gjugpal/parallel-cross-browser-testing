package com.geek.framework;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DefiningBrowser extends BaseTest {

    /**
     * This particular test should only run using Google Chrome browser, therefore the test is defining the browser
     * to spin up
     */
    @Override
    @BeforeTest
    public void setup() {
        setBrowser(SupportedBrowser.CHROME);
        super.setup();
    }

    @Test
    public void chromeBrowserTest() {
        WebDriver driver = getDriver();
        driver.get("http://www.google.com");
    }
}
