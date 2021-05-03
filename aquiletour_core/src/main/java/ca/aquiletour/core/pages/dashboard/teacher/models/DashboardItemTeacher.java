package ca.aquiletour.core.pages.dashboard.teacher.models;

import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.ntro.core.system.trace.T;

public class DashboardItemTeacher extends DashboardItem<CurrentTaskTeacher> {

	@Override
	protected void updateCurrentTasks(List<Task> currentTasks) {
		T.call(this);
		
	}

}
