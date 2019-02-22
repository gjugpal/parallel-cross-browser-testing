package com.geek.movies;

import com.geek.movies.sut.IMDB;
import org.testng.annotations.Test;

public class QuietPlace extends BaseTest {

    @Test(priority = 3)
    public void getMovieInformation() throws InterruptedException {
        new IMDB().navigateToAndScrapeData("https://www.imdb.com/title/tt6644200");
    }
}
