package ca.aquiletour.core.pages.dashboard.teacher.models;

import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.ntro.core.system.trace.T;

public class DashboardModelTeacher extends DashboardModel<CurrentTaskTeacher> {

	@Override
	protected DashboardItem<CurrentTaskTeacher> createDashboardItem() {
		T.call(this);
		
		return new DashboardItemTeacher();
	}

}
