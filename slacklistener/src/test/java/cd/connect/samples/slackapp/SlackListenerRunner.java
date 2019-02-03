package cd.connect.samples.slackapp;

import bathe.BatheBooter;

import java.io.File;
import java.io.IOException;

public class SlackListenerRunner {

  public static void main(String[] args) throws IOException {
    if (!new File("src/test/resources").exists()) {
      throw new RuntimeException("Please ensure this test is run in the home directory of the project.");
    }

    // this loads all the stuff in
    new BatheBooter().runWithLoader(SlackListenerApplication.class.getClassLoader(), null, SlackListenerApplication.class.getName(),
      new String[]{"-Psrc/test/resources/slack-listener.properties"});
  }
}
