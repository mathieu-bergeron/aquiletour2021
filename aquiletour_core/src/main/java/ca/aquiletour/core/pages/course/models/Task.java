package ca.aquiletour.core.pages.course.models;

import ca.ntro.core.models.NtroModelValue;

public class Task implements NtroModelValue {
	
	private String id = "";
	private String title = "";

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
