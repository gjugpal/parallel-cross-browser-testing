package com.geek.movies;

import com.geek.movies.sut.IMDB;
import org.testng.annotations.Test;

public class BlackPanther extends BaseTest {

    @Test(description = "Get movie information for the Black Knight", priority = 4)
    public void getMovieInformation() throws InterruptedException {
        new IMDB().navigateToAndScrapeData("https://www.imdb.com/title/tt1825683");
    }
}
