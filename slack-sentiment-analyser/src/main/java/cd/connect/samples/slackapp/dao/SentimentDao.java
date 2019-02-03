package cd.connect.samples.slackapp.dao;

import cd.connect.samples.slackapp.api.HourlySentiment;
import cd.connect.samples.slackapp.api.SentimentScoreService;
import cd.connect.samples.slackapp.api.SlackMessages;
import cd.connect.samples.slackapp.model.SlackMessage;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class SentimentDao {

  Map<String, List<SlackMessage>> currentHourMessages = new HashMap<>();
  @Inject
  SentimentScoreService sentimentScoreService;
  private Logger log = LoggerFactory.getLogger(this.getClass());
  private Map<String, CircularFifoQueue<HourlySentiment>> sentiments = new HashMap<>();

  public void addMessage(SlackMessage message) {
    String channelName = message.getChannel().getName();
    int currentHour = getCurrentHour();
    log.info("Adding message for hour {}: {}", currentHour, message);
    if (!currentHourMessages.containsKey(channelName)) {
      currentHourMessages.put(channelName, new ArrayList<>());
    }
    currentHourMessages.get(channelName).add(message);

    updateCurrentHourChannelSentiment(channelName, currentHour, true);


  }

  private CircularFifoQueue<HourlySentiment> getChannelQueue(String channel) {

    if (!sentiments.containsKey(channel)) {
      sentiments.put(channel, new CircularFifoQueue(12));
    }

    return sentiments.get(channel);

  }

  private void updateCurrentHourChannelSentiment(String channel, int currentHour, boolean updateSentiment) {

    CircularFifoQueue<HourlySentiment> channelSentiments = getChannelQueue(channel);

    HourlySentiment hourlySentiment = null;

    if (channelSentiments.size() > 0) {
      hourlySentiment = channelSentiments.get(channelSentiments.size() - 1);
    }

    if (hourlySentiment == null) {

      hourlySentiment = newHourlySentiment(currentHour);
      channelSentiments.add(hourlySentiment);

      if (updateSentiment) {
        updateCurrentHourSentiment(channel, hourlySentiment);
      }

    } else if (hourlySentiment.getHour().intValue() != currentHour) {

      if (updateSentiment) {
        updateCurrentHourSentiment(channel, hourlySentiment);
      }

      hourlySentiment = newHourlySentiment(currentHour);
      channelSentiments.add(hourlySentiment);

      currentHourMessages.get(channel).clear();

    } else {
      if (updateSentiment) {
        updateCurrentHourSentiment(channel, hourlySentiment);
      }
    }

    log.info("Current hour is: {} hourly sentiment: {}", currentHour, hourlySentiment);

  }

  private HourlySentiment newHourlySentiment(int hour) {
    HourlySentiment hourlySentiment = new HourlySentiment();
    hourlySentiment.setHour(hour);
    hourlySentiment.setTotalMessages(0);
    return hourlySentiment;
  }


  private int getCurrentHour() {
    return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
  }

  private void updateCurrentHourSentiment(String channelName, HourlySentiment hourlySentiment) {

    List<SlackMessage> slackMessages = currentHourMessages.get(channelName);

    hourlySentiment.setTotalMessages(CollectionUtils.isEmpty(slackMessages) ? 0 : slackMessages.size());
    hourlySentiment.setSentiment(getChannelSentiment(channelName, slackMessages));

  }

  private Double getChannelSentiment(String channel, List<SlackMessage> channelMessages) {

    if (CollectionUtils.isEmpty(channelMessages)) {
      return null;
    }

    SlackMessages slackMessages = new SlackMessages();
    slackMessages.setMessages(channelMessages.stream().map(SlackMessage::getMessageContent).collect(Collectors.toList()));
    log.info("Getting sentiment scores for channel: {}, slack messages: {}", channel, slackMessages);

    return sentimentScoreService.pOSTSentimentScore(slackMessages).getScore();

  }


  public Map<String, List<HourlySentiment>> getSentiments() {
    return sentiments.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue())));
  }

  public synchronized void clearCurrentHourMessages() {
    int currentHour = getCurrentHour();
    log.info("Clear current hour ({}) messages at {}", currentHour, new Date());
    currentHourMessages.keySet().forEach(channel -> {
      updateCurrentHourChannelSentiment(channel, currentHour, false);
      currentHourMessages.get(channel).clear();
    });
  }


  public synchronized void deleteSentiments() {
    sentiments.clear();
    currentHourMessages.clear();
  }

}
