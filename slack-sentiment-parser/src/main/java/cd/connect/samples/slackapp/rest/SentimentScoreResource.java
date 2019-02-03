package cd.connect.samples.slackapp.rest;

import cd.connect.samples.slackapp.api.SentimentScore;
import cd.connect.samples.slackapp.api.SlackMessages;
import cd.connect.samples.slackapp.service.SentimentScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SentimentScoreResource implements cd.connect.samples.slackapp.api.SentimentScoreService {

  @Inject
  SentimentScoreService sentimentScoreService;
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public SentimentScore pOSTSentimentScore(SlackMessages messages) {

    SentimentScore sentimentScore = new SentimentScore();

    try {
      sentimentScore.setScore(sentimentScoreService.getSentimentScore(messages));
    } catch (Exception ignore) {
      log.error("Failed to get sentiment score for slack messages: " + ignore.getMessage(), ignore);
    }

    return sentimentScore;

  }

}
