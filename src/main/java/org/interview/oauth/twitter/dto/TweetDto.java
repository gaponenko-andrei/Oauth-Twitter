package org.interview.oauth.twitter.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public final class TweetDto implements Serializable {

  @SerializedName("id")
  private Long id;

  @SerializedName("created_at")
  private String creationDate;

  @SerializedName("text")
  private String text;

  @SerializedName("user")
  private UserDto author;
}
