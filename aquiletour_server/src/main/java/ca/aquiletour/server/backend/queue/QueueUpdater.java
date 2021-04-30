package ca.aquiletour.server.backend.queue;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.server.backend.open_queues_list.QueuesUpdater;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class QueueUpdater {

	public static void createQueue(ModelStoreSync modelStore,
								   String queueId,
			                       User user) {

		T.call(QueueUpdater.class);
		
		createQueue(modelStore, user.getId(), queueId);
	}

	public static void createQueue(ModelStoreSync modelStore,
								   String teacherId,
			                       String queueId) {

		T.call(QueueUpdater.class);

		modelStore.createModel(QueueModel.class, 
							   "admin",
				               queueId, 
				               new ModelInitializer<QueueModel>() {
			@Override
			public void initialize(QueueModel newQueue) {
				T.call(this);

				newQueue.setTeacherId(teacherId);
				newQueue.setCourseId(queueId);
			}
		});
	}

	public static void deleteQueue(ModelStoreSync modelStore,
			                       String queueId) {

		T.call(QueueUpdater.class);

		QueuesUpdater.deleteQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);
	
		modelStore.delete(queue);
	}

	public static void openQueue(ModelStoreSync modelStore,
			                     String queueId) {

		T.call(QueueUpdater.class);
		
		QueuesUpdater.openQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);
		
		modelStore.closeWithoutSaving(queue);

	}

	public static void closeQueue(ModelStoreSync modelStore,
			                      String queueId) {

		T.call(QueueUpdater.class);

		QueuesUpdater.closeQueue(modelStore, queueId);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);
		
		modelStore.updateModel(QueueModel.class, "amdin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				queue.clearQueue();
			}
		});
	}

	public static int addStudentsToQueue(ModelStoreSync modelStore, 
			                             String queueId, 
			                             List<User> studentsToAdd) {
		T.call(QueueUpdater.class);

		QueueModel queue = modelStore.getModel(QueueModel.class, 
				"admin",
				queueId);

		int numberOfStudentAdded = 0;
		
		for(User student : studentsToAdd) {
			String studentId = student.getId();
			
			if(!queue.getStudentIds().contains(studentId)) {

				queue.getStudentIds().add(studentId);
				numberOfStudentAdded++;
			}
		}

		if(numberOfStudentAdded > 0) {
			modelStore.save(queue);
		}
		
		modelStore.closeWithoutSaving(queue);

		return numberOfStudentAdded;
	}

	public static void addAppointmentForUser(ModelStoreSync modelStore,
			                                 String queueId,
			                                 User user) {

		T.call(QueueUpdater.class);

		Appointment appointment = createAppointment(user);
		
		modelStore.updateModel(QueueModel.class, "admin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);

				queue.addAppointment(appointment);
			}
		});
	}

	private static Appointment createAppointment(User user) {
		T.call(QueueUpdater.class);

		Appointment appointment = new Appointment();

		appointment.updateTime(Ntro.calendar().now());
		appointment.setStudentId(user.getId());
		appointment.setStudentName(user.getName());
		appointment.setStudentSurname(user.getSurname());
		
		return appointment;
	}

	public static void addAppointmentUpdates(ModelStoreSync modelStore, String queueId) {
		T.call(QueueUpdater.class);
		
		numberOfAppointmentUpdates(modelStore, queueId);
	}

	// FIXME: much better to increment number of appointments
	//        if two threads add appointements, this size() could be wrong
	private static void numberOfAppointmentUpdates(ModelStoreSync modelStore, String queueId) {
		T.call(QueueUpdater.class);

		QueueModel queue = modelStore.getModel(QueueModel.class, "admin", queueId);

		// FIXME: use increment insted
		int nbAppointment = queue.getAppointments().size();

		String teacherId = queue.getTeacherId();
		List<String> studentIds = queue.getStudentIds();

		modelStore.closeWithoutSaving(queue);
	}

	public static Appointment getAppointmentById(ModelStoreSync modelStore, String queueId, String appointmentId) {
		T.call(QueueUpdater.class);

		QueueModel queue = modelStore.getModel(QueueModel.class, "admin", queueId);
		
		Appointment appointment = queue.appointmentById(appointmentId);

		return appointment;
	}

	public static void deleteAppointment(ModelStoreSync modelStore, String queueId, String appointmentId) {
		T.call(QueueUpdater.class);
		
		modelStore.updateModel(QueueModel.class, "admin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);

				queue.deleteAppointment(appointmentId);
			}
		});
	}

	public static void deleteAppointmentUpdates(ModelStoreSync modelStore, String queueId, Appointment deletedAppointment) {
		T.call(QueueUpdater.class);
		
		String appointmentOwnerId = deletedAppointment.getStudentId();
		
		numberOfAppointmentUpdates(modelStore, queueId);
	}

	public static void moveAppointment(ModelStoreSync modelStore, 
			                           String queueId, 
			                           String appointmentId, 
			                           String destinationId,
			                           String beforeOrAfter) {

		T.call(QueueUpdater.class);
		
		modelStore.updateModel(QueueModel.class, "admin", queueId, new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);

				queue.moveAppointment(appointmentId, destinationId, beforeOrAfter);
			}
		});
	}

	public static void modifyAppointmentTimes(ModelStoreSync modelStore, int timeIncrementSeconds, User user) {
		T.call(QueueUpdater.class);
		
		modelStore.updateModel(QueueModel.class, "admin", user.getId(), new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				
				queue.incrementAppointmentTimesSeconds(timeIncrementSeconds);
			}
		});
	}

	public static void modifyAppointmentDurations(ModelStoreSync modelStore, int durationIncrementSeconds, User user) {
		T.call(QueueUpdater.class);
		
		modelStore.updateModel(QueueModel.class, "admin", user.getId(), new ModelUpdater<QueueModel>() {
			@Override
			public void update(QueueModel queue) {
				T.call(this);
				
				queue.modifyAppointmentDurations(durationIncrementSeconds);
			}
		});
	}
}
