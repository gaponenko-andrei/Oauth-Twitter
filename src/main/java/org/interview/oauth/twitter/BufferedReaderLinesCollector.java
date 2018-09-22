package org.interview.oauth.twitter;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.google.api.client.util.Strings.isNullOrEmpty;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
public class BufferedReaderLinesCollector {

  private static final Log LOGGER = LogFactory.getLog(BufferedReaderLinesCollector.class);

  /**
   * Maximum number of lines to collect.
   */
  private final int requiredCount;

  /**
   * Maximum duration of one collection operation.
   */
  @NonNull
  private final Duration durationLimit;


  /**
   * Collects {@link #requiredCount} of string lines from specified {@link BufferedReader}
   * for maximum of {@link #durationLimit}. If reader does not have required number of lines
   * or time limit is exceeded, collection operation is finished and all lines collected up
   * to that point of time are returned.This method doesn't close provided buffered reader.
   */
  public List<String> collectFrom(@NonNull final BufferedReader bufferedReader)
    throws IOException {

    val collectedStrings = newObservableList();
    val dateTimeLimit = now().plus(durationLimit);

    while (this.needsMore(collectedStrings) && now().isBefore(dateTimeLimit)) {
      // TODO there's a potential bug here as 'readLine' 
      // is a blocking operation, so it has to be fixed
      val tweetJson = bufferedReader.readLine(); 

      if (isNullOrEmpty(tweetJson)) {
        break;
      } else {
        collectedStrings.add(tweetJson);
      }
    }

    return new ArrayList<>(collectedStrings);
  }

  private List<String> newObservableList() {
    val observableArrayList = FXCollections.<String>observableArrayList();
    observableArrayList.addListener(new LoggingListener(observableArrayList, Instant.now()));
    return observableArrayList;
  }

  private boolean needsMore(final List<String> collectedStrings) {
    return collectedStrings.size() < requiredCount;
  }

  /**
   * Event listener for logging purposes.
   */
  @RequiredArgsConstructor
  private static final class LoggingListener implements ListChangeListener<String> {

    /**
     * List to which elements are collected.
     */
    private final List<String> eventSource;

    /**
     * Instant when collection process was started.
     */
    private final Instant startInstant;


    @Override
    public void onChanged(final Change change) {
      while(change.next()) {
        if (change.wasAdded() && LOGGER.isDebugEnabled()) {
          val observedList = change.getList();
          logAdditionTo(observedList);
        }
      }
    }

    private void logAdditionTo(final List<?> observedList) {
      val linesCollected = observedList.size();
      val secondsPassed = Duration.between(startInstant, Instant.now()).getSeconds();
      LOGGER.debug(format("Lines collected: %s, seconds passed: %s", linesCollected, secondsPassed));
    }
  }
}
