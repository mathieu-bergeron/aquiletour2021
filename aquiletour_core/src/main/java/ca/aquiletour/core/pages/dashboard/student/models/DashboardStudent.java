package ca.aquiletour.core.pages.dashboard.student.models;

import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.aquiletour.core.pages.dashboard.models.Dashboard;
import ca.ntro.core.system.trace.T;

public class DashboardStudent extends Dashboard<CurrentTaskStudent> {

	@Override
	protected DashboardItem<CurrentTaskStudent> createDashboardItem() {
		T.call(this);
		
		return new DashboardItemStudent();
	}

}
