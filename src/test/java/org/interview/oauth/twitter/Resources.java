package org.interview.oauth.twitter;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.io.*;
import java.net.URISyntaxException;

@UtilityClass
public class Resources {

  public static File getFile(final String filePath)
    throws URISyntaxException {

    val classLoader = Resources.class.getClassLoader();
    val resourceUri = classLoader.getResource(filePath).toURI();

    return new File(resourceUri);
  }
}
