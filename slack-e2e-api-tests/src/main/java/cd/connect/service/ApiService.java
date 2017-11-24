package cd.connect.service;

import cd.connect.samples.slackapp.api.MessagesService;
import cd.connect.samples.slackapp.api.SentimentService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ApiService {

	private Client client = ClientBuilder.newClient(new ClientConfig());
	private WebTarget webTarget = client.target(System.getProperty("sentiment.analyser.api"));
	private WebTarget target = client.target(System.getProperty("services.mock.api"));

	public MessagesService messagesApi() {
		return WebResourceFactory.newResource(MessagesService.class, target);
	}

	public SentimentService sentimentApi() {
		return WebResourceFactory.newResource(SentimentService.class, webTarget);
	}

}
