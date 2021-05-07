package ca.aquiletour.server.backend.git;


import ca.aquiletour.core.messages.git.OnExerciseCompleted;
import ca.aquiletour.core.messages.git.OnNewCommits;
import ca.aquiletour.server.RegisteredSockets;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class OnExerciseCompletedHandler extends BackendMessageHandler<OnExerciseCompleted> {

	@Override
	public void handleNow(ModelStoreSync modelStore, OnExerciseCompleted message) throws BackendMessageHandlerError {
		// TODO Auto-generated method stub
		T.call(this);
		T.here();
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, OnExerciseCompleted message) {
		// TODO Auto-generated method stub
		
	}
}
