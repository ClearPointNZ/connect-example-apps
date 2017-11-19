package steps;

import cd.connect.samples.slackapp.api.Channel;
import cd.connect.samples.slackapp.api.Messagelist;
import cd.connect.samples.slackapp.api.Sentimentsummary;
import cd.connect.service.ApiService;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
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
	private String delete;
	private BigDecimal previousCount = new BigDecimal(0);


	private BigDecimal getCurrentMessageCount() {

		sentimentSummary = apiService.sentimentApi().gETSentiment();

		List<BigDecimal> messageCount = sentimentSummary.getChannels().stream()
				.filter(str -> str.getChannel().equalsIgnoreCase("connect-testing"))
				.map(Channel::getMessageCount)
				.collect(Collectors.toList());
		System.out.println("message count is :" + messageCount);
		if (messageCount.isEmpty()) {
			return BigDecimal.valueOf(0);
		}
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
	public void iCallGetMessagesApi(String userId, BigDecimal fromDate, BigDecimal toDate) throws Throwable {

		messagelist = apiService.messagesApi().gETMessages(userId, fromDate, toDate);

}

	@Then("^I should get a list of messages$")
	public void getListOfMessages() throws Throwable {

		assertThat(messagelist).isNotEmpty();

	}

	@Given("^a (?:message|happy message|sad message|neutral message) is sent to a slack channel$")
	public void msgSentToSlackApi(DataTable dataTable) throws Throwable {

		for (DataTableRow row : dataTable.getGherkinRows()) {
			List<String> cells = row.getCells();

			if ("message".equals(cells.get(0))) {
				continue;
			}
			for (int i = 0; i < cells.size(); i++) {
				slackList = slackApiHelper.slackMessagePost(cells.get(i));
			}

		}
	}

	@Then("^a valid response is received$")
	public void responseMessage() throws Throwable {

		assertThat(slackList.toString()).contains("ok");
		assertThat(slackList.getStatus()).isEqualTo(200);

	}

	@Then("^the message count of slack sentiment analyser api should be increased by one$")
	public void messageCountIncrease() throws Throwable {

		assertThat(getCurrentMessageCount()).isEqualTo(previousCount.add(new BigDecimal(1)));

	}

	@Given("^a message count is taken before sending a message to slack channel$")
	public void previousMessageCount() throws Throwable {

		previousCount = getCurrentMessageCount();
		System.out.println("previous count is :" + previousCount);

	}

	@When("^I call slack sentiment analyser api$")
	public void getSlackSentimentAnalyserApi() throws Throwable {

		sentimentSummary = apiService.sentimentApi().gETSentiment();

	}

	@Then("^the sentiment count of slack sentiment analyser api should be (.*)$")
	public void sentimentCountDisplayedAs(BigInteger happyOrSadCount) throws Throwable {

		assertThat(getCurrentSentimentCount().toBigInteger()).isEqualTo(happyOrSadCount);

	}

	@And("^another (.*) is sent to a slack channel$")
	public void VeryHappyMessageIsSent(String text) throws Throwable {

		slackList = slackApiHelper.slackMessagePost(text);
	}

	@Given("^I reset the counter$")
	public void iResetTheCounter() throws Throwable {

		delete = apiService.sentimentApi().deleteSentiments();
		System.out.println("deleted response is :" + delete);

	}
}
