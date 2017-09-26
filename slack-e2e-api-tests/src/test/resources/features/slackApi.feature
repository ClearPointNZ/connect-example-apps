
Feature: Slack Sentiment Analyser
  AS a slack sentiment analyser that can monitor a given slack channel
  I WANT to be able to extract message count and sentiments from that channel
  SO THAT sentiments from those channels can be analysed

  Scenario Outline: Get messages sent through slack channel
	Given a message is sent to a slack channel
	Then a valid response is received with <status>

	Examples:
	  | status |
	  | 200    |

  Scenario: Get message count from slack sentiment analyser api
	Given a message count is taken before sending a message to slack channel
	When a message is sent to a slack channel
	And I wait for 2 seconds
	Then the message count of slack sentiment analyser api should be increased to one
