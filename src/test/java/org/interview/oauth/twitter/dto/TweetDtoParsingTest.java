package org.interview.oauth.twitter.dto;

import com.google.gson.GsonBuilder;
import lombok.val;
import org.interview.oauth.twitter.Resources;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.testng.Assert.assertEquals;

public class TweetDtoParsingTest {

  @Test
  public void parse() throws IOException, URISyntaxException {

    /* Arrange */
    val userJson = Files.readFile(Resources.getFile("tweet-dto-parsing-test.json"));

    val expectedAuthorDto =
      new UserDto()
        .id(2196647713L)
        .screenName("halleeroo57")
        .creationDate("Fri Nov 15 21:28:25 +0000 2013");

    val expectedTweetDto =
      new TweetDto()
        .author(expectedAuthorDto)
        .id(1022598635450585089L)
        .text("Some random text")
        .creationDate("Thu Jul 26 21:44:48 +0000 2018");

    /* Act */
    val actualTweetDto = new GsonBuilder().create().fromJson(userJson, TweetDto.class);

    /* Assert */
    assertEquals(actualTweetDto, expectedTweetDto);
  }
}
