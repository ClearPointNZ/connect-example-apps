package support;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by Richard Vowles on 9/10/17.
 */
@Path("/slack-messages-api")
public interface SlackApi {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	String postText(SlackText text);
}
