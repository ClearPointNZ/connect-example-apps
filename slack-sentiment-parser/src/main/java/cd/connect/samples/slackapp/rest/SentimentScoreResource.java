package cd.connect.samples.slackapp.rest;

import cd.connect.samples.slackapp.api.SentimentScore;
import cd.connect.samples.slackapp.api.SlackMessages;
import cd.connect.samples.slackapp.service.SentimentScoreService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SentimentScoreResource implements cd.connect.samples.slackapp.api.SentimentScoreService {

    @Inject
	SentimentScoreService sentimentScoreService;

	@Override
	public SentimentScore pOSTSentimentScore(SlackMessages messages) {
		SentimentScore sentimentScore = new SentimentScore();
		sentimentScore.setScore(sentimentScoreService.getSentimentScore(messages));
		return sentimentScore;
	}

}
