package ca.aquiletour.server.backend.dashboard;


import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardModelStudent;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class DashboardUpdater {

	public static <DM extends DashboardModel<?>> void addDashboardItemForUser(ModelStoreSync modelStore, 
																		   Class<DM> dashboardModelClass, 
																		   CourseListItem courseListItem, 
																		   User user) {
		T.call(DashboardUpdater.class);

		modelStore.updateModel(dashboardModelClass, "admin", user.getId(), new ModelUpdater<DM>() {
			@Override
			public void update(DM dashboardModel) {
				T.call(this);
				
				dashboardModel.addDashboardItem(courseListItem);
			}
		});
	}

	public static <DM extends DashboardModel<?>> void createDashboardForUser(ModelStoreSync modelStore, 
																	      Class<DM> dashboardModelClass,
			 														      User user) {
		T.call(DashboardUpdater.class);

		modelStore.createModel(dashboardModelClass, "admin", user.getId(), new ModelInitializer<DM>() {
			@Override
			public void initialize(DM newModel) {
				T.call(this);
			}
		});
	}

	public static <DM extends DashboardModel<?>> void updateCurrentTasksForUser(ModelStoreSync modelStore, 
																	         Class<DM> dashboardModelClass,
																	         CoursePath coursePath,
																	         List<Task> currentTasks,
			 														         User user) {
		T.call(DashboardUpdater.class);

		modelStore.updateModel(dashboardModelClass, "admin", user.getId(), new ModelUpdater<DM>() {
			@Override
			public void update(DM dashboardModel) {
				T.call(this);
				
				dashboardModel.updateCurrentTasks(coursePath, currentTasks);
			}
		});
	}
}
