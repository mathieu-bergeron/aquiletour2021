package ca.aquiletour.core.pages.activity;

import ca.aquiletour.core.pages.course.CourseController;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class ActivityController extends NtroController<CourseController>{

	@Override
	protected void initialize(NtroContext context) {
		T.call(this);
		
	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);
		
	}

}
