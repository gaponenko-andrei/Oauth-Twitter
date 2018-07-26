package org.interview.oauth.twitter.mapper;

import lombok.val;
import org.interview.oauth.twitter.dto.TweetDto;
import org.interview.oauth.twitter.test.dto.TestTweetDto;
import org.interview.oauth.twitter.util.ZonedDateTimes;
import org.interview.oauth.twitter.vo.Tweet;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FromTweetDtoMapperTest {

  private FromTweetDtoMapper mapper;
  private TweetDto input;
  private Tweet output;


  @BeforeTest
  public void setUp() {
    this.mapper = new FromTweetDtoMapper();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void throwsOnNullArgument() {

    /* Arrange */
    givenInputIs(null);

    /* Act */
    applyMapper();
  }

  @Test(dataProvider = "testCasesProvider")
  public void appliesCorrectly(final TweetDto tweetDto,
                               final Tweet expectedTweet) {
    /* Arrange */
    givenInputIs(tweetDto);

    /* Act */
    applyMapper();

    /* Assert */
    assertOutputIs(expectedTweet);
  }

  private void givenInputIs(final TweetDto tweetDto) {
    this.input = tweetDto;
  }

  private void applyMapper() {
    this.output = this.mapper.apply(input);
  }

  private void assertOutputIs(final Tweet expectedTweet) {
    assertEquals(output, expectedTweet);
  }

  @DataProvider(name = "testCasesProvider")
  private static Object[][] getTestCases() {
    val input = new TestTweetDto().delegate();

    val expectedAuthor = new FromUserDtoMapper().apply(input.author());
    val expectedCreationDate = ZonedDateTimes.parse(input.creationDate());

    val expectedTweetMessage =
      Tweet.builder()
        .id(input.id())
        .creationDate(expectedCreationDate)
        .text(input.text())
        .author(expectedAuthor)
        .build();

    return new Object[][]{{input, expectedTweetMessage}};
  }
}
