package support;

/**
 * Created by Richard Vowles on 9/10/17.
 */
public class SlackText {
	private String text;

	public SlackText(String text) {
		this.text = text;
	}

	public SlackText() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
