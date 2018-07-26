package org.interview.oauth.twitter.mapper;

import lombok.NonNull;
import lombok.val;
import org.interview.oauth.twitter.dto.UserDto;
import org.interview.oauth.twitter.util.ZonedDateTimes;
import org.interview.oauth.twitter.vo.User;

import java.time.ZonedDateTime;
import java.util.function.Function;

public class FromUserDtoMapper implements Function<UserDto, User> {

  @Override
  public User apply(@NonNull final UserDto userDto) {
    val creationDate = ZonedDateTimes.parse(userDto.creationDate());

    val userState =
      User.State
        .builder()
        .creationDate(creationDate)
        .screenName(userDto.screenName())
        .build();

    return User.builder()
             .id(userDto.id())
             .state(userState)
             .build();
  }
}
