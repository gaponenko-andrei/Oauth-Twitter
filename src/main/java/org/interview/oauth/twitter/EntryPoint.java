package org.interview.oauth.twitter;

import com.google.api.client.http.HttpBackOffIOExceptionHandler;
import com.google.api.client.http.HttpResponse;
import lombok.Cleanup;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.interview.oauth.twitter.util.Tweets;
import org.interview.oauth.twitter.vo.Tweet;
import org.interview.oauth.twitter.vo.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.google.api.client.util.BackOff.ZERO_BACKOFF;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

public class EntryPoint {

  private static final Log LOGGER = LogFactory.getLog(EntryPoint.class);

  private static final String CONSUMER_KEY = "vp8qXAMoZzy6jowJdtouPLUUb";
  private static final String CONSUMER_SECRET = "IMx3eIRfXXbRimoIz7cNpZCl0dr9dYEdRuDVTr2C4LdResXjN7";


  public static void main(final String[] args)
    throws TwitterAuthenticationException, IOException, InterruptedException {

    // get authorized request factory
    val authenticator = new TwitterAuthenticator(System.out, CONSUMER_KEY, CONSUMER_SECRET);
    val requestFactory = authenticator.getAuthorizedHttpRequestFactory();

    // build post request
    val postRequest =
      requestFactory
        .buildPostRequest(new TweetsFilteredStreamUrl().setTrack("bieber"), null)
        .setIOExceptionHandler(new HttpBackOffIOExceptionHandler(ZERO_BACKOFF));

    // collect specified number of json strings with duration time limit
    val linesCollector = new BufferedReaderLinesCollector(100, Duration.ofSeconds(30));
    val tweetJsonStrings = collectLinesWithDisconnect(postRequest.execute(), linesCollector);

    print(tweetJsonStrings);
  }

  private static List<String> collectLinesWithDisconnect(
    final HttpResponse httpResponse,
    final BufferedReaderLinesCollector collector) throws IOException {

    // not sure if cleanup is needed here as disconnect is done
    @Cleanup val responseContent = httpResponse.getContent();

    try (val streamAdapter = new InputStreamReader(responseContent);
         val bufferedReader = new BufferedReader(streamAdapter)) {

      return collector.collectFrom(bufferedReader);

    } finally {
      httpResponse.disconnect();
    }
  }

  private static void print(final List<String> tweetJsonStrings) {
    val messageCounter = new AtomicInteger(0);

    val tweetsByAuthors =
      tweetJsonStrings
        .stream()
        .map(Tweets.fromJson())
        .collect(groupingBy(Tweet::author));

    tweetsByAuthors
      .keySet()
      .stream()
      .sorted(comparing(User::creationDate))
      .forEach(user -> print(user, tweetsByAuthors.get(user), messageCounter));
  }

  private static void print(final User user,
                            final List<Tweet> userTweets,
                            final AtomicInteger tweetsCounter) {

    val separator = Stream.iterate("-", c -> c + "-").limit(15).collect(joining());

    LOGGER.info(separator);
    LOGGER.info(user);
    LOGGER.info(separator);
    LOGGER.info("Messages:\n");

    userTweets
      .stream()
      .sorted(comparing(Tweet::creationDate))
      .forEach(tweet -> {
        LOGGER.info(tweetsCounter.incrementAndGet() + " - " + tweet + "\n");
      });
  }
}
