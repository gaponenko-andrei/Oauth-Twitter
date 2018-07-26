package org.interview.oauth.twitter.mapper;

import lombok.val;
import org.interview.oauth.twitter.dto.UserDto;
import org.interview.oauth.twitter.test.dto.TestUserDto;
import org.interview.oauth.twitter.util.ZonedDateTimes;
import org.interview.oauth.twitter.vo.User;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FromUserDtoMapperTest {

  private FromUserDtoMapper mapper;
  private UserDto input;
  private User output;


  @BeforeTest
  public void setUp() {
    this.mapper = new FromUserDtoMapper();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void throwsOnNullArgument() {

    /* Arrange */
    givenInputIs(null);

    /* Act */
    applyMapper();
  }

  @Test(dataProvider = "testCasesProvider")
  public void appliesCorrectly(final UserDto userDto, final User expectedUser) {

    /* Arrange */
    givenInputIs(userDto);

    /* Act */
    applyMapper();

    /* Assert */
    assertOutputIs(expectedUser);
  }

  private void givenInputIs(final UserDto userDto) {
    this.input = userDto;
  }

  private void applyMapper() {
    this.output = mapper.apply(input);
  }

  private void assertOutputIs(final User expectedUser) {
    assertEquals(output, expectedUser);
    assertEquals(output.state(), expectedUser.state());
  }

  @DataProvider(name = "testCasesProvider")
  private static Object[][] getTestCases() {
    val input = new TestUserDto().delegate();

    val creationDate = ZonedDateTimes.parse(input.creationDate());

    val expectedUserState =
      User.State
        .builder()
        .creationDate(creationDate)
        .screenName(input.screenName())
        .build();

    val expectedUser =
      User.builder()
        .id(input.id())
        .state(expectedUserState)
        .build();

    return new Object[][]{{input, expectedUser}};
  }
}
