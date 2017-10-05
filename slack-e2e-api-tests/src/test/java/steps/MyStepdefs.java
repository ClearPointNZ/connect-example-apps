package steps;

import cd.connect.samples.slackapp.api.Channel;
import cd.connect.samples.slackapp.api.Messagelist;
import cd.connect.samples.slackapp.api.Sentimentsummary;
import cd.connect.service.ApiService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import support.SlackApiHelper;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.api.Assertions.assertThat;

public class MyStepdefs {
	private ApiService apiService;
	private SlackApiHelper slackApiHelper;
	private Messagelist messagelist;
	private Response slackList;
	private Sentimentsummary sentimentSummary;
	private BigDecimal previousCount = new BigDecimal(0);


	private BigDecimal getCurrentMessageCount() {

		sentimentSummary = apiService.sentimentApi().gETSentiment();

		List<BigDecimal> messageCount = sentimentSummary.getChannels().stream()
				.filter(str -> str.getChannel().contentEquals("connect-testing"))
				.map(Channel::getMessageCount)
				.collect(Collectors.toList());

		return messageCount.get(0);
	}


	private BigDecimal getCurrentSentimentCount() {

		BigDecimal sentimentCount = new BigDecimal(0);

		for (int i = 0; i < sentimentSummary.getChannels().size(); i++) {
			String channelName = sentimentSummary.getChannels().get(i).getChannel();

			if (channelName.contentEquals("connect-testing")) {
				sentimentCount = sentimentSummary.getChannels().get(i).getSentiment();
			}

		}
		return sentimentCount;
	}

	public MyStepdefs(ApiService apiService, SlackApiHelper slackApiHelper) {

		this.apiService = apiService;
		this.slackApiHelper = slackApiHelper;

	}

	@Given("^I call get messages api for user id (.*) from date (.*) to date (.*)$")
	public void iCallGetMessagesApiForUserUser_idFromTo(String userId, BigDecimal fromDate, BigDecimal toDate) throws Throwable {

		messagelist = apiService.messagesApi().gETMessages(userId, fromDate, toDate);

}

	@Then("^I should get a list of messages$")
	public void iShouldGetAListOfMessages() throws Throwable {

		assertThat(messagelist).isNotEmpty();

	}

	@Given("^a (.*) is sent to a slack channel$")
	public void aTextIsSentToSlackApi(String text) throws Throwable {

		slackList = slackApiHelper.slackMessagePost(text);

	}

	@Then("^a valid response is received with (.*)$")
	public void theResponseContainsMessage(int status) throws Throwable {

		assertThat(slackList.toString()).contains("ok");
		assertThat(slackList.getStatus()).isEqualTo(status);

	}

	@Then("^the message count of slack sentiment analyser api should be increased by one$")
	public void theMessageCountIncreasedToOne() throws Throwable {

		assertThat(getCurrentMessageCount()).isEqualTo(previousCount.add(new BigDecimal(1)));

	}

	@Given("^a message count is taken before sending a message to slack channel$")
	public void previousMessageCount() throws Throwable {

		previousCount = getCurrentMessageCount();

	}

	@When("^I call slack sentiment analyser api$")
	public void iCallSlackSentimentAnalyserApi() throws Throwable {

		sentimentSummary = apiService.sentimentApi().gETSentiment();

	}

	@Then("^the sentiment count of slack sentiment analyser api should be (.*)$")
	public void sentimentCountDisplayedAs(BigInteger happyOrSadCount) throws Throwable {

		assertThat(getCurrentSentimentCount().toBigInteger()).isEqualTo(happyOrSadCount);

	}

}
