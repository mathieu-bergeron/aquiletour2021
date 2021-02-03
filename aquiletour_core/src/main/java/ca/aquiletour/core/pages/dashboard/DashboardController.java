package ca.aquiletour.core.pages.dashboard;

import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class DashboardController extends NtroController {

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
	}

	public void addCourse(CourseSummary course) {
		T.call(this);
		
	}

	public void showDashboard() {
		T.call(this);
		
	}

}
