package org.interview.oauth.twitter.util;

import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.interview.oauth.twitter.dto.TweetDto;
import org.interview.oauth.twitter.mapper.FromTweetDtoMapper;
import org.interview.oauth.twitter.vo.Tweet;

import java.util.function.Function;

// Mostly for readability

@UtilityClass
public class Tweets {

  /**
   * @return a function to create {@link Tweet} from JSON {@link String}
   */
  public static Function<String, Tweet> fromJson() {
    val jsonParser = new GsonBuilder().create();
    val fromDtoMapper = new FromTweetDtoMapper();

    return (tweetJson) -> {
      val tweetDto = jsonParser.fromJson(tweetJson, TweetDto.class);
      return fromDtoMapper.apply(tweetDto);
    };
  }
}
