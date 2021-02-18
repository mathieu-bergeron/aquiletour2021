package ca.aquiletour.core.pages.course;

import ca.aquiletour.core.pages.dashboards.DashboardController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class CourseController extends NtroController<DashboardController> {

	@Override
	protected void onCreate() {
		T.call(this);

	}

	@Override
	protected void onChangeContext(NtroContext previousContext) {
		T.call(this);
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}


}
