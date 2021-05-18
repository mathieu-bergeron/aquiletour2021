package ca.aquiletour.server.backend.git;

import ca.aquiletour.core.messages.git.DeleteGitRepo;
import ca.aquiletour.core.pages.course.student.messages.StudentDeletesRepoMessage;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class StudentDeletesRepoHandler extends BackendMessageHandler<StudentDeletesRepoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, StudentDeletesRepoMessage message) throws BackendMessageHandlerError {
		T.call(this);

		GitMessages.sendMessage(new DeleteGitRepo(message));

		CourseManager.removeAtomicTaskCompletionStudent(modelStore, 
				                                  		message.coursePath(), 
				                                  		message.getStudentId(),
				                                  		message.getTaskPath(), 
				                                  		message.getAtomicTaskId());
		
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, StudentDeletesRepoMessage message) {
		T.call(this);

		CourseManager.removeAtomicTaskCompletionTeacher(modelStore, 
				                                 		message.coursePath(), 
				                                 		message.getStudentId(),
				                                 		message.getTaskPath(), 
				                                 		message.getAtomicTaskId());
	}

}
