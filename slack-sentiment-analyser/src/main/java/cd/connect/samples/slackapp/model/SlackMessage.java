package cd.connect.samples.slackapp.model;

public class SlackMessage {

    String messageContent;
    Channel channel;
    Sender sender;

    private SlackMessage() {
    }

    public Channel getChannel() {
        return channel;
    }

	public String getMessageContent() {
		return messageContent;
	}
}
