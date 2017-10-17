
Feature: Slack Sentiment Analyser
  AS a slack sentiment analyser that can monitor a given slack channel
  I WANT to be able to extract message count and sentiments from that channel
  SO THAT sentiments from those channels can be analysed


  Scenario Outline: Get message count from slack sentiment analyser api
	Given a message count is taken before sending a message to slack channel
	When a <message> is sent to a slack channel
	Then the message count of slack sentiment analyser api should be increased by one

	Examples:
	  | message                   |
	  | Connect Slack API Testing |


  Scenario Outline: Get sentiment count as 10 when a happy message is sent to a slack channel
	Given a <happyMessage> is sent to a slack channel
	When I call slack sentiment analyser api
	Then the sentiment count of slack sentiment analyser api should be <sentimentCount>

	Examples:
	  | happyMessage               | sentimentCount |
	  | I am having an awesome day | 10             |


  Scenario Outline: Get sentiment count as 1 when a sad message is sent to a slack channel
	Given a <sadMessage> is sent to a slack channel
	When I call slack sentiment analyser api
	Then the sentiment count of slack sentiment analyser api should be <sentimentCount>

	Examples:
	  | sadMessage               | sentimentCount |
	  | Its a very bad day today | 1              |
