package ca.aquiletour.core.pages.group_list.views;

import ca.aquiletour.core.views.ListView;

public interface GroupListView extends ListView<GroupView> {

	void selectCourse(String courseId);
	void identifyCurrentCourse(String courseId);
	void insertIntoCourseDropdown(int index, String courseId, String href);
	void appendToCourseDropdown(String courseId, String href);
	void clearCourseDropdown();


}
