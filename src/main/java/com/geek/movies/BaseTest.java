package com.geek.movies;

import com.aventstack.extentreports.ExtentTest;
import com.geek.movies.browser.BrowserManager;
import com.geek.movies.browser.SupportedBrowsers;
import com.geek.movies.reporter.TestReportManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest extends BrowserManager {

    private ExtentTest test = null;

    public void setBrowser(SupportedBrowsers browser) {
        super.setBrowser(browser);
    }

    @BeforeClass(alwaysRun = true)
    protected void setup() {
        createTestReporter();
        super.setup();
    }

    @AfterClass(alwaysRun = true)
    protected void tearDown() {
        if(test != null){
            TestReportManager.endTest();
        }

        super.tearDown();
    }

    private void createTestReporter() {
        final String[] tmp = getClass().getName().split("\\.");
        final String testName = String.format("%s.%s", tmp[tmp.length - 2], tmp[tmp.length - 1]);
        TestReportManager.startTest(testName);
        test = TestReportManager.get();
    }
}
