package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public abstract class DashboardController extends NtroController {

	public void addCourse(CourseSummary course) {
		T.call(this);
		
	}

	public void showDashboard() {
		T.call(this);
		
	}
}
