package ca.aquiletour.core.pages.dashboard.teacher.models;


import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.ntro.core.system.trace.T;

public class DashboardItemTeacher extends DashboardItem<CurrentTaskTeacher> {
	
	@Override
	protected void updateCurrentTasks(List<CurrentTaskTeacher> currentTasks) {
		T.call(this);

		getCurrentTasks().clearItems();
		
		for(int i = 0; 
				i < Constants.NUMBER_OF_CURRENT_TASKS_TEACHER
				&&
				i < currentTasks.size();
				i++) {

			getCurrentTasks().addItem(currentTasks.get(i));
		}
	}
}
