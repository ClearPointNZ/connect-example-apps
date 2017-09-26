package steps;

import cd.connect.samples.slackapp.api.Sentimentsummary;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cd.connect.samples.slackapp.api.MessagesService;
import cd.connect.samples.slackapp.api.Messagelist;
import cd.connect.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import static org.fest.assertions.api.Assertions.assertThat;


public class MyStepdefs {

//	private static Logger logger = LoggerFactory.getLogger(MyStepdefs.class);

	private ApiService apiService;
	Messagelist messagelist;
	Response slackList;
	Sentimentsummary sentimentSummary;
	BigDecimal previousCount = new BigDecimal(0);
	BigDecimal currentCountAfterNewMessage = new BigDecimal(0);

	private BigDecimal getCurrentMessageCount(){

		BigDecimal count = new BigDecimal(0);

		sentimentSummary = apiService.sentimentApi().gETSentiment();

		for (int i = 0; i < sentimentSummary.getChannels().size(); i++) {

			String channelName = sentimentSummary.getChannels().get(i).getChannel();

			if (channelName.contentEquals("connect-testing"))

			{
				count = sentimentSummary.getChannels().get(i).getMessageCount();
			}

		}
		return count;

	}

	public MyStepdefs(ApiService apiService) {
		this.apiService = apiService;
	}



	@Given("^I call get messages api for user id (.*) from date (.*) to date (.*)$")
	public void iCallGetMessagesApiForUserUser_idFromTo(String userId, BigDecimal fromDate, BigDecimal toDate) throws Throwable {

				messagelist = apiService.messagesApi().gETMessages(userId, fromDate, toDate);
	}

	@Then("^I should get a list of messages$")
	public void iShouldGetAListOfMessages() throws Throwable {
		assertThat(messagelist).isNotEmpty();
	}

	@Given("^a message is sent to a slack channel$")
	public void aMessageIsSentToSlackApi() throws Throwable {

		slackList = apiService.slackApi();

	}

	@Then("^a valid response is received with (.*)$")
	public void theResponseContainsMessage(int status) throws Throwable {

		assertThat(slackList.toString()).contains("ok");
		assertThat(slackList.getStatus()).isEqualTo(status);

	}

	@And("I wait for (.*) seconds")
	public void wait(int seconds) {
		try {
			Thread.sleep(seconds * 2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("^the message count of slack sentiment analyser api should be increased to one$")
	public void theMessageCountIncreasedToOne() throws Throwable {

		currentCountAfterNewMessage = getCurrentMessageCount();
		assertThat(currentCountAfterNewMessage).isEqualTo(previousCount.add(new BigDecimal(1)));

	}

	@Given("^a message count is taken before sending a message to slack channel$")
	public void previousMessageCount() throws Throwable {

		previousCount = getCurrentMessageCount();

	}

}
