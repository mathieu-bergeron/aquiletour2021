package ca.aquiletour.core.pages.dashboard.models;

import java.util.List;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public abstract class DashboardItem<CT extends CurrentTask> implements NtroModelValue {
	
	private CoursePath coursePath = new CoursePath();
	private StoredString courseTitle = new StoredString();
	private CurrentTasks<CT> currentTasks = new CurrentTasks<CT>();

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

	public CurrentTasks<CT> getCurrentTasks() {
		return currentTasks;
	}

	public void setCurrentTasks(CurrentTasks<CT> currentTasks) {
		this.currentTasks = currentTasks;
	}

	protected void updateCurrentTasks(List<CT> currentTasks) {
		T.call(this);

		getCurrentTasks().clearItems();
		for(CT currentTask : currentTasks) {
			getCurrentTasks().addItem(currentTask);
		}
	}

	public boolean hasCoursePath(CoursePath coursePath) {
		T.call(this);
		
		return getCoursePath().equals(coursePath);
	}
	
}
