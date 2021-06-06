package ca.aquiletour.core.pages.course.teacher.views;

import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.views.widgets.CategoryDropdown;

public interface CourseViewTeacher extends CourseView, CategoryDropdown {

	void insertIntoGroupDropdown(int index, String groupId, String href, String text);
	void appendToGroupDropdown(String groupId, String href, String text);
	void selectGroup(String groupId);

	void identifyCurrentTask(CoursePath coursePath, Task task);

	void displayCourseStructureView(boolean shouldDisplay);

	void appendEntryTask(AtomicTask task);
	void appendExitTask(AtomicTask task);
	
	void appendCompletion(String studentId);
	void clearStudentStatuses();
}
