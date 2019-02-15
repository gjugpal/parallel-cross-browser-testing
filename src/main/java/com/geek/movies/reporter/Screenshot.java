package com.geek.movies.reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.geek.movies.browser.BrowserManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.fail;

public class Screenshot {

    private static final String TEST_REPORT_LOCATION = "Reports.html";
    private static final String SCREENSHOT_FOLDER_NAME = "Screenshots/";
    private static final String SCREENSHOT_FILE_TYPE = ".png";
    private static final String PAGE_SOURCE_FILE_TYPE = ".html";
    private static final AtomicBoolean CREATED_DIRECTORY = new AtomicBoolean(false);

    private Screenshot() {

    }

    private static void createDirectory() {

        if (!CREATED_DIRECTORY.get()) {

//            final String folderPath = String.format("%s/%s", TEST_REPORT_LOCATION, SCREENSHOT_FOLDER_NAME);
            String folderPath = "Screenshots/";
            final File file = new File(folderPath);

            if (!file.exists()) {
                Assert.assertTrue(file.mkdirs(), "Failed to create the directory ".concat(folderPath));
                CREATED_DIRECTORY.getAndSet(true);
            }
        }
    }

    private static String takeScreenshot(final WebDriver driver) {

        FileOutputStream outputStream = null;
        final String filename = generateScreenshotFilename();

        try {

            final File file = new File(String.format("%s%s", SCREENSHOT_FOLDER_NAME, filename));
            outputStream = new FileOutputStream(file);

            final byte[] screenShotData;
            screenShotData = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            outputStream.write(screenShotData);
        } catch (final Exception e) {

            TestReportManager.get().log(Status.WARNING, String.format("ERR_FAILED_TO_CAPTURE_SCREENSHOT<pre>%s</pre>", e.toString()));
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (final IOException e) {
                }
            }
        }

        return filename;
    }

    public static synchronized void capture() {

        createDirectory();

        final WebDriver driver = BrowserManager.getDriver();
        final ExtentTest reporter = TestReportManager.get();

        if (driver != null) {

            final String screenshotFilename = takeScreenshot(driver);

            try {


            if (TestReportManager.get().getStatus().equals(Status.FAIL)) {
                final String pageSourceFilename = capturePageSource(driver, screenshotFilename);
//                reporter.logStatus.INFO, "<span class='label'><a href=\"" + SCREENSHOT_FOLDER_NAME.concat(pageSourceFilename) + "\">Click here to view the HTML Page Source</a></span>" + reporter);
                reporter.addScreenCaptureFromPath(SCREENSHOT_FOLDER_NAME.concat(screenshotFilename));

            } else {
                reporter.addScreenCaptureFromPath(SCREENSHOT_FOLDER_NAME.concat(screenshotFilename));
            }
            } catch (Exception e) {
                fail(e.getMessage());
            }

        } else {
            fail("Failed to retrieve the web browser in order to take a screenshot");
        }
    }

    /**
     * Captures the Page source at the point of failure and stores as a .html file in
     * the test report folder.
     *
     * @param driver browser to take the page source
     * @param filename of corresponding screenshot
     * @return filename of the of the .html file
     */
    private static synchronized String capturePageSource(final WebDriver driver, final String filename){

        String pageSource = driver.getPageSource();
        String tmpFilename = filename.replace(SCREENSHOT_FILE_TYPE, PAGE_SOURCE_FILE_TYPE);

        try {
            Files.write(Paths.get(String.format("%s/%s%s", TEST_REPORT_LOCATION, SCREENSHOT_FOLDER_NAME, tmpFilename)), pageSource.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tmpFilename;
    }

    private static String generateScreenshotFilename(){
        final ExtentTest reporter = TestReportManager.get();
        return reporter.getModel().getName().concat(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssS"))).concat(
                SCREENSHOT_FILE_TYPE);
    }
}
