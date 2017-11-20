package cd.connect.samples.slackapp.rest;

import cd.connect.samples.slackapp.api.*;
import cd.connect.samples.slackapp.api.Sentiments;
import cd.connect.samples.slackapp.dao.SentimentDao;
import cd.connect.samples.slackapp.model.SlackMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Singleton
public class SentimentResource implements SentimentService{

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
    SentimentDao sentimentDao;

	@Inject
	SentimentScoreService sentimentScoreService;

    @Override
    public Sentiments gETSentiment() {

        Sentiments sentiments = new Sentiments();
        ArrayList<Channel> channels = new ArrayList<>();
        sentimentDao.getSentiment().entrySet().forEach(stringListEntry -> {
            Channel e = new Channel();
            e.setChannel(stringListEntry.getKey());
            e.setMessageCount(new BigDecimal(stringListEntry.getValue().size()));
			SlackMessages slackMessages = new SlackMessages();
			slackMessages.setMessages(stringListEntry.getValue().stream().map(SlackMessage::getMessageContent).collect(Collectors.toList()));
            log.info("Getting sentiment scores for channel: {}, slack messages: {}", e.getChannel(), slackMessages);
			e.setSentiment(new BigDecimal(sentimentScoreService.pOSTSentimentScore(slackMessages).getScore()));
            channels.add(e);

        });
        sentiments.setChannels(channels);
        return sentiments;
    }

	@Override
	public String deleteSentiments() {
		sentimentDao.deleteSentiments();
		return "OK";
	}
}
