Feature: Slack Sentiment Analyser
  AS a slack sentiment analyser that can monitor a given slack channel
  I WANT to be able to extract message count and sentiments from that channel
  SO THAT sentiments from those channels can be analysed

  @Regression
  Scenario: Get message count from slack sentiment analyser api
	Given a message count is taken before sending a message to slack channel
	When a message is sent to a slack channel
	  | message              |
	  | It is nice and sunny |
	Then the message count of slack sentiment analyser api should be increased by one

  @Regression
  Scenario Outline: Get sentiment count as 0 when a very positive message is sent to a slack channel
	Given I reset the counter
	And a happy message is sent to a slack channel
	  | message                      |
	  | It is a very happy day       |
	  | It is an awesome day at work |
	  | It is a great day            |
	When I call slack sentiment analyser api
	Then the sentiment count of slack sentiment analyser api should be <sentimentCount>

	Examples:
	  | sentimentCount |
	  | 0              |

  @Regression
  Scenario Outline: Get sentiment count as 4 when a very negative message is sent to a slack channel
	Given I reset the counter
	And a sad message is sent to a slack channel
	  | message          |
	  | That is too bad  |
	  | That is horrible |
	When I call slack sentiment analyser api
	Then the sentiment count of slack sentiment analyser api should be <sentimentCount>

	Examples:
	  | sentimentCount |
	  | 4              |

  @Regression
  Scenario Outline: Get sentiment count as 4 when a neutral message is sent to a slack channel
	Given I reset the counter
	And a neutral message is sent to a slack channel
	  | message         |
	  | I have a pen    |
	When I call slack sentiment analyser api
	Then the sentiment count of slack sentiment analyser api should be <sentimentCount>

	Examples:
	  | sentimentCount |
	  | 2              |
