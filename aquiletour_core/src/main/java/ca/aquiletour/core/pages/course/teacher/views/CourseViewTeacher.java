package ca.aquiletour.core.pages.course.teacher.views;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.views.widgets.SemesterDropdown;

public interface CourseViewTeacher extends CourseView, SemesterDropdown {

	void insertIntoGroupDropdown(int index, String groupId, String href, String text);
	void appendToGroupDropdown(String groupId, String href, String text);
	void selectGroup(String groupId);

	void identifyCurrentTask(CoursePath coursePath, Task task);

	void showEditableComponents(boolean show);
	
	void appendCompletion(String studentId);
}
