package com.geek.movies;

import com.geek.movies.sut.IMDB;
import org.testng.annotations.Test;

public class TheDarkKnight extends BaseTest {

    @Test
    public void getMovieInformation() {
        new IMDB().navigateToAndScrapeData("https://www.imdb.com/title/tt0468569");
    }
}
