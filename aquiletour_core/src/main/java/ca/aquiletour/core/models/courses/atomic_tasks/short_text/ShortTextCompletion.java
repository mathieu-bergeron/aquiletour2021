package ca.aquiletour.core.models.courses.atomic_tasks.short_text;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;

public class ShortTextCompletion extends AtomicTaskCompletion {
	
	private String text = "";

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
