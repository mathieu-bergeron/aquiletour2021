package ca.aquiletour.core.pages.course.views;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.base.TaskBreadcrumbs;
import ca.ntro.core.mvc.NtroView;

public interface CourseView extends NtroView  {

	void displayBreadcrumbs(CoursePath coursePath, TaskBreadcrumbs breadcrumps);

	void clearSubtasks();
	void insertSubtask(int index, TaskView taskView);
	void appendSubtask(TaskView taskView);

	void identifyCurrentTask(CoursePath coursePath, Task task);

	void clearPreviousTasks();
	void appendPreviousTask(CoursePath coursePath, Task previousTask);

	void clearNextTasks();
	void appendNextTask(CoursePath coursePath, Task nextTask);
}
