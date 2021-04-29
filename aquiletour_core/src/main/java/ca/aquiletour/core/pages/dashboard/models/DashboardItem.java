package ca.aquiletour.core.pages.dashboard.models;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public class DashboardItem implements NtroModelValue {
	
	private StoredString courseTitle = new StoredString();
	private CoursePath coursePath = new CoursePath();

	public DashboardItem() {
		super();
		T.call(this);
	}

	public StoredString getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(StoredString courseTitle) {
		this.courseTitle = courseTitle;
	}
	
	public CoursePath getCoursePath() {
		return coursePath;
	}

	public void setCoursePath(CoursePath coursePath) {
		this.coursePath = coursePath;
	}
	
	public void updateCourseTitle(String courseTitle) {
		T.call(this);
		
		this.courseTitle.set(courseTitle);
	}
	
	
	
}
