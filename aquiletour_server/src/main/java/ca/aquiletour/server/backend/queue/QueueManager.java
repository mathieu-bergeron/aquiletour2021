package ca.aquiletour.server.backend.queue;

import ca.aquiletour.core.models.paths.CoursePath;
import ca.aquiletour.core.models.paths.TaskPath;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.server.backend.open_queues_list.QueuesUpdater;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;

public class QueueManager {

	public static void createQueueForUser(ModelStoreSync modelStore,
								   		  User user) throws BackendError {

		T.call(QueueManager.class);
		
		createQueue(modelStore, user.getId());
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

	public static void openQueue(ModelStoreSync modelStore,
			                     String queueId) throws BackendError {

		T.call(QueueManager.class);
		
		QueuesUpdater.openQueue(modelStore, queueId);
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

		Appointment appointment = createAppointment(user, coursePath, taskPath, taskTitle);
		
		modelStore.updateModel(QueueModel.class, "admin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);

				queue.addAppointment(appointment);
			}
		});
	}

	private static Appointment createAppointment(User user, CoursePath coursePath, TaskPath taskPath, String taskTitle) {
		T.call(QueueManager.class);

		Appointment appointment = new Appointment();

		appointment.updateTime(Ntro.calendar().now());
		appointment.setStudentId(user.getId());
		appointment.setStudentName(user.getFirstname());
		appointment.setStudentSurname(user.getLastname());
		if(coursePath != null) {
			appointment.updateCourseTitle(coursePath.courseId());
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
}
