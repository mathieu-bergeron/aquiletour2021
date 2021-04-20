package ca.aquiletour.server.backend.dashboard;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.aquiletour.core.pages.dashboards.values.DashboardItem;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class DashboardUpdater {

	public static DashboardItem createQueueSummary(String queueId, String queueTitle) {
		T.call(DashboardUpdater.class);

		DashboardItem courseSummary = new DashboardItem();
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
		T.call(DashboardUpdater.class);
		
		for(String userId : userIds) {
			deleteQueueForUser(modelStore, queueId, userId);
		}
	}
	
	public static void deleteQueueForUser(ModelStoreSync modelStore, 
			                              String queueId, 
			                              String userId) {

		T.call(DashboardUpdater.class);
		
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
									      DashboardItem queue,
			                              List<String> userIds) {

		T.call(DashboardUpdater.class);
		
		for(String userId : userIds) {
			addQueueForUserId(modelStore, queue, userId);
		}
	}

	public static void addQueueForUsers(ModelStoreSync modelStore, 
									    DashboardItem queue,
			                            List<User> users) {

		T.call(DashboardUpdater.class);
		
		for(User user : users) {
			addQueueForUserId(modelStore, queue, user.getId());
		}
	}

	public static void addQueueForUser(ModelStoreSync modelStore, 
									   DashboardItem queue,
			                           User user) {

		T.call(DashboardUpdater.class);
		
		addQueueForUserId(modelStore, queue, user.getId());
	}

	public static void addQueueForUserId(ModelStoreSync modelStore, 
									     DashboardItem queue,
			                             String userId) {

		T.call(DashboardUpdater.class);
		
		if(!modelStore.ifModelExists(DashboardModel.class, "admin", userId)) {
			modelStore.createModel(DashboardModel.class, "admin", userId, new ModelInitializer<DashboardModel>() {
				@Override
				public void initialize(DashboardModel newModel) {
					T.call(this);
				}
			});
		}

		modelStore.updateModel(DashboardModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<DashboardModel>() {

			@Override
			public void update(DashboardModel dashboard) {
				T.call(this);

				dashboard.addCourse(queue);
			}
		});
	}
	
	public static void openQueueForUsers(ModelStoreSync modelStore, 
			                             String queueId, 
			                             List<String> userIds) {
		T.call(DashboardUpdater.class);
		
		for(String userId : userIds) {
			openQueueForUser(modelStore, queueId, userId);
		}
	}

	public static void closeQueueForUsers(ModelStoreSync modelStore, 
			                              String queueId, 
			                              List<String> userIds) {
		T.call(DashboardUpdater.class);
		
		for(String userId : userIds) {
			closeQueueForUser(modelStore, queueId, userId);
		}
	}

	public static void openQueueForUser(ModelStoreSync modelStore, 
			                            String queueId,
			                            String userId) {

		T.call(DashboardUpdater.class);

		setIsQueueOpenForUser(modelStore, queueId, userId, true);
	}

	public static void closeQueueForUser(ModelStoreSync modelStore, 
			                             String queueId,
			                             String userId) {

		T.call(DashboardUpdater.class);
		

		setIsQueueOpenForUser(modelStore, queueId, userId, false);
		setMyAppointmentForUserId(modelStore, queueId, userId, false);
		setNumberOfAppointmentsForUser(modelStore, queueId, userId, 0);
	}

	public static void setNumberOfAppointmentsForUserS(ModelStoreSync modelStore, 
			                                           String queueId,
			                                           List<User> users,
			                                           int numberOfAppointments) {
		T.call(DashboardUpdater.class);
		
		for(User user : users) {
			setNumberOfAppointmentsForUser(modelStore, queueId, user.getId(), numberOfAppointments);
		}
	}

	public static void setNumberOfAppointmentsForUserIds(ModelStoreSync modelStore, 
			                                             String queueId,
			                                             List<String> userIds,
			                                             int numberOfAppointments) {
		T.call(DashboardUpdater.class);
		
		for(String userId : userIds) {
			setNumberOfAppointmentsForUser(modelStore, queueId, userId, numberOfAppointments);
		}
	}

	public static void setNumberOfAppointmentsForUser(ModelStoreSync modelStore, 
			                                          String queueId,
			                                          String userId,
			                                          int numberOfAppointments) {

		T.call(DashboardUpdater.class);

		modelStore.updateModel(DashboardModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<DashboardModel>() {

			@Override
			public void update(DashboardModel dashboard) {
				T.call(this);

				dashboard.updateNbAppointmentOfCourse(queueId, numberOfAppointments);
			}
		});
	}

	public static void setIsQueueOpenForUser(ModelStoreSync modelStore, 
			                                 String queueId,
			                                 String userId,
			                                 boolean isQueueOpen) {

		T.call(DashboardUpdater.class);

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

	public static void setMyAppointmentForUser(ModelStoreSync modelStore, 
			                                   String queueId,
			                                   User user,
			                                   boolean myAppointment) {
		T.call(DashboardUpdater.class);
		
		setMyAppointmentForUserId(modelStore, queueId, user.getId(), myAppointment);
	}

	public static void setMyAppointmentForUserId(ModelStoreSync modelStore, 
			                                     String queueId,
			                                     String userId,
			                                     boolean myAppointment) {

		T.call(DashboardUpdater.class);

		modelStore.updateModel(DashboardModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<DashboardModel>() {

			@Override
			public void update(DashboardModel dashboard) {
				T.call(this);

				dashboard.updateMyAppointment(queueId, myAppointment);
			}
		});
	}

	public static void incrementNumberOfStudents(ModelStoreSync modelStore, 
												 String queueId,
			                                     String userId, 
			                                     int numberOfStudentAdded) {

		T.call(DashboardUpdater.class);

		modelStore.updateModel(DashboardModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<DashboardModel>() {

			@Override
			public void update(DashboardModel dashboard) {
				T.call(this);
				
				DashboardItem summary = dashboard.findCourseById(queueId);
				int currentNumber = summary.getNumberOfAppointments().getValue();
				summary.updateNumberOfStudents(currentNumber + numberOfStudentAdded);
			}
		});
	}
}
