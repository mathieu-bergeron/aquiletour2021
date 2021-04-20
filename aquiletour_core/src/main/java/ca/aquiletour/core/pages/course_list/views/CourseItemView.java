package ca.aquiletour.core.pages.course_list.views;

import ca.aquiletour.core.pages.course_list.models.CourseItem;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.views.ItemView;

public interface CourseItemView extends ItemView {

	void displayCourseDescription(CourseItem courseDescription);
	void appendTaskDescription(TaskDescription task);
	void appendGroupId(String groupId);

}
