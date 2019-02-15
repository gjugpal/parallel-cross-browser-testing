package com.geek.movies;

import com.geek.movies.browser.SupportedBrowser;
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
        setBrowser(SupportedBrowser.CHROME);
        super.setup();
    }

    @Test
    public void chromeBrowserTest() {
        new IMDB().navigateToAndScrapeData("https://www.imdb.com/title/tt4218572");
    }
}
