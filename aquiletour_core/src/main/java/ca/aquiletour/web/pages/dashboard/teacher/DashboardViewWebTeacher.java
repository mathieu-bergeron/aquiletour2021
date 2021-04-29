package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.pages.dashboard.teacher.views.TeacherDashboardView;
import ca.aquiletour.web.pages.dashboard.DashboardViewWeb;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;

public class DashboardViewWebTeacher extends DashboardViewWeb implements TeacherDashboardView {


	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		super.initializeViewWeb(context);
		T.call(this);
	}
}
