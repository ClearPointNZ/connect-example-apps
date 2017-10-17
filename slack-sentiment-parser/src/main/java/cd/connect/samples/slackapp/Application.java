package cd.connect.samples.slackapp;

import cd.connect.spring.jersey.BaseWebApplication;
import org.springframework.context.annotation.Import;

@Import({SlackAppGenConfig.class, JerseyDataConfig.class})
public class Application extends BaseWebApplication {
}
