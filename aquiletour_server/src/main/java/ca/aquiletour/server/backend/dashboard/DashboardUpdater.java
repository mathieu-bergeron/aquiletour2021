package ca.aquiletour.server.backend.dashboard;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class DashboardUpdater {

	public static void addDashboardItemForUser(ModelStoreSync modelStore, 
											   CourseListItem courseListItem, 
											   User user) {
		T.call(DashboardUpdater.class);
		
		if(!modelStore.ifModelExists(DashboardModel.class,"admin", user.getId())) {
			modelStore.createModel(DashboardModel.class, "admin", user.getId(), new ModelInitializer<DashboardModel>() {
				@Override
				public void initialize(DashboardModel newModel) {
					T.call(this);
				}
			});
		}
		
		modelStore.updateModel(DashboardModel.class, "admin", user.getId(), new ModelUpdater<DashboardModel>() {
			@Override
			public void update(DashboardModel dashboardModel) {
				T.call(this);
				
				dashboardModel.addDashboardItem(courseListItem);
			}
		});
	}
}
