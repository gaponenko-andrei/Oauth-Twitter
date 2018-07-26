package org.interview.oauth.twitter.test.dto;

import lombok.Getter;
import lombok.experimental.Delegate;
import org.interview.oauth.twitter.dto.UserDto;

public final class TestUserDto {

  @Getter
  @Delegate
  private final UserDto delegate;

  public TestUserDto() {
    this.delegate =
      new UserDto()
        .id(12345L)
        .screenName("screenName")
        .creationDate("Fri Nov 15 21:28:25 +0000 2013");
  }
}
