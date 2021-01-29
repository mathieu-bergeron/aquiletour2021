package ca.aquiletour.web.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.mvc.NtroViewWeb;

public class DashboardViewWeb extends NtroViewWeb implements DashboardView {

	@Override
	public void appendCourse(String title) {
		T.call(this);
		
	}

}
