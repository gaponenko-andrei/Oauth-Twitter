package org.interview.oauth.twitter.util;

import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class ZonedDateTimes {

  private static final DateTimeFormatter FORMATTER =
    DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss xxxx yyyy", Locale.ENGLISH);


  public static ZonedDateTime parse(final String twitterDateTimeString) {
    return ZonedDateTime.parse(twitterDateTimeString, FORMATTER);
  }
}
