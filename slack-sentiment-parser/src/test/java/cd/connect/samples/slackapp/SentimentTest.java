package cd.connect.samples.slackapp;


import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

@Ignore
public class SentimentTest {

	@Test
	public void testSentiment() {

		// use simple package
		//		public enum SentimentClass {
		//			VERY_POSITIVE,
		//			POSITIVE,
		//			NEUTRAL,
		//			NEGATIVE,
		//			VERY_NEGATIVE,
		//			;
		Document doc = new Document("It is a good weather. .I am not feeling well. It is not too bad.");

		for (Sentence sentence : doc.sentences()) {
			System.out.println(sentence.sentiment().ordinal() + "\t" + sentence.sentiment() + "\t" + sentence);
		}

		// StanfordCoreNLP
		Properties props = new Properties();

		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation annotation = new Annotation("IT was very fantastic experience." +
				" it was a pathetice experience");
		pipeline.annotate(annotation);
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			final Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
			final SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
			String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
			System.out.println(sm + "\t" + sentiment + "\t" + sentence);
			System.out.println(sm + "\t" + sentiment + "\t" + sentence);
		}

	}

}
