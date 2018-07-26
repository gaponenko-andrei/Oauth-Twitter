package org.interview.oauth.twitter.vo;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;

import java.time.ZonedDateTime;

@Value
@Wither
@Builder(builderClassName = "Builder")
public class Tweet {

  @NonNull
  private Long id;

  @NonNull
  private ZonedDateTime creationDate;

  @NonNull
  private String text;

  @NonNull
  private User author;
}
