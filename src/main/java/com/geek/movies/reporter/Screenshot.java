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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.fail;

public class Screenshot {

    private static final String SCREENSHOT_FOLDER_NAME = "Screenshots/";
    private static final String SCREENSHOT_FILE_TYPE = ".png";
    private static final AtomicBoolean CREATED_DIRECTORY = new AtomicBoolean(false);

    private Screenshot() {

    }

    private static void createDirectory() {
        if (!CREATED_DIRECTORY.get()) {
            final File file = new File(SCREENSHOT_FOLDER_NAME);

            if (!file.exists()) {
                Assert.assertTrue(file.mkdirs(), "Failed to create the directory ".concat(SCREENSHOT_FOLDER_NAME));
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
        final WebDriver driver = BrowserManager.getBrowser();
        final ExtentTest reporter = TestReportManager.get();

        if (driver != null) {
            final String screenshotFilename = takeScreenshot(driver);

            try {
                reporter.addScreenCaptureFromPath(SCREENSHOT_FOLDER_NAME.concat(screenshotFilename));
            } catch (Exception e) {
                fail(e.getMessage());
            }

        } else {
            fail("Failed to take screenshot, browser not found");
        }
    }

    private static String generateScreenshotFilename(){
        final ExtentTest reporter = TestReportManager.get();
        return reporter.getModel().getName().concat(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssS"))).concat(
                SCREENSHOT_FILE_TYPE);
    }
}
