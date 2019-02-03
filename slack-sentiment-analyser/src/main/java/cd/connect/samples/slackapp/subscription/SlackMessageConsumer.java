package cd.connect.samples.slackapp.subscription;

import cd.connect.samples.slackapp.dao.SentimentDao;
import cd.connect.samples.slackapp.model.SlackMessage;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.stickycode.stereotype.configured.PostConfigured;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.io.IOException;

public class SlackMessageConsumer implements MessageListener {

  @Inject
  SentimentDao sentimentDao;
  ObjectMapper mapper = new ObjectMapper();
  private Logger log = LoggerFactory.getLogger(this.getClass());

  public SlackMessageConsumer() {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @PostConfigured
  public void init() throws JMSException {

    String brokerUrl = System.getProperty("activemq.brokerurl", ActiveMQConnection.DEFAULT_BROKER_URL);

    log.info("Got activemq brokerUrl=" + brokerUrl);

    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

    Connection connection = connectionFactory.createConnection();
    connection.start();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    Topic topic = session.createTopic("slack-messages");

    MessageConsumer consumer = session.createConsumer(topic);
    consumer.setMessageListener(this);
  }

  @Override
  public void onMessage(Message message) {
    if (message instanceof TextMessage) {
      try {
        String text = ((TextMessage) message).getText();
        System.out.println("text:" + text);
        sentimentDao.addMessage(mapper.readValue(text, SlackMessage.class));
      } catch (JMSException | IOException e) {
        e.printStackTrace();
      }
    }

  }
}
