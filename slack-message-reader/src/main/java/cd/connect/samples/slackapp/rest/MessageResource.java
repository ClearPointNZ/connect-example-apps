package cd.connect.samples.slackapp.rest;

import cd.connect.samples.slackapp.api.Messagelist;
import cd.connect.samples.slackapp.api.MessagesService;

import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
public class MessageResource implements MessagesService {

  @Override
  public Messagelist gETMessages(String userId, BigDecimal fromDate, BigDecimal toDate) {
    return new Messagelist();
  }
}
