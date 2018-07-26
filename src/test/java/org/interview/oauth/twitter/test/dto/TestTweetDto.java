package org.interview.oauth.twitter.test.dto;

import lombok.Getter;
import lombok.experimental.Delegate;
import org.interview.oauth.twitter.dto.TweetDto;

public final class TestTweetDto {

  @Getter
  @Delegate
  private final TweetDto delegate;

  public TestTweetDto() {
    this.delegate =
      new TweetDto()
        .id(12345L)
        .text("^ _ ^")
        .author(new TestUserDto().delegate())
        .creationDate("Thu Jul 26 21:44:48 +0000 2018");
  }
}
