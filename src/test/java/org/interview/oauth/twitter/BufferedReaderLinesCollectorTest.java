package org.interview.oauth.twitter;

import lombok.val;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class BufferedReaderLinesCollectorTest {

  private BufferedReaderLinesCollector collector;
  private List<String> collectedLines;


  @Test(dataProvider = "testCasesProvider")
  public void collectsExpectedNumberOfLines(
    final BufferedReader reader,
    final Duration durationLimit,
    final int requiredNumberOfLines,
    final int expectedNumberOfLines) throws IOException {

    /* Arrange */
    givenCollectorWith(requiredNumberOfLines, durationLimit);

    /* Act */
    collectLinesFrom(reader);

    /* Assert */
    assertResultSizeIs(expectedNumberOfLines);
  }

  private void givenCollectorWith(final int requiredNumberOfLines, final Duration durationLimit) {
    this.collector = new BufferedReaderLinesCollector(requiredNumberOfLines, durationLimit);
  }

  private void collectLinesFrom(final BufferedReader reader) throws IOException {
    this.collectedLines = collector.collectFrom(reader);
  }

  private void assertResultSizeIs(final int expectedNumberOfLines) {
    assertEquals(collectedLines.size(), expectedNumberOfLines);
  }

  @DataProvider(name = "testCasesProvider")
  private static Iterator<Object[]> getTestCases() throws IOException, URISyntaxException {
    val testCases = new ArrayList<Object[]>();

    val availableNumberOfLines = 10;
    BufferedReader reader;
    Duration duration;
    int requiredNumberOfLines;
    int expectedNumberOfLines;

    // test case of zero duration limit
    reader = mockReader(availableNumberOfLines);
    duration = Duration.ZERO;
    requiredNumberOfLines = 10;
    expectedNumberOfLines = 0;
    testCases.add(new Object[]{reader, duration, requiredNumberOfLines, expectedNumberOfLines});

    // test case of zero required lines
    reader = mockReader(availableNumberOfLines);
    duration = Duration.ZERO;
    requiredNumberOfLines = 0;
    expectedNumberOfLines = 0;
    testCases.add(new Object[]{reader, duration, requiredNumberOfLines, expectedNumberOfLines});

    // test case of normal usage
    reader = mockReader(availableNumberOfLines);
    duration = Duration.ofSeconds(5);
    requiredNumberOfLines = availableNumberOfLines / 2;
    expectedNumberOfLines = availableNumberOfLines / 2;
    testCases.add(new Object[]{reader, duration, requiredNumberOfLines, expectedNumberOfLines});

    // test case of 2x available required lines
    reader = mockReader(availableNumberOfLines);
    duration = Duration.ofSeconds(5);
    requiredNumberOfLines = availableNumberOfLines * 2;
    expectedNumberOfLines = availableNumberOfLines;
    testCases.add(new Object[]{reader, duration, requiredNumberOfLines, expectedNumberOfLines});

    return testCases.iterator();
  }

  private static BufferedReader mockReader(int availableNumberOfLines) throws IOException {
    val bufferedReader = mock(BufferedReader.class);

    when(bufferedReader.readLine()).thenAnswer(new Answer<String>() {
      int lineIndex = 0;

      @Override
      public String answer(InvocationOnMock invocationOnMock) throws Throwable {
        return lineIndex++ < availableNumberOfLines ? "some string" : null;
      }
    });

    return bufferedReader;
  }
}
