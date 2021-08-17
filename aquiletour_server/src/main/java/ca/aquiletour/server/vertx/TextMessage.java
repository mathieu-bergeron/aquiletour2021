package ca.aquiletour.server.vertx;

public class TextMessage {
	
	private String text = "";

	public TextMessage(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}

}
