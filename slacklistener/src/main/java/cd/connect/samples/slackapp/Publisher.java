package cd.connect.samples.slackapp;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;


public class Publisher {

  private static final Logger log = LoggerFactory.getLogger(Publisher.class);

  private String clientId;
  private Connection connection;
  private Session session;
  private MessageProducer messageProducer;

  public void create(String clientId, String topicName) throws JMSException {
    this.clientId = clientId;

    String brokerUrl = System.getProperty("activemq.brokerurl", ActiveMQConnection.DEFAULT_BROKER_URL);

    log.info("Got brokerurl: " + brokerUrl);

    // create a Connection Factory
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

    // create a Connection
    connection = connectionFactory.createConnection();
    connection.setClientID(clientId);

    // create a Session
    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    // create the Topic to which messages will be sent
    Topic topic = session.createTopic(topicName);

    // create a MessageProducer for sending messages
    messageProducer = session.createProducer(topic);
  }

  public void closeConnection() throws JMSException {
    connection.close();
  }


  public void sendMessgae(String text) throws JMSException {
    // create a JMS TextMessage
    TextMessage textMessage = session.createTextMessage(text);

    // send the message to the topic destination
    messageProducer.send(textMessage);

    log.debug(clientId + ": sent message with text='{}'", text);
  }
}

