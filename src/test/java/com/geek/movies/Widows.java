package com.geek.movies;

import com.geek.movies.browser.SupportedBrowsers;
import com.geek.movies.sut.IMDB;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Widows extends BaseTest {

    /**
     * This particular test should only run using Google Chrome browser, therefore the test is defining the browser
     * to spin up
     */
    @Override
    @BeforeClass(alwaysRun = true)
    public void setup() {
        setBrowser(SupportedBrowsers.CHROME);
        super.setup();
    }

    @Test(priority = 1)
    public void chromeBrowserTest() throws InterruptedException {
        new IMDB().navigateToAndScrapeData("https://www.imdb.com/title/tt4218572");
    }
}
