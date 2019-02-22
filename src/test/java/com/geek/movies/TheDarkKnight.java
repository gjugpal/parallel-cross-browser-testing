package com.geek.movies;

import com.geek.movies.browser.SupportedBrowsers;
import com.geek.movies.sut.IMDB;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TheDarkKnight extends BaseTest {


    @Override
    @BeforeClass(alwaysRun = true)
    public void setup() {
        setBrowser(SupportedBrowsers.CHROME);
        super.setup();
    }


    @Test(priority = 2)
    public void getMovieInformation() throws InterruptedException {
        new IMDB().navigateToAndScrapeData("https://www.imdb.com/title/tt0468569");
    }
}
