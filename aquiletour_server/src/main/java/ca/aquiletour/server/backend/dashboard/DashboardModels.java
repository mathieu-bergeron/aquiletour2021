package ca.aquiletour.server.backend.dashboard;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class DashboardModels {

	public static CourseSummary createQueueSummary(String queueId, String queueTitle) {
		T.call(DashboardModels.class);

		CourseSummary courseSummary = new CourseSummary();
		courseSummary.setTitle(queueTitle); 
		courseSummary.setCourseId(queueId);
		courseSummary.updateQueueOpen(false);
		courseSummary.updateMyAppointment(false);
		courseSummary.updateNumberOfStudents(0);
		
		return courseSummary;
	}
	
	public static void deleteQueueForUsers(ModelStoreSync modelStore, 
			                                  String queueId, 
			                                  List<String> userIds) {
		T.call(DashboardModels.class);
		
		for(String userId : userIds) {
			deleteQueueForUser(modelStore, queueId, userId);
		}
	}
	
	public static void deleteQueueForUser(ModelStoreSync modelStore, 
			                              String queueId, 
			                              String userId) {

		T.call(DashboardModels.class);
		
		modelStore.updateModel(DashboardModel.class, 
				               "admin", 
				               userId, 
				               new ModelUpdater<DashboardModel>() {
			@Override
			public void update(DashboardModel dashboardModel) {
				T.call(this);

				dashboardModel.deleteCourseById(queueId);
			}
		});
	}

	public static void addQueueForUserIds(ModelStoreSync modelStore, 
									      CourseSummary queue,
			                              List<String> userIds) {

		T.call(DashboardModels.class);
		
		for(String userId : userIds) {
			addQueueForUserId(modelStore, queue, userId);
		}
	}

	public static void addQueueForUsers(ModelStoreSync modelStore, 
									    CourseSummary queue,
			                            List<User> users) {

		T.call(DashboardModels.class);
		
		for(User user : users) {
			addQueueForUserId(modelStore, queue, user.getId());
		}
	}

	public static void addQueueForUser(ModelStoreSync modelStore, 
									   CourseSummary queue,
			                           User user) {

		T.call(DashboardModels.class);
		
		addQueueForUserId(modelStore, queue, user.getId());
	}

	public static void addQueueForUserId(ModelStoreSync modelStore, 
									     CourseSummary queue,
			                             String userId) {

		T.call(DashboardModels.class);

		modelStore.updateModel(DashboardModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<DashboardModel>() {

			@Override
			public void update(DashboardModel teacherDashboard) {
				T.call(this);

				teacherDashboard.addCourse(queue);
			}
		});
	}
	
	public static void openQueueForUsers(ModelStoreSync modelStore, 
			                             String queueId, 
			                             List<String> userIds) {
		T.call(DashboardModels.class);
		
		for(String userId : userIds) {
			openQueueForUser(modelStore, queueId, userId);
		}
	}

	public static void closeQueueForUsers(ModelStoreSync modelStore, 
			                              String queueId, 
			                              List<String> userIds) {
		T.call(DashboardModels.class);
		
		for(String userId : userIds) {
			closeQueueForUser(modelStore, queueId, userId);
		}
	}

	public static void openQueueForUser(ModelStoreSync modelStore, 
			                            String queueId,
			                            String userId) {

		T.call(DashboardModels.class);

		setIsQueueOpenForUser(modelStore, queueId, userId, true);
	}

	public static void closeQueueForUser(ModelStoreSync modelStore, 
			                             String queueId,
			                             String userId) {

		T.call(DashboardModels.class);

		setIsQueueOpenForUser(modelStore, queueId, userId, false);
	}

	public static void setIsQueueOpenForUser(ModelStoreSync modelStore, 
			                                 String queueId,
			                                 String userId,
			                                 boolean isQueueOpen) {

		T.call(DashboardModels.class);

		modelStore.updateModel(DashboardModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<DashboardModel>() {

			@Override
			public void update(DashboardModel teacherDashboard) {
				T.call(this);

				teacherDashboard.setTeacherAvailability(queueId, isQueueOpen);
			}
		});
	}

	public static void incrementNumberOfStudents(ModelStoreSync modelStore, 
												 String queueId,
			                                     String userId, 
			                                     int numberOfStudentAdded) {

		T.call(DashboardModels.class);

		modelStore.updateModel(DashboardModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<DashboardModel>() {

			@Override
			public void update(DashboardModel dashboard) {
				T.call(this);
				
				CourseSummary summary = dashboard.findCourseById(queueId);
				int currentNumber = summary.getNumberOfAppointments().getValue();
				summary.updateNumberOfStudents(currentNumber + numberOfStudentAdded);
			}
		});
	}
}
