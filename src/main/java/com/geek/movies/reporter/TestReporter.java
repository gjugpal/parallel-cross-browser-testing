package com.geek.movies.reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


public class TestReporter extends TestListenerAdapter {

    @Override
    public void onConfigurationFailure(ITestResult testResult) {
        final ExtentTest test = TestReportManager.get();

        if(test != null) {
            test.log(Status.ERROR, testResult.getThrowable());
        }

        super.onConfigurationFailure(testResult);
    }

    @Override
    public void onFinish(final ITestContext testContext) {
        TestReportManager.close();
        super.onFinish(testContext);
    }

    @Override
    public void onTestStart(final ITestResult testResult) {
        if (testResult.getMethod().getDescription() != null) {
            TestReportManager.get().getModel().setDescription(testResult.getMethod().getDescription());
        }

        super.onTestStart(testResult);
    }

    @Override
    public synchronized void onTestSuccess(final ITestResult testResult) {
        TestReportManager.endTest();
        super.onTestSuccess(testResult);
    }

    @Override
    public synchronized void onTestFailure(final ITestResult testResult) {
        final ExtentTest test = TestReportManager.get();
        test.log(Status.FAIL, testResult.getThrowable());
        Screenshot.capture();
        TestReportManager.endTest();
        super.onTestFailure(testResult);
    }

}
