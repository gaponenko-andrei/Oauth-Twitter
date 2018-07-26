package org.interview.oauth.twitter.dto;

import com.google.gson.GsonBuilder;
import lombok.val;
import org.interview.oauth.twitter.Resources;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.testng.Assert.assertEquals;

public class UserDtoParsingTest {

  @Test
  public void parse() throws IOException, URISyntaxException {

    /* Arrange */
    val userJson = Files.readFile(Resources.getFile("user-dto-parsing-test.json"));

    val expectedUserDto =
      new UserDto()
        .id(2196647713L)
        .screenName("halleeroo57")
        .creationDate("Fri Nov 15 21:28:25 +0000 2013");

    /* Act */
    val actualUserDto = new GsonBuilder().create().fromJson(userJson, UserDto.class);

    /* Assert */
    assertEquals(actualUserDto, expectedUserDto);
  }
}
