package org.interview.oauth.twitter.mapper;

import lombok.NonNull;
import lombok.val;
import org.interview.oauth.twitter.dto.TweetDto;
import org.interview.oauth.twitter.util.ZonedDateTimes;
import org.interview.oauth.twitter.vo.Tweet;

import java.util.function.Function;

public class FromTweetDtoMapper implements Function<TweetDto, Tweet> {

  @Override
  public Tweet apply(@NonNull final TweetDto tweetDto) {
    val author = new FromUserDtoMapper().apply(tweetDto.author());
    val creationDate = ZonedDateTimes.parse(tweetDto.creationDate());

    return Tweet
             .builder()
             .id(tweetDto.id())
             .creationDate(creationDate)
             .text(tweetDto.text())
             .author(author)
             .build();
  }
}
