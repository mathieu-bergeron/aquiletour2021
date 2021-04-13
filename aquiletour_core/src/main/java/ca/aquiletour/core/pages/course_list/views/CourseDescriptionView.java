package ca.aquiletour.core.pages.course_list.views;

import ca.aquiletour.core.pages.course_list.models.CourseDescription;
import ca.ntro.core.mvc.NtroView;

public interface CourseDescriptionView extends NtroView {

	void displayCourseDescription(CourseDescription item);


}
