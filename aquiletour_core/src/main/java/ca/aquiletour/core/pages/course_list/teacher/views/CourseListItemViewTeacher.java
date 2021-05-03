package ca.aquiletour.core.pages.course_list.teacher.views;

import ca.aquiletour.core.pages.course_list.models.TaskDescription;
import ca.aquiletour.core.pages.course_list.views.CourseListItemView;

public interface CourseListItemViewTeacher extends CourseListItemView {

	void appendTaskDescription(TaskDescription task);
	void appendGroupId(String groupId);
	void displayTasksSummary(String tasksSummaryText);
	void displayGroupsSummary(String groupsSummaryText);
	void displayQueueLink(boolean queueOpen, String text, String href);
}
