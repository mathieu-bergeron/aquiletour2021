package ca.aquiletour.server.backend.time;

import java.util.HashSet;
import java.util.Set;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.time.TimePassesMessage;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.server.backend.login.SessionManager;
import ca.aquiletour.server.registered_sockets.RegisteredSocketsSockJS;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.services.ModelStoreSync;
import ca.ntro.services.Ntro;
import ca.ntro.users.NtroSession;

public class TimePassesHandler extends BackendMessageHandler<TimePassesMessage> {
	
	private NtroDate nextNighlyTime = Ntro.calendar().now();
	private NtroDate nextFrequentlyTime = Ntro.calendar().now();

	public TimePassesHandler() {
		if(Ntro.config().isProd()) {
			nextNighlyTime.adjustTime(Constants.NIGHTLY_TASKS_TIME);
		}
	}

	@Override
	public void handleNow(ModelStoreSync modelStore, TimePassesMessage message) throws BackendError {
		T.call(this);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, TimePassesMessage message) throws BackendError {
		T.call(this);

		runEveryTime(modelStore);

		runNightlyTasksWhenNeeded(modelStore, message);

		runFrequentTasksWhenNeeded(modelStore, message);
	}

	private void runFrequentTasksWhenNeeded(ModelStoreSync modelStore, TimePassesMessage message) throws BackendError {
		T.call(this);

		NtroDate currentTime = message.getCurrentTime();
		
		if(currentTime != null) {

			if(currentTime.biggerThan(nextFrequentlyTime)) {

				runFrequently(modelStore);

				if(Ntro.config().isProd()) {

					nextFrequentlyTime = nextFrequentlyTime.deltaMinutes(Constants.FREQUENT_TASKS_PERDIOD_MINUTES);

				}else {

					nextFrequentlyTime = nextFrequentlyTime.deltaMinutes(1);
				}
			}

		}else {
			
			Log.warning("[TimePassesHandler] currentTime is null");
		}
	}

	private void runNightlyTasksWhenNeeded(ModelStoreSync modelStore, TimePassesMessage message) throws BackendError {
		T.call(this);

		NtroDate currentTime = message.getCurrentTime();
		
		if(currentTime != null) {

			if(currentTime.biggerThan(nextNighlyTime)) {

				runNightly(modelStore);

				if(Ntro.config().isProd()) {

					nextNighlyTime = nextNighlyTime.deltaDays(1);

				}else {

					nextNighlyTime = nextNighlyTime.deltaMinutes(1);
				}
			}

		}else {
			
			Log.warning("[TimePassesHandler] currentTime is null");
		}
	}

	private void runEveryTime(ModelStoreSync modelStore) throws BackendError {
		T.call(this);

		// Log.info("[runEveryTime]");
		
		// TODO: update currentTime in queues etc.
	}

	private void runFrequently(ModelStoreSync modelStore) throws BackendError {
		T.call(this);

		Log.info("[runFrequently]");
		
		deleteExpiredSessions(modelStore);

		updateFirstAppointmentTimeInQueues(modelStore);
	}

	private void updateFirstAppointmentTimeInQueues(ModelStoreSync modelStore) throws BackendError {
		T.call(this);

		modelStore.forEachModelId(QueueModel.class, "admin", queueId -> {
			modelStore.updateModel(QueueModel.class, "admin", queueId, queueModel -> {
				
				queueModel.updateFirstAppointmentTimeIfNeeded();
			});
		});
	}

	private void deleteExpiredSessions(ModelStoreSync modelStore) throws BackendError {
		Set<String> expiredSesions = new HashSet<>();
		
		modelStore.forEachModelId(NtroSession.class, "admin", authToken -> {
			
			modelStore.readModel(NtroSession.class, "admin", authToken, session -> {
				
				if(session.hasExpired()) {

					expiredSesions.add(authToken);
				}
			});
		});

		for(String authToken : expiredSesions) {
			SessionManager.deleteSession(modelStore, authToken);
		}
	}
	
	private void runNightly(ModelStoreSync modelStore) throws BackendError {
		T.call(this);

		Log.info("[runNightly]");

		// TODO: a pass at the data to remove any inconsistencies
		deregisterOrphanSockets(modelStore);
	}

	private void deregisterOrphanSockets(ModelStoreSync modelStore) throws BackendError {
		T.call(this);

		Set<String> sessionIds = getActiveSessionIds(modelStore);

		RegisteredSocketsSockJS.deregisterOrphanSockets(sessionIds);
	}

	private Set<String> getActiveSessionIds(ModelStoreSync modelStore) throws BackendError {
		T.call(this);

		Set<String> sessionIds = new HashSet<>();
		
		modelStore.forEachModelId(NtroSession.class, "admin", authToken -> {
			sessionIds.add(authToken);
		});

		return sessionIds;
	}
}
