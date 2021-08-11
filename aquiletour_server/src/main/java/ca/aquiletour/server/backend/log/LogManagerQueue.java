package ca.aquiletour.server.backend.log;

import ca.aquiletour.core.models.logs.LogModelQueue;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.queue.models.Appointment;
import ca.aquiletour.server.backend.queue.QueueManager;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.ModelStoreSync;

public class LogManagerQueue {

	public static void logNewAppointment(ModelStoreSync modelStore, 
			                             String queueId, 
			                             User user, 
			                             NtroDate timestamp, 
			                             Appointment appointment) throws BackendError {
		T.call(QueueManager.class);
		
		modelStore.updateModel(LogModelQueue.class, "admin", queueId, queueLog -> {

			queueLog.addAppointement(timestamp, user, appointment);

		});
	}

	public static void createQueueLogForUser(ModelStoreSync modelStore,
								   		     User user) throws BackendError {

		T.call(QueueManager.class);
		
		modelStore.createModel(LogModelQueue.class, "admin", user.getId(), logModel -> {
			
		});
	}
	
}
