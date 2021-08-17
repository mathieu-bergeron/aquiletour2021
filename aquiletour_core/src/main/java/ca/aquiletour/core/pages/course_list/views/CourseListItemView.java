package ca.aquiletour.core.pages.course_list.views;

import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.views.ItemView;

public interface CourseListItemView extends ItemView {

	void displayCourseListItem(CourseListItem courseDescription);

}
