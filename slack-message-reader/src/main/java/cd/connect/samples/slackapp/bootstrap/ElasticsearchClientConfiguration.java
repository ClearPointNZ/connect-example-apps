package cd.connect.samples.slackapp.bootstrap;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticsearchClientConfiguration {

  @Bean
  TransportClient transportClient() throws UnknownHostException {
    return new PreBuiltTransportClient(
      Settings.builder()
        .put(ClusterName.CLUSTER_NAME_SETTING.getKey(), "slacklistener")
        .build()
    )
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(System.getProperty("elasticsearch.host")), 9300));
  }

}
