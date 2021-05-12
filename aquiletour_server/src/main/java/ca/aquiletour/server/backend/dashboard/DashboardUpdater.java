package ca.aquiletour.server.backend.dashboard;


import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.aquiletour.core.pages.dashboard.models.Dashboard;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardStudent;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class DashboardUpdater {

	public static <DM extends Dashboard<?>> void addDashboardItemForUser(ModelStoreSync modelStore, 
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

	public static <DM extends Dashboard<?>> void createDashboardForUser(ModelStoreSync modelStore, 
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

	public static <DM extends Dashboard<CT>, CT extends CurrentTask> void updateCurrentTasksForUserId(ModelStoreSync modelStore, 
																	         Class<DM> dashboardModelClass,
																	         Class<CT> currentTaskClass,
																	         CoursePath coursePath,
																	         List<CT> currentTasks,
			 														         String userId) {
		T.call(DashboardUpdater.class);

		modelStore.updateModel(dashboardModelClass, "admin", userId, new ModelUpdater<DM>() {
			@Override
			public void update(DM dashboardModel) {
				T.call(this);
				
				dashboardModel.updateCurrentTasks(coursePath, currentTasks);
			}
		});

	}

	public static <DM extends Dashboard<CT>, CT extends CurrentTask> void updateCurrentTasksForUser(ModelStoreSync modelStore, 
																	         Class<DM> dashboardModelClass,
																	         Class<CT> currentTaskClass,
																	         CoursePath coursePath,
																	         List<CT> currentTasks,
			 														         User user) {
		T.call(DashboardUpdater.class);
		
		updateCurrentTasksForUserId(modelStore, dashboardModelClass, currentTaskClass, coursePath, currentTasks, user.getId());
	}
}
