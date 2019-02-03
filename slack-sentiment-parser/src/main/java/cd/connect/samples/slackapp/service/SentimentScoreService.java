package cd.connect.samples.slackapp.service;

import cd.connect.samples.slackapp.api.SlackMessages;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.SentimentClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Singleton
public class SentimentScoreService {


  /**
   * Uses Stanford Simple CoreNLP to calculate the average sentiment score for a list of slack messages with the following scales:
   * <p>
   * (0) VERY_POSITIVE
   * (1) POSITIVE
   * (2) NEUTRAL
   * (3) NEGATIVE
   * (4) VERY_NEGATIVE
   *
   * @param messages - object contains a list of slack messages
   * @return average sentiment score
   */
  public Double getSentimentScore(SlackMessages messages) {

    if (messages != null && !CollectionUtils.isEmpty(messages.getMessages())) {

      Document doc = new Document(StringUtils.join(messages.getMessages()));

      double score = doc.sentences().stream().mapToInt(sentence -> sentence.sentiment().ordinal()).average().getAsDouble();

      return new BigDecimal(score).setScale(2, RoundingMode.HALF_UP).doubleValue();

    } else {
      return new Double(SentimentClass.NEUTRAL.ordinal());
    }

  }

}
