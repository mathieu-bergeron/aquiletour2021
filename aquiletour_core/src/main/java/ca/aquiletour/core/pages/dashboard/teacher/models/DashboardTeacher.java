package ca.aquiletour.core.pages.dashboard.teacher.models;

import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.aquiletour.core.pages.dashboard.models.Dashboard;
import ca.ntro.core.system.trace.T;

public class DashboardTeacher extends Dashboard<CurrentTaskTeacher> {

	@Override
	protected DashboardItem<CurrentTaskTeacher> createDashboardItem() {
		T.call(this);
		
		return new DashboardItemTeacher();
	}

}
