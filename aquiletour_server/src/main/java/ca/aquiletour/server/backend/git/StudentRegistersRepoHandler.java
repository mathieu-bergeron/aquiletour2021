package ca.aquiletour.server.backend.git;

import ca.aquiletour.core.messages.git.RegisterRepo;
import ca.aquiletour.core.pages.course.student.messages.StudentRegistersRepoMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class StudentRegistersRepoHandler extends BackendMessageHandler<StudentRegistersRepoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, StudentRegistersRepoMessage message) {
		T.call(this);
		
		GitMessages.sendMessage(RegisterRepo.fromStudentRegistersRepoMessage(message));
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, StudentRegistersRepoMessage message) {
		T.call(this);
		
		// Add completion to course model
		
		
		
	}

}
