package ca.aquiletour.web.pages.course_list;

import ca.aquiletour.core.pages.course_list.views.CourseSummaryView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.mvc.NtroViewWeb;

public class CourseSummaryViewWeb extends NtroViewWeb implements CourseSummaryView {

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);
		
	}

}
