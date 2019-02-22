package com.geek.movies.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestReportManager {

    private static ExtentReports REPORT;
    private static final ThreadLocal<ExtentTest> REPORT_LIST = new ThreadLocal<>();

    static {
        ExtentHtmlReporter html = new ExtentHtmlReporter("testReport.html");
        REPORT = new ExtentReports();
        REPORT.attachReporter(html);
    }

    static void close(){
        endTest(); // make sure all open tests have been closed properly
        REPORT.close();
    }

    public static synchronized void startTest(final String name) {
        REPORT_LIST.set(REPORT.createTest(name));
    }

    public static synchronized ExtentTest get() {
        return REPORT_LIST.get();
    }

    /**
     * This method ends the test reporter safely and then updates the execution report. Flushing
     * the report at this stage means that the report is generated and updated during execution
     * rather than having to wait until the full test suite finishes execution.
     */
    public static synchronized void endTest() {
        if (REPORT_LIST.get() != null) {
            REPORT_LIST.get().getModel().end();
            REPORT_LIST.remove();
        }

        REPORT.flush();
    }
}
