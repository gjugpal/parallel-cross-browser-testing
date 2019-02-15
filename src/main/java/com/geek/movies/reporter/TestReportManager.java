package com.geek.movies.reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestReportManager {

    private static ExtentReports REPORT;
    private static final ThreadLocal<ExtentTest> REPORT_LIST = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> CHILD_TEST = new ThreadLocal<>(); // Data Driven tests run in pool threads therefore they need to be stored separately.
    private static final String EXTENT_CONFIG_XML = "extent-config.xml";

    static {
        ExtentHtmlReporter html = new ExtentHtmlReporter("testReport.html");
        REPORT = new ExtentReports();
        REPORT.attachReporter(html);
    }

    public static void addReportSystemInfo(final String name, final String value) {
        REPORT.setSystemInfo(name, value);
    }

    static void close(){
        endTest(); // make sure all open tests have been closed properly
        REPORT.close();
    }

    public static synchronized void startTest(final String name) {
        REPORT_LIST.set(REPORT.createTest(name));
    }

    public static synchronized ExtentTest get() {
        return CHILD_TEST.get() != null ? CHILD_TEST.get() : REPORT_LIST.get();
    }

    /**
     * This method ends the test reporter safely and then updates the execution report. Flushing
     * the report at this stage means that the report is generated and updated during execution
     * rather than having to wait until the full test suite finishes execution.
     */
    public static synchronized void endTest() {
        if (REPORT_LIST.get() != null) {
//            REPORT..endTest(REPORT_LIST.get());
            REPORT_LIST.remove();
        }

        REPORT.flush();
    }
}
