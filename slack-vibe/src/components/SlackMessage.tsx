import * as React from "react";
import { getSlackSentiment } from '../api/SlackSentimentAPI';

interface slackSentiment {
	channel: string
	messageCount: number
	sentiment: number
}

export class SlackMessage extends React.Component<any, any> {

	render() {
		return (
			<div>
				<div className='container' style={{'textAlign': 'center'}}>
					<h3>Slack Vibe</h3>
					<table>
						<tbody>
						<tr>
							<th>Channel</th>
							<th>Message</th>
							<th>Sentiment</th>
						</tr>
						{getSlackSentiment.map((m: slackSentiment) => (
							<tr key={m.channel}>
								<td>
									{m.channel}
								</td>
								<td>
									{m.messageCount}
								</td>
								<td>
									{m.sentiment}
								</td>
							</tr>
						))}
						</tbody>
					</table>
				</div>
			</div>
		);
	}
}