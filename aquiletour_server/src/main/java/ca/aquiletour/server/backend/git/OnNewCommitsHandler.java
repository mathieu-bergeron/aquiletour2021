package ca.aquiletour.server.backend.git;


import ca.aquiletour.core.messages.git.OnNewCommits;
import ca.aquiletour.server.RegisteredSockets;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class OnNewCommitsHandler extends BackendMessageHandler<OnNewCommits> {


	@Override
	public void handleNow(ModelStoreSync modelStore, OnNewCommits message) {
		// TODO Auto-generated method stub
		T.call(this);
		T.here();
		String studentId = message.getStudentId();
		RegisteredSockets.sendMessageToUserId(studentId, message);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, OnNewCommits message) {
		// TODO Auto-generated method stub
		
	}
}
