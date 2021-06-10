package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.models.logs.LogModelQueue;
import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.AppointmentAddedListener;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.server.backend.open_queues_list.QueuesUpdater;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.models.NtroDate;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class QueueManager {

	public static void createQueueForUser(ModelStoreSync modelStore,
								   		  User user) throws BackendError {

		T.call(QueueManager.class);
		
		createQueue(modelStore, user.getId());
		createQueueLog(modelStore, user.getId());
	}

	public static void createQueueLog(ModelStoreSync modelStore,
			                          String queueId) throws BackendError {
		T.call(QueueManager.class);
		
		modelStore.createModel(LogModelQueue.class, "admin", queueId, logModel -> {
			
		});
	}

	public static void createQueue(ModelStoreSync modelStore,
			                       String queueId) throws BackendError {

		T.call(QueueManager.class);

		modelStore.createModel(QueueModel.class, 
							   "admin",
				               queueId, 
				               new ModelInitializer<QueueModel>() {
			@Override
			public void initialize(QueueModel newQueue) {
				T.call(this);

				newQueue.setQueueId(queueId);
			}
		});
	}

	public static void deleteQueue(ModelStoreSync modelStore,
			                       String queueId) throws BackendError {

		T.call(QueueManager.class);

		QueuesUpdater.deleteQueue(modelStore, queueId);
	}


	public static void openQueueForCourseId(ModelStoreSync modelStore,
			                     			String queueId,
			                     			String courseId) throws BackendError {

		T.call(QueueManager.class);
		
		modelStore.updateModel(QueueModel.class, "admin", queueId, queueModel -> {
			T.call(QueueManager.class);
			
			queueModel.updateIsQueueOpenForCourseId(courseId, true);
		});
		
		//QueuesUpdater.openQueue(modelStore, queueId);
	}

	public static void closeQueue(ModelStoreSync modelStore,
			                      String queueId) throws BackendError {

		T.call(QueueManager.class);

		QueuesUpdater.closeQueue(modelStore, queueId);

		modelStore.updateModel(QueueModel.class, "amdin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				queue.clearQueue();
			}
		});
	}

	public static void addAppointmentForUser(ModelStoreSync modelStore,
			                                 String queueId,
			                                 CoursePath coursePath,
			                                 TaskPath taskPath,
			                                 String taskTitle,
			                                 User user) throws BackendError {

		T.call(QueueManager.class);
		
		NtroDate timestamp = Ntro.calendar().now();

		Appointment appointment = createAppointment(timestamp, user, coursePath, taskPath, taskTitle);
		
		modelStore.updateModel(QueueModel.class, "admin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);

				queue.addAppointment(appointment, new AppointmentAddedListener() {
					@Override
					public void onAppointementAdded(Appointment appointment) {
						T.call(user);
						
						logNewAppointment(modelStore, queueId, user, timestamp, appointment);
					}
				});
			}
		});
	}

	private static void logNewAppointment(ModelStoreSync modelStore, 
			                              String queueId, 
			                              User user, 
			                              NtroDate timestamp, 
			                              Appointment appointment) {
		T.call(QueueManager.class);
		
		try {
			modelStore.updateModel(LogModelQueue.class, "admin", queueId, queueLog -> {

				queueLog.addAppointement(timestamp, user, appointment);

			});

		} catch (BackendError e) {

			Log.warning("[logNewAppointement] error: " + e.getMessage());
		}

		/*
		Ntro.threadService().executeLater(new NtroTaskSync() {
			@Override
			protected void runTask() {
				try {
					modelStore.updateModel(LogModelQueue.class, "admin", queueId, queueLog -> {

						queueLog.addAppointement(timestamp, user, appointment);

					});

				} catch (BackendError e) {

					Log.warning("[logNewAppointement] error: " + e.getMessage());
				}
			}

			@Override
			protected void onFailure(Exception e) {
			}
		});*/
	}

	private static Appointment createAppointment(NtroDate timestamp, User user, CoursePath coursePath, TaskPath taskPath, String taskTitle) {
		T.call(QueueManager.class);

		Appointment appointment = new Appointment();

		appointment.updateTime(timestamp);
		appointment.setStudentId(user.getId());
		appointment.setStudentName(user.getFirstname());
		appointment.setStudentSurname(user.getLastname());
		if(coursePath != null) {
			appointment.updateCoursePath(coursePath);
			appointment.updateCourseTitle(coursePath.courseId());
		}
		if(taskPath != null) {
			appointment.updateTaskPath(taskPath);
		}
		if(taskTitle != null) {
			appointment.updateTaskTitle(taskTitle);
		}
		
		return appointment;
	}

	public static void addAppointmentUpdates(ModelStoreSync modelStore, String queueId) throws BackendError {
		T.call(QueueManager.class);
		
		numberOfAppointmentUpdates(modelStore, queueId);
	}

	private static void numberOfAppointmentUpdates(ModelStoreSync modelStore, String queueId) throws BackendError {
		T.call(QueueManager.class);

		modelStore.updateModel(QueueModel.class, 
				               "admin", 
				               queueId, 
				               queue -> {

			int nbAppointment = queue.getAppointments().size();
	   });
	}

	public static Appointment getAppointmentById(ModelStoreSync modelStore, String queueId, String appointmentId) {
		T.call(QueueManager.class);
		
		return modelStore.extractFromModel(QueueModel.class, "admin", queueId, Appointment.class, queueModel -> {
			return queueModel.appointmentById(appointmentId);
		});
	}

	public static void deleteAppointment(ModelStoreSync modelStore, String queueId, String appointmentId) throws BackendError {
		T.call(QueueManager.class);
		
		modelStore.updateModel(QueueModel.class, "admin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);

				queue.deleteAppointment(appointmentId);
			}
		});
	}

	public static void deleteAppointmentUpdates(ModelStoreSync modelStore, String queueId, Appointment deletedAppointment) throws BackendError {
		T.call(QueueManager.class);
		
		String appointmentOwnerId = deletedAppointment.getStudentId();
		
		numberOfAppointmentUpdates(modelStore, queueId);
	}

	public static void moveAppointment(ModelStoreSync modelStore, 
			                           String queueId, 
			                           String appointmentId, 
			                           String destinationId,
			                           String beforeOrAfter) throws BackendError {

		T.call(QueueManager.class);
		
		modelStore.updateModel(QueueModel.class, "admin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);

				queue.moveAppointment(appointmentId, destinationId, beforeOrAfter);
			}
		});
	}

	public static void modifyAppointmentTimes(ModelStoreSync modelStore, int timeIncrementSeconds, User user) throws BackendError {
		T.call(QueueManager.class);
		
		modelStore.updateModel(QueueModel.class, "admin", user.getId(), new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				
				queue.incrementAppointmentTimesSeconds(timeIncrementSeconds);
			}
		});
	}

	public static void modifyAppointmentDurations(ModelStoreSync modelStore, int durationIncrementSeconds, User user) throws BackendError {
		T.call(QueueManager.class);
		
		modelStore.updateModel(QueueModel.class, "admin", user.getId(), new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				
				queue.modifyAppointmentDurations(durationIncrementSeconds);
			}
		});
	}

	public static void modifyAppointmentComment(ModelStoreSync modelStore, 
			                                    String queueId, 
			                                    String comment, 
			                                    User student) throws BackendError {
		T.call(QueueManager.class);

		modelStore.updateModel(QueueModel.class, "admin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				
				queue.modifyAppointmentComment(student.getId(), comment);
			}
		});
		
	}

	public static void updateIsQueueOpen(ModelStoreSync modelStore, String courseId, boolean isQueueOpen, User user) throws BackendError {
		T.call(QueueManager.class);
		
		modelStore.updateModel(QueueModel.class, "admin", user.getId(), new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				
				queue.updateIsQueueOpenForCourseId(courseId, isQueueOpen);
			}
		});
	}

	public static void addCourseSettings(ModelStoreSync modelStore, 
										 CoursePath coursePath,
			                             String courseTitle, 
			                             User user) throws BackendError {
		T.call(QueueManager.class);

		modelStore.updateModel(QueueModel.class, "admin", user.getId(), new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				
				queue.addCourseSettings(coursePath);
				queue.updateCourseTitle(coursePath, courseTitle);
			}
		});
	}

	public static void addGroup(ModelStoreSync modelStore, 
			                    String courseId, 
			                    String groupId, 
			                    User user) throws BackendError {

		T.call(QueueManager.class);

		modelStore.updateModel(QueueModel.class, "admin", user.getId(), new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				
				queue.addGroupSettings(courseId, groupId);
			}
		});
	}

	public static void updateQueueInfo(ModelStoreSync modelStore, 
			                           String semesterId, 
			                           String courseId, 
			                           String groupId, 
			                           String queueMessage,
			                           String queueId) throws BackendError {

		T.call(QueueManager.class);
		
		modelStore.updateModel(QueueModel.class, "admin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				
				queue.updateQueueMessage(semesterId, courseId, groupId, queueMessage);
			}
		});
	}
}
