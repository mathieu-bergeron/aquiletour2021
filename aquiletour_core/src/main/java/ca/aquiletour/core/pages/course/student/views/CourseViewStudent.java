package ca.aquiletour.core.pages.course.student.views;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.task_completions.AtomicTaskCompletion;
import ca.aquiletour.core.pages.course.views.CourseView;

public interface CourseViewStudent extends CourseView  {
	
	void displayGitRepoForm(boolean show);
	void displayCompletionCheckbox(boolean show);
	void enableCompletionCheckbox(boolean enable);
	void checkCompletion(boolean check);

	void appendEntryTask(String groupId, AtomicTask task, AtomicTaskCompletion completion);
	void appendExitTask(String groupId, AtomicTask task, AtomicTaskCompletion completion);

}
