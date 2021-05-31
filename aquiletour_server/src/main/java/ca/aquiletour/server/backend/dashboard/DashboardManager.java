package ca.aquiletour.server.backend.dashboard;


import java.util.ArrayList;
import java.util.List;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.models.courses.CoursePathStudent;
import ca.aquiletour.core.models.courses.base.CourseModel;
import ca.aquiletour.core.models.courses.base.Task;
import ca.aquiletour.core.models.courses.student.CourseModelStudent;
import ca.aquiletour.core.models.courses.teacher.CourseModelTeacher;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course_list.models.CourseListItem;
import ca.aquiletour.core.pages.dashboard.models.CurrentTask;
import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.aquiletour.core.pages.dashboard.student.models.CurrentTaskStudent;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardModelStudent;
import ca.aquiletour.core.pages.dashboard.teacher.models.CurrentTaskTeacher;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardModelTeacher;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelReader;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class DashboardManager {

	public static <DM extends DashboardModel<?>> void addDashboardItemForUser(ModelStoreSync modelStore, 
																		   Class<DM> dashboardModelClass, 
																		   CourseListItem courseListItem, 
																		   User user) throws BackendError {
		T.call(DashboardManager.class);

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
		T.call(DashboardManager.class);

		modelStore.createModel(dashboardModelClass, "admin", user.getId(), new ModelInitializer<DM>() {
			@Override
			public void initialize(DM newModel) {
				T.call(this);
			}
		});
	}

	public static <DM extends DashboardModel<CT>, CT extends CurrentTask> void updateCurrentTasksForUserId(ModelStoreSync modelStore, 
																	         Class<DM> dashboardModelClass,
																	         Class<CT> currentTaskClass,
																	         CoursePath coursePath,
																	         List<CT> currentTasks,
			 														         String userId) throws BackendError {
		T.call(DashboardManager.class);

		modelStore.updateModel(dashboardModelClass, "admin", userId, new ModelUpdater<DM>() {
			@Override
			public void update(DM dashboardModel) {
				T.call(this);
				
				dashboardModel.updateCurrentTasks(coursePath, currentTasks);
			}
		});

	}

	public static <DM extends DashboardModel<CT>, CT extends CurrentTask> void updateCurrentTasksForUser(ModelStoreSync modelStore, 
																	         Class<DM> dashboardModelClass,
																	         Class<CT> currentTaskClass,
																	         CoursePath coursePath,
																	         List<CT> currentTasks,
			 														         User user) throws BackendError {
		T.call(DashboardManager.class);
		
		updateCurrentTasksForUserId(modelStore, dashboardModelClass, currentTaskClass, coursePath, currentTasks, user.getId());
	}

	public static <CT extends CurrentTask, 
	               CM extends CourseModel<CT>,
	               DM extends DashboardModel<CT>> 

	       void updateCurrentTasks(ModelStoreSync modelStore, 
	    		                   Class<? extends DashboardModel<CT>> dashboardModelClass,
	    		                   Class<CT> currentTaskClass, 
	    		                   Class<CM> courseModelClass, 
	    		                   CoursePath coursePath,
	    		                   String userId) throws BackendError {

		T.call(DashboardManager.class);
		
		List<CT> currentTasks = CourseManager.getCurrentTasks(modelStore, courseModelClass, coursePath);

		updateCurrentTasks(modelStore, dashboardModelClass, coursePath, userId, currentTasks);
	}

	private static 
	        <DM extends DashboardModel<CT>, CT extends CurrentTask> 
       	    void 
       	    updateCurrentTasks(ModelStoreSync modelStore, 
       	    		           Class<? extends DashboardModel<CT>> dashboardModelClass, 
       	    		           CoursePath coursePath, 
       	    		           String userId, 
       	    		           List<CT> currentTasks) throws BackendError {

		T.call(DashboardManager.class);

		modelStore.updateModel(dashboardModelClass, "admin", userId, new ModelUpdater<DM>() {
			@Override
			public void update(DM dashboardModel) throws BackendError {
				T.call(this);
				
				CoursePath teacherCoursePath = coursePath;
				
				if(coursePath instanceof CoursePathStudent) {
					teacherCoursePath = ((CoursePathStudent) coursePath).toCoursePath();
				}

				dashboardModel.updateCurrentTasks(teacherCoursePath, currentTasks);
			}
		});
	}

	public static void updateCurrentTasks(ModelStoreSync modelStore, CoursePath coursePath) throws BackendError {
		T.call(DashboardManager.class);

		DashboardManager.updateCurrentTasks(modelStore, 
										    DashboardModelTeacher.class,
										    CurrentTaskTeacher.class,
											CourseModelTeacher.class,
											coursePath,
											coursePath.teacherId());
		
		List<String> studentIds = CourseManager.getStudentIds(modelStore, coursePath);

		for(String studentId : studentIds) {
			DashboardManager.updateCurrentTasks(modelStore, 
											    DashboardModelStudent.class,
											    CurrentTaskStudent.class, 
											    CourseModelStudent.class, 
											    CoursePathStudent.fromCoursePath(coursePath, studentId),
											    studentId);
		}
	}
}
