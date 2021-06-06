package ca.aquiletour.server.backend.git;


import ca.aquiletour.core.messages.git.OnNewCommits;
import ca.aquiletour.server.registered_sockets.RegisteredSockets;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class OnNewCommitsHandler extends BackendMessageHandler<OnNewCommits> {


	@Override
	public void handleNow(ModelStoreSync modelStore, OnNewCommits message) {
		T.call(this);
		T.here();
		String studentId = message.getStudentId();
		RegisteredSockets.sendMessageToUserId(studentId, message);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, OnNewCommits message) {
		
	}
}
