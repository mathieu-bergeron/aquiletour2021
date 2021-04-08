package ca.aquiletour.core.pages.course.views;

import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.base.TaskBreadcrumbs;
import ca.ntro.core.mvc.NtroView;

public interface CourseView extends NtroView  {

	void displayBreadcrumbs(String courseId, TaskBreadcrumbs breadcrumps);

	void insertTask(int index, TaskView taskView);
	void appendTask(TaskView taskView);

	void identifyCurrentTask(String courseId, Task task);

	void clearTasks();
}
