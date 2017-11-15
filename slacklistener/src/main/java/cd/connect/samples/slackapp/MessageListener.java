package cd.connect.samples.slackapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.util.Arrays;
import java.util.List;

public class MessageListener implements SlackMessagePostedListener {

	Logger log = LoggerFactory.getLogger(MessageListener.class);

	Publisher publisher = new Publisher();
	ObjectMapper mapper = new ObjectMapper();
	List<String> slackChannels;

	MessageListener() throws JMSException {
		publisher.create("slackListener", "slack-messages");
		init();
	}

	public void init() {

		String slackChannelsProperties = System.getProperty("slack.channels", "connect-testing");
		log.info("Got properties slack.channels={}", slackChannelsProperties);

		slackChannels = Arrays.asList(slackChannelsProperties.split(","));


	}

	public void onEvent(SlackMessagePosted slackMessagePosted, SlackSession slackSession) {
		try {

			SlackChannel channel = slackMessagePosted.getChannel();

			if (slackChannels.contains(channel.getName())) {


				ObjectNode jsonNodes = mapper.valueToTree(slackMessagePosted);
				jsonNodes.replace("channel", mapper.valueToTree(channel));
				jsonNodes.replace("sender", mapper.valueToTree(slackMessagePosted.getSender()));
				publisher.sendMessgae(mapper.writeValueAsString(jsonNodes));

				log.info("Sent message for channel: {} message: {}", channel.getName(), slackMessagePosted.getMessageContent());

			} else {

				log.info("Ignored message for channel: {} message: {}", channel.getName(), slackMessagePosted.getMessageContent());

			}
		} catch (JsonProcessingException | JMSException e) {
			log.info("unable to parse message:" + slackMessagePosted.getMessageContent());
		}
	}
}
