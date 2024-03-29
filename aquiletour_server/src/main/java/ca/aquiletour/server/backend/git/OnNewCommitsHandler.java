package ca.aquiletour.server.backend.git;


import ca.aquiletour.core.messages.git.OnNewCommits;
import ca.aquiletour.server.registered_sockets.RegisteredSocketsSockJS;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class OnNewCommitsHandler extends BackendMessageHandler<OnNewCommits> {


	@Override
	public void handleNow(ModelStoreSync modelStore, OnNewCommits message) throws BackendError {
		T.call(this);
		T.here();
		String studentId = message.getStudentId();
		RegisteredSocketsSockJS.sendMessageToUserId(studentId, message);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, OnNewCommits message) {
		
	}
}
