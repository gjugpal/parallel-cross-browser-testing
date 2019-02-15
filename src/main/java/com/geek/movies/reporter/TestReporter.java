package com.geek.movies.reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestReporter extends TestListenerAdapter {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_TICK = "\u2713";
    private static final String ANSI_CROSS = "\u0058";
    private static final String ANSI_CYAN = "\u001B[36m";
    private final String testReportLocation = "Reports.html";

    @Override
    public void onConfigurationFailure(ITestResult testResult) {

        final ExtentTest test = TestReportManager.get();

        if(test != null) {
            test.log(Status.ERROR, testResult.getThrowable());
        }

        super.onConfigurationFailure(testResult);
    }

    @Override
    public void onStart(final ITestContext testContext){

//        propertyProvider = PropertyProviderManager.get();
//        if(propertyProvider != null) {
            testContext.getCurrentXmlTest().setThreadCount(Integer.parseInt("4"));
//        }
        super.onStart(testContext);
    }

    @Override
    public void onFinish(final ITestContext testContext) {
//        ReportManager.addReportSystemInfo("Test Environment", GlobalProperty.TEST_ENV.getValue().toUpperCase());

            if (testContext.getAllTestMethods().length > 0) {
                TestReportManager.close();
            }

        super.onFinish(testContext);
    }


    @Override
    public void onTestStart(final ITestResult testResult) {

//        if(testResult.getParameters().length == 0) {
//            final ExtentTest test = ReportManager.get();
//            test.assignCategory(testResult.getMethod().getGroups());

//            if (testResult.getMethod().getDescription() != null) {
//                test.setDescription(testResult.getMethod().getDescription());
//            } else {
//                test.setDescription("[ERR_TEST_DESCRIPTION_NOT_FOUND]");
//            }
//        }

        boolean testFailedConfig = getConfigurationFailures()
                .stream()
                .anyMatch(x -> x.getInstanceName() == testResult.getInstanceName());

        if(testFailedConfig){
            System.out.println(String.format("%s %s %s %s %s", ANSI_CYAN, "Fail @Before ->", testResult.getInstanceName(), ANSI_CROSS, ANSI_RESET));
            TestReportManager.endTest();
        }
        super.onTestStart(testResult);
    }

    @Override
    public synchronized void onTestSuccess(final ITestResult testResult) {

        if(testResult.getParameters().length ==0) {
            System.out.println(String.format("%s %s %s %s %s",
                    ANSI_GREEN,
                    "Pass ->",
                    testResult.getInstanceName(),
                    ANSI_TICK,
                    ANSI_RESET));
        }else{
            StringUtils.join(testResult.getParameters(),"_");
            System.out.println(String.format("%s %s %s_%s %s %s", ANSI_GREEN, "Pass ->", testResult.getInstanceName(), StringUtils.join(testResult.getParameters(),"_"), ANSI_TICK, ANSI_RESET));
        }

        TestReportManager.endTest();
        super.onTestSuccess(testResult);
    }

    @Override
    public synchronized void onTestFailure(final ITestResult testResult) {

        System.out.println(String.format("%s %s %s %s %s", ANSI_RED, "Fail ->", testResult.getInstanceName(), ANSI_CROSS, ANSI_RESET));
        final ExtentTest test = TestReportManager.get();
        test.log(Status.FAIL, testResult.getThrowable());

        Screenshot.capture();

        TestReportManager.endTest();
        super.onTestFailure(testResult);
    }

}
