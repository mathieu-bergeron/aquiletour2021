package ca.aquiletour.core.pages.course;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class CourseController extends NtroController<DashboardController> {

	@Override
	protected void initialize() {
		T.call(this);

	}

}
