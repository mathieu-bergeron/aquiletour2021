package ca.aquiletour.core.pages.course_list.views;

import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.views.ItemView;

public interface CourseItemView extends ItemView {

	void displayCourseDescription(CourseListItem courseDescription);
	void appendTaskDescription(TaskDescription task);
	void appendGroupId(String groupId);
	void displayTasksSummary(String tasksSummaryText);
	void displayGroupsSummary(String groupsSummaryText);
	void displayQueueLink(boolean queueOpen, String text, String href);

}
