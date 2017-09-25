const mockData = {
	"channels":
		[
			{
				"channel": "random",
				"messageCount": 5,
				"sentiment": 5
			},
			{
				"channel": "D0U3TNKKJ",
				"messageCount": 5,
				"sentiment": 5
			}
			,
			{
				"channel": "foodstuffs - alerts",
				"messageCount": 19,
				"sentiment": 9
			}
			,
			{
				"channel": "gdt - cd - pipeline",
				"messageCount": 15,
				"sentiment": 5
			}
			,
			{
				"channel": "connect - testing",
				"messageCount": 1,
				"sentiment": 1
			}
			,
			{
				"channel": "connect",
				"messageCount": 1,
				"sentiment": 1
			}
		]
}

export const getSlackSentiment: any = mockData.channels;