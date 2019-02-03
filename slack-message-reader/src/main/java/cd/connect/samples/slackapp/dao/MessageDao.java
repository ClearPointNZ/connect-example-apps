package cd.connect.samples.slackapp.dao;

import net.stickycode.stereotype.configured.PostConfigured;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;

import javax.inject.Inject;
import java.io.IOException;

public class MessageDao {

  @Inject
  private TransportClient client;

  @PostConfigured
  public void init() throws IOException {
    final IndicesExistsResponse response = client
      .admin()
      .indices()
      .prepareExists("messages")
      .get(TimeValue.timeValueMillis(1000));

    if (!response.isExists()) {
//            XContentBuilder mapping = jsonBuilder()
//                    .startObject("properties")
//                        .startObject("userId")
//                            .field("type", "string")
//                            .field("store", "yes")
//                        .endObject()
//                        .startObject("timestamp")
//                            .field("type", "date")
//                            .field("store", "yes")
//                        .endObject()
//                    .endObject();

      client.admin().indices().prepareCreate("messages")
//                    .addMapping("message", mapping)
        .get();
    }
  }


}
