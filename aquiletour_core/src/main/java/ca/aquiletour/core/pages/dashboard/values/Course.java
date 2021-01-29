package ca.aquiletour.core.pages.dashboard.values;

import ca.ntro.core.models.properties.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class Course extends NtroModelValue {
	
	private String title;

	public Course(String title) {
		T.call(this);
		
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
