package ca.aquiletour.core.pages.course_list.views;

import ca.ntro.core.mvc.NtroView;

public interface CourseListView extends NtroView {

	void insertIntoSemesterDropdown(int index, String semesterId);

}
