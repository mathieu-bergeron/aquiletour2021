package ca.aquiletour.server.backend.git;

import ca.aquiletour.core.messages.git.OnClone;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoCloned;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendMessageHandlerError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class OnCloneHandler extends BackendMessageHandler<OnClone> {

	@Override
	public void handleNow(ModelStoreSync modelStore, OnClone message) throws BackendMessageHandlerError {
		T.call(this);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, OnClone message) {
		T.call(this);
		
		GitRepoCloned gitRepoCloned = new GitRepoCloned();
		
		CourseManager.updateAtomicTaskCompletionStudent(modelStore, 
				                                        message.coursePath(), 
				                                        message.getStudentId(), 
				                                        message.taskPath(), 
				                                        message.atomicTaskId(), 
				                                        gitRepoCloned);

		CourseManager.updateAtomicTaskCompletionTeacher(modelStore, 
				                                        message.coursePath(), 
				                                        message.getStudentId(), 
				                                        message.taskPath(), 
				                                        message.atomicTaskId(), 
				                                        gitRepoCloned);
	}

}
