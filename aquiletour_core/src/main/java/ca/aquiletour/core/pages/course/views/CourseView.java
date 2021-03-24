package ca.aquiletour.core.pages.course.views;

import ca.aquiletour.core.pages.course.models.TaskBreadcrumbs;
import ca.ntro.core.mvc.NtroView;

public interface CourseView extends NtroView  {

	void displayBreadcrumbs(String courseId, TaskBreadcrumbs breadcrumps);
	void insertTask(int index, TaskView taskView);
	void identifyCurrentTask(String id);

}
