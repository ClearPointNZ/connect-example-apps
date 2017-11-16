package cd.connect.samples.slackapp.dao;

import cd.connect.samples.slackapp.model.SlackMessage;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class SentimentDao {

    Map<String, List<SlackMessage>> messages = new HashMap<>();

    public void addMessage(SlackMessage message) {
        String channelName = message.getChannel().getName();
        if(!messages.containsKey(channelName)){
            messages.put(channelName, new ArrayList<SlackMessage>());
        }
        messages.get(channelName).add(message);

    }


    public Map<String, List<SlackMessage>> getSentiment() {
        return new HashMap<>(messages);
    }

	public synchronized void deleteSentiments() {
		messages.clear();
	}

}
