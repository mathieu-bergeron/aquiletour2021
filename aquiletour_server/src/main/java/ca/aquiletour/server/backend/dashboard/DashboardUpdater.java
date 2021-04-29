package ca.aquiletour.server.backend.dashboard;


import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class DashboardUpdater {

	public static <DM extends DashboardModel> void addDashboardItemForUser(ModelStoreSync modelStore, 
																		   Class<DM> dashboardModelClass, 
																		   CourseListItem courseListItem, 
																		   User user) {
		T.call(DashboardUpdater.class);
		
		if(!modelStore.ifModelExists(dashboardModelClass,"admin", user.getId())) {
			modelStore.createModel(dashboardModelClass, "admin", user.getId(), new ModelInitializer<DM>() {
				@Override
				public void initialize(DM newModel) {
					T.call(this);
				}
			});
		}
		
		modelStore.updateModel(dashboardModelClass, "admin", user.getId(), new ModelUpdater<DM>() {
			@Override
			public void update(DM dashboardModel) {
				T.call(this);
				
				dashboardModel.addDashboardItem(courseListItem);
			}
		});
	}
}
