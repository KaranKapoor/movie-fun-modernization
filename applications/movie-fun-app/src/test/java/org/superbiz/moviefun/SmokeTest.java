package org.superbiz.moviefun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void smokeTest() {
//        RestTemplate restTemplate = new RestTemplate();

        String homePage = restTemplate.getForObject(url("/"), String.class);

        assertThat(homePage, containsString("Please select one of the following links:"));

        String setupPage = restTemplate.getForObject(url("/setup"), String.class);

        assertThat(setupPage, containsString("Wedding Crashers"));
        assertThat(setupPage, containsString("Starsky & Hutch"));
        assertThat(setupPage, containsString("Shanghai Knights"));
        assertThat(setupPage, containsString("I-Spy"));
        assertThat(setupPage, containsString("The Royal Tenenbaums"));

        String movieFunPage = restTemplate.getForObject(url("/moviefun"), String.class);

        assertThat(movieFunPage, containsString("Wedding Crashers"));
        assertThat(movieFunPage, containsString("David Dobkin"));
    }

    private String url(String path) {
        return path;
    }
}
