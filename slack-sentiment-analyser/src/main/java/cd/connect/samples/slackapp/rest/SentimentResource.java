package cd.connect.samples.slackapp.rest;

import cd.connect.samples.slackapp.api.Channel;
import cd.connect.samples.slackapp.api.SentimentScoreService;
import cd.connect.samples.slackapp.api.SentimentService;
import cd.connect.samples.slackapp.api.Sentimentsummary;
import cd.connect.samples.slackapp.api.SlackMessages;
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
    public Sentimentsummary gETSentiment() {

        Sentimentsummary sentimentsummary = new Sentimentsummary();
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
        sentimentsummary.setChannels(channels);
        return sentimentsummary;
    }
}
