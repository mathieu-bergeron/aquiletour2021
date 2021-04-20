package ca.aquiletour.core.pages.course_list.views;

import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.views.ItemView;

public interface CourseDescriptionView extends ItemView {

	void displayCourseDescription(CourseDescription courseDescription);
	void appendTaskDescription(TaskDescription task);
	void appendGroupId(String groupId);

}
