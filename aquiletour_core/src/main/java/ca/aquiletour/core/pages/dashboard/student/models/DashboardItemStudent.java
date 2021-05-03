package ca.aquiletour.core.pages.dashboard.student.models;

import java.util.List;

import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.ntro.core.system.trace.T;

public class DashboardItemStudent extends DashboardItem<CurrentTaskStudent> {

	@Override
	protected void updateCurrentTasks(List<Task> currentTasks) {
		T.call(this);
		
		getCurrentTasks().clearItems();
		for(Task task : currentTasks) {

			CurrentTaskStudent currentTask = new CurrentTaskStudent();
			getCurrentTasks().addItem(currentTask);

			currentTask.setTaskPath(task.getPath());
			currentTask.updateEndTime(task.getEndTime().getValue());
		}
	}

}
