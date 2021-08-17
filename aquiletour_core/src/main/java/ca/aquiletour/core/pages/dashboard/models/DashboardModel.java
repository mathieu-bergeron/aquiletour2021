package ca.aquiletour.core.pages.dashboard.models;

import java.util.List;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public abstract class DashboardModel<CT extends CurrentTask> implements NtroModel {

	private DashboardItems<CT> dashboardItems = new DashboardItems<>();
	private StoredString statusMessage = new StoredString();

	public DashboardItems<CT> getDashboardItems() {
		return dashboardItems;
	}

	public void setDashboardItems(DashboardItems<CT> dashboardItems) {
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
		
		DashboardItem<CT> item = createDashboardItem();
		updateDashboardItem(courseListItem, item);

		dashboardItems.addItem(item);
	}

	private void updateDashboardItem(CourseListItem courseListItem, DashboardItem<CT> item) {
		T.call(this);

		item.updateCourseTitle(courseListItem.getCourseTitle());
		item.setCoursePath(courseListItem.coursePath());
	}
	
	protected abstract DashboardItem<CT> createDashboardItem();
	
	@SuppressWarnings("unchecked")
	private DashboardItem<CT> dashboardItemByCoursePath(CoursePath coursePath){
		
		return getDashboardItems().reduceTo(DashboardItem.class, null, (index, candidate, accumulator) -> {
			if(accumulator != null) {
				throw new Break();
			}
			
			if(candidate.hasCoursePath(coursePath)) {
				accumulator = candidate;
			}
			
			return accumulator;
			
		});
	}

	public void updateCurrentTasks(CoursePath coursePath, List<CT> currentTasks) {
		T.call(this);

		DashboardItem<CT> item = dashboardItemByCoursePath(coursePath);
		
		if(item != null) {
			
			item.updateCurrentTasks(currentTasks);
			
		}else {
			
			Log.warning("DashboardItem not found for coursePath: " + coursePath.toString());
		}
	}
}
