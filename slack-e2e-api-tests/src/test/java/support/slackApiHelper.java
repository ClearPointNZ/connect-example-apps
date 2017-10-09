package support;

import cd.connect.jersey.client.JaxrsClientManager;
import cd.connect.spring.jersey.log.JerseyFilteringConfiguration;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

@SuppressWarnings("unchecked")
public class SlackApiHelper {
	private Client client;
	private SlackApi slackApi;

	public SlackApiHelper() {
		JerseyFilteringConfiguration filtering = new JerseyFilteringConfiguration();
		filtering.init();

		client = new JaxrsClientManager(filtering).getClient();

		WebTarget webTarget = client.target(UriBuilder.fromPath(System.getProperty("slack.api.postMessage")).build());

		slackApi = WebResourceFactory.newResource(SlackApi.class, webTarget);
	}

	public String slackMessagePost(String text) {

		try {
			String response = slackApi.postText(new SlackText(text));

			System.out.printf("Slack returned `%s`", response);

			return response;

		} catch (Exception e) {
			System.out.println("Failed to get response");

			throw e;
		}
	}

}
