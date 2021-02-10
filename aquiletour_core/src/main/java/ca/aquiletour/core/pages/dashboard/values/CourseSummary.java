package ca.aquiletour.core.pages.dashboard.values;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class CourseSummary extends NtroModelValue {

	private String title;
	private String summary;
	private String date;

	public CourseSummary() {
		super();
	}

	public CourseSummary(String title, String summary, String date) {
		super();
		T.call(this);
		
		this.title = title;
		this.summary = summary;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
