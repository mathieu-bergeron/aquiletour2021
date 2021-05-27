package ca.aquiletour.core.pages.course.views;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.base.TaskBreadcrumbs;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.ntro.core.mvc.NtroView;

public interface CourseView extends NtroView  {

	void displayBreadcrumbs(CoursePath coursePath, TaskBreadcrumbs breadcrumps);

	void clearSubtasks();
	void insertSubtask(int index, TaskView taskView);
	void appendSubtask(TaskView taskView);

	void identifyCurrentTask(CoursePath coursePath, Task task);

	void hidePreviousTasks();
	void showPreviousTasks();
	void clearPreviousTasks();
	void appendPreviousTask(CoursePath coursePath, Task previousTask);

	void hideNextTasks();
	void showNextTasks();
	void clearNextTasks();
	void appendNextTask(CoursePath coursePath, Task nextTask);

	void displayTaskTitle(String title, boolean editable);
	void displayTaskDescription(String description, boolean editable);
	void displayTaskEndTime(AquiletourDate endTime, boolean editable);

	void clearEntryTasks();
	void clearExitTasks();

	void showUneditableComponents(boolean show);

}
