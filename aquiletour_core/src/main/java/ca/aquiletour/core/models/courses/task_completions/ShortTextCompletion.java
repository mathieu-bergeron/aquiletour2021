package ca.aquiletour.core.models.courses.task_completions;

public class ShortTextCompletion extends AtomicTaskCompletion {
	
	private String text = "";

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
