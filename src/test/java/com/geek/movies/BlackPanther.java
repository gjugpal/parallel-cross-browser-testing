package com.geek.movies;

import com.geek.movies.sut.IMDB;
import org.testng.annotations.Test;

public class BlackPanther extends BaseTest {

    @Test
    public void getMovieInformation() {
        new IMDB().navigateToAndScrapeData("https://www.imdb.com/title/tt1825683");
    }
}
