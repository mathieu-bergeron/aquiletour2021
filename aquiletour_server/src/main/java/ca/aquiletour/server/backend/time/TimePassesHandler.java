package ca.aquiletour.server.backend.time;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.time.TimePassesMessage;
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

				runNightly();

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
		
		modelStore.forEachModelId(NtroSession.class, "admin", modelId -> {
			
			Log.info(modelId);
			
		});
		
		// TODO: update currentTime in queues etc.
	}
	
	private void runNightly() throws BackendError {
		T.call(this);

		Log.info("[runNightly]");

		// TODO: update timeToLive everywhere and remove any obselete model
	}
}
