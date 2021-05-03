package ca.aquiletour.core.pages.dashboard.models;


import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.trace.T;

public abstract class DashboardModel implements NtroModel {

	private DashboardItems dashboardItems = new DashboardItems();
	private StoredString statusMessage = new StoredString();

	public DashboardItems getDashboardItems() {
		return dashboardItems;
	}

	public void setDashboardItems(DashboardItems dashboardItems) {
		this.dashboardItems = dashboardItems;
	}

	public StoredString getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(StoredString statusMessage) {
		this.statusMessage = statusMessage;
	}

	public void addDashboardItem(CourseListItem courseListItem) {
		T.call(this);
		
		DashboardItem item = createDashboardItem();
		dashboardItems.addItem(item); // XXX: must add before setting stored values
		
		updateDashboardItem(courseListItem, item);
	}

	private void updateDashboardItem(CourseListItem courseListItem, DashboardItem item) {
		T.call(this);

		item.updateCourseTitle(courseListItem.getCourseTitle());
		item.setCoursePath(courseListItem.coursePath());
	}
	
	protected abstract DashboardItem createDashboardItem();
}
