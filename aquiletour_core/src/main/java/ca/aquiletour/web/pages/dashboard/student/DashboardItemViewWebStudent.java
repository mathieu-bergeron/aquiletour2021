package ca.aquiletour.web.pages.dashboard.student;

import ca.aquiletour.core.pages.dashboards.student.DashboardCourseViewStudent;
import ca.aquiletour.web.pages.dashboard.DashboardItemViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;

public class DashboardItemViewWebStudent extends DashboardItemViewWeb implements DashboardCourseViewStudent {

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);
    }
}
