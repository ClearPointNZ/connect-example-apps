package cd.connect.service;

import cd.connect.samples.slackapp.api.MessagesService;
import cd.connect.samples.slackapp.api.Messagelist;
import cd.connect.samples.slackapp.api.SentimentService;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.filter.LoggingFilter;
import org.json.simple.JSONObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class ApiService {


	private Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
	private WebTarget webTarget = client.target(System.getProperty("services.api"));

	public MessagesService messagesApi() {
		return WebResourceFactory.newResource(MessagesService.class, webTarget);
	}
	public SentimentService sentimentApi() {
		return WebResourceFactory.newResource(SentimentService.class, webTarget);
	}
	public Response slackApi() {

		client = ClientBuilder.newClient();
		JSONObject body = new JSONObject();
		body.put("text", "Connect slack api\ntesting");
		String url = "https://hooks.slack.com/services/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";

		Form form = new Form();

		WebTarget webTarget = client.target(UriBuilder.fromPath(url).build());

		try {
			Response response  = webTarget.request().post(Entity.entity(body, MediaType.APPLICATION_JSON));
			System.out.println(response.readEntity(String.class));

			return response;

		} catch (Exception e) {
			System.out.println("Failed to get response");

			throw e;
		}
	}

}
