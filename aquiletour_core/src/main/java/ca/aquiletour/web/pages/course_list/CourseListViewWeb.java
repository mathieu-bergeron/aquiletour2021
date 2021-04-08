package ca.aquiletour.web.pages.course_list;

import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseListViewWeb extends NtroViewWeb implements CourseListView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);
		
	}

}
