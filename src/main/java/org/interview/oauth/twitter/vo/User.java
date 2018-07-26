package org.interview.oauth.twitter.vo;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Delegate;
import lombok.experimental.Wither;

import java.time.ZonedDateTime;

@Value
@Wither
@Builder(builderClassName = "Builder")
public class User {

  @NonNull
  private Long id;

  @NonNull
  @Delegate
  private State state;

  /**
   * This is what allows us to make distinction between user and it's current state.
   * It's generally very confusing when two users considered different by 'equals'
   * method only because of the fact some field - e.g. screen name - is changed.
   * This can lead to some unexpected side-effects when working with collections.
   */
  @Value
  @lombok.Builder(builderClassName = "Builder")
  public static final class State {

    @NonNull
    private String screenName;

    @NonNull
    private ZonedDateTime creationDate;
  }

  // It's important to notice only 'id' is used in 'equals' and 'hashCode' methods.
  // Which means that state of a 'User' is not considered, which is kind of makes sense.
  // Even if state of a user changed - e.g. screen name - the user itself is the same.

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
