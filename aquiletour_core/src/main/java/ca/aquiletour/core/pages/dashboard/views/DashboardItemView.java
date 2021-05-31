package ca.aquiletour.core.pages.dashboard.views;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.ntro.core.mvc.NtroView;

public interface DashboardItemView<CT extends CurrentTask> extends NtroView {
	
	public void identifyCourse(CoursePath coursePath);
	public void updateCourseTitle(String courseTitle);

	public void insertTask(int index, CT currentTask);
	public void updateTaskTitle(int index, String value);
}
