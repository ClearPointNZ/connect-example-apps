package steps;

import cd.connect.samples.slackapp.api.HourlySentiment;
import cd.connect.samples.slackapp.api.Messagelist;
import cd.connect.samples.slackapp.api.Sentimentsummary;
import cd.connect.service.ApiService;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
import support.SlackApiHelper;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import static org.fest.assertions.api.Assertions.assertThat;

public class MyStepdefs {
	private ApiService apiService;
	private SlackApiHelper slackApiHelper;
	private Messagelist messagelist;
	private Response slackList;
	private Sentimentsummary sentimentSummary;
	private Integer previousCount;

	private Integer getCurrentMessageCount() {

		List<Integer> messageCount = sentimentSummary.getChannels().get(0).getHourlySentiments().stream()
				.map(HourlySentiment::getTotalMessages)
				.collect(Collectors.toList());

		return messageCount.get(0);

	}

	private Double getCurrentSentimentCount() {

		List<Double> sentimentCount = sentimentSummary.getChannels().get(0).getHourlySentiments().stream()
				.map(HourlySentiment::getSentiment)
				.collect(Collectors.toList());

		return sentimentCount.get(0);

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

	@Given("^a (?:message|positive message|negative message|neutral message) is sent to a slack channel$")
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

		assertThat(getCurrentMessageCount()).isEqualTo(previousCount + 1);

	}

	@Given("^a message count is taken$")
	public void previousMessageCount() throws Throwable {

		previousCount = getCurrentMessageCount();

	}

	@When("^I call slack sentiment analyser api$")
	public void getSlackSentimentAnalyserApi() throws Throwable {

		sentimentSummary = apiService.sentimentApi().gETSentiment();

	}

	@Then("^the sentiment count of slack sentiment analyser api should be (.*)$")
	public void sentimentCountDisplayedAs(Double positiveNegativeOrNeutralCount) throws Throwable {

		assertThat(getCurrentSentimentCount()).isEqualTo(positiveNegativeOrNeutralCount);

	}

	@Given("^I reset the sentiments counter$")
	public void iResetTheCounter() throws Throwable {

		apiService.sentimentApi().deleteSentiments();

	}
}
