package com.geek.framework;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class UsingDefaultBrowser extends BaseTest {

    @Test
    public void second() {
        WebDriver driver = getDriver();
        driver.get("http://www.google.com");
    }
}
