package cd.connect.samples.slackapp.rest;

import cd.connect.samples.slackapp.api.Channel;
import cd.connect.samples.slackapp.api.SentimentService;
import cd.connect.samples.slackapp.api.Sentimentsummary;
import cd.connect.samples.slackapp.dao.SentimentDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
public class SentimentResource implements SentimentService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	SentimentDao sentimentDao;

	@Override
	public Sentimentsummary gETSentiment() {

		Sentimentsummary sentimentsummary = new Sentimentsummary();
		ArrayList<Channel> channels = new ArrayList<>();
		sentimentDao.getSentiments().entrySet().forEach(item -> {
			Channel channel = new Channel();
			channel.setChannel(item.getKey());
			channel.setHourlySentiments(item.getValue());
			channels.add(channel);

		});
		sentimentsummary.setChannels(channels);
		return sentimentsummary;
	}

	@Override
	public String deleteSentiments() {
		sentimentDao.deleteSentiments();
		return "OK";
	}
}
