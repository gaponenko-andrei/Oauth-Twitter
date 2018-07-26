package org.interview.oauth.twitter;

import com.google.api.client.http.GenericUrl;

public class TweetsFilteredStreamUrl extends GenericUrl {

  public TweetsFilteredStreamUrl() {
    super("https://stream.twitter.com/1.1/statuses/filter.json");
  }

  public TweetsFilteredStreamUrl setTrack(final String trackValue) {
    super.set("track", trackValue);
    return this;
  }
}
