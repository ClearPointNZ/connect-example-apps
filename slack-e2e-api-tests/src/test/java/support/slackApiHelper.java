package support;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.json.simple.JSONObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@SuppressWarnings("unchecked")
public class SlackApiHelper {

	private Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
	private WebTarget webTarget = client.target(UriBuilder.fromPath(System.getProperty("slack.api.postMessage")).build());

	public Response slackMessagePost(String text) {

		JSONObject json = new JSONObject();
		json.put("text", text);

		client = ClientBuilder.newClient();

		try {
			Response response = webTarget.request().post(Entity.entity(json,MediaType.APPLICATION_JSON));
			System.out.println(response.readEntity(String.class));

			return response;

		} catch (Exception e) {
			System.out.println("Failed to get response");

			throw e;
		}
	}

}
