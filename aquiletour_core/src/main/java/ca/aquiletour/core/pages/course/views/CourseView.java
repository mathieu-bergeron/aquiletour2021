package ca.aquiletour.core.pages.course.views;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.base.TaskBreadcrumbs;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.ntro.core.mvc.NtroView;

public interface CourseView extends NtroView  {

	void displayBreadcrumbs(CoursePath coursePath, TaskBreadcrumbs breadcrumps);

	void displaySubTasks(boolean shouldDisplay);
	void enableSubTasks(boolean shouldEnable);
	void clearSubtasks();
	void insertSubtask(int index, TaskView taskView);
	void appendSubtask(TaskView taskView);

	void identifyCurrentTask(CoursePath coursePath, Task task);

	void displayPreviousTasks(boolean shouldDisplay);
	void clearPreviousTasks();
	void appendPreviousTask(CoursePath coursePath, Task previousTask);

	void displayNextTasks(boolean shouldDisplay);
	void clearNextTasks();
	void appendNextTask(CoursePath coursePath, Task nextTask);

	void displayTaskTitle(String title, boolean editable);

	void displayTaskDescription(boolean shouldDisplay);
	void updateTaskDescription(String description, boolean editable);

	void displayTaskEndTime(boolean shouldDisplay);
	void updateTaskEndTime(AquiletourDate endTime, boolean editable);

	void clearEntryTasks();
	void clearExitTasks();

	void showUneditableComponents(boolean show);


}
