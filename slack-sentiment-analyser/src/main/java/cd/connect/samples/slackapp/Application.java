package cd.connect.samples.slackapp;

import cd.connect.jersey.client.JaxrsClientManager;
import cd.connect.samples.slackapp.api.EnableSlackSentimentParserService;
import cd.connect.samples.slackapp.service.SentimentScheduledService;
import cd.connect.samples.slackapp.subscription.SlackMessageConsumer;
import cd.connect.spring.jersey.BaseWebApplication;
import org.springframework.context.annotation.Import;

@Import({SlackAppGenConfig.class, JerseyDataConfig.class, SlackMessageConsumer.class, SentimentScheduledService.class, JaxrsClientManager.class, EnableSlackSentimentParserService.class})
public class Application extends BaseWebApplication {
}
