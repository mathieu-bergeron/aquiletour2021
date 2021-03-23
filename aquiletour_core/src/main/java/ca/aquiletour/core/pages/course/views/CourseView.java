package ca.aquiletour.core.pages.course.views;

import ca.ntro.core.mvc.NtroView;

public interface CourseView extends NtroView  {

	void insertTask(int index, TaskView taskView);

}
