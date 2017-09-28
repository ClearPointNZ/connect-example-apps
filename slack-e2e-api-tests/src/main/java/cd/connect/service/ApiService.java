package cd.connect.service;

import cd.connect.samples.slackapp.api.MessagesService;
import cd.connect.samples.slackapp.api.SentimentService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.filter.LoggingFilter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ApiService {

	private Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
	private WebTarget webTarget = client.target(System.getProperty("sentimentAnalyser.api"));

	public MessagesService messagesApi() {
		return WebResourceFactory.newResource(MessagesService.class, webTarget);
	}

	public SentimentService sentimentApi() {
		return WebResourceFactory.newResource(SentimentService.class, webTarget);
	}

}
