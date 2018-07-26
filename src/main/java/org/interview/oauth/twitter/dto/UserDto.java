package org.interview.oauth.twitter.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public final class UserDto implements Serializable {

  @SerializedName("id")
  private Long id;

  @SerializedName("screen_name")
  private String screenName;

  @SerializedName("created_at")
  private String creationDate;
}
