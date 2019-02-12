package com.geek.framework;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest extends DriverGenerator {

    public void setBrowser(SupportedBrowser browser) {
        super.setBrowser(browser);
    }

    @Override
    @BeforeTest()
    protected void setup() {
        super.setup();
    }

    @Override
    @AfterTest()
    protected void tearDown() {
        super.tearDown();
    }
}
