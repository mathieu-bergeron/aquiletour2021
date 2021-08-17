package ca.aquiletour.core.pages.dashboard.student.models;


import ca.aquiletour.core.pages.dashboard.models.DashboardItem;
import ca.ntro.core.models.StoredBoolean;

public class DashboardItemStudent extends DashboardItem<CurrentTaskStudent> {
	
	private StoredBoolean isQueueOpen = new StoredBoolean();

	public StoredBoolean getIsQueueOpen() {
		return isQueueOpen;
	}

	public void setIsQueueOpen(StoredBoolean isQueueOpen) {
		this.isQueueOpen = isQueueOpen;
	}
}
