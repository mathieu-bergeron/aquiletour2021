package ca.aquiletour.core.pages.course.student.views;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.status.StatusBlocked;
import ca.aquiletour.core.models.dates.AquiletourDate;
import ca.aquiletour.core.pages.course.views.CourseView;

public interface CourseViewStudent extends CourseView  {
	
	void displayGitRepoForm(boolean show);


	void displayDoneTasks(boolean shouldDisplay);
	void displayTodoTasks(boolean shouldDisplay);

	void appendEntryTask(String groupId, AtomicTask task, AtomicTaskCompletion completion);

	void removeEntryTask(AtomicTask atomicTask);

	void updateAtomicTaskCompletion(String groupId, AtomicTask atomicTask , AtomicTaskCompletion completion);


	void appendExitTask(String groupId, AtomicTask task, AtomicTaskCompletion completion);

	void displayToCompleteFirst(boolean shouldDisplay);
	void updateToCompleteFirst(StatusBlocked status);

	void updateTaskDoneTime(AquiletourDate doneTime);
	void updateTaskLateTime(AquiletourDate lateTime);

	void displayGitProgression(String groupId);

}
