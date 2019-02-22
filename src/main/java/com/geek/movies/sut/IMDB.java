package com.geek.movies.sut;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.geek.movies.browser.BrowserManager;
import com.geek.movies.reporter.Screenshot;
import com.geek.movies.reporter.TestReportManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IMDB {

    private WebDriver driver;
    private ExtentTest testReport;

    public IMDB() {
        driver = BrowserManager.getBrowser();
        testReport = TestReportManager.get();
    }

    public void navigateToAndScrapeData(String url) {
        driver.get(url);
        Screenshot.capture();
        testReport.log(Status.INFO, "Navigated to " + url);
        testReport.log(Status.INFO, "Rating: " + getRating());
        testReport.log(Status.INFO, "Duration: " + getMovieDuration());
        testReport.log(Status.INFO, "Summary: " + getSummary());
    }

    private String getRating() {
        return driver.findElement(By.cssSelector("div.ratingValue > strong > span")).getText();
    }

    private String getMovieDuration() {
        return driver.findElement(By.cssSelector("div > time")).getText();
    }

    private String getSummary() {
        return driver.findElement(By.cssSelector("div.summary_text")).getText();
    }

}
