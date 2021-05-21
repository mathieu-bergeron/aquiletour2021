package ca.aquiletour.server.backend.git;

import ca.aquiletour.core.messages.git.OnClone;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoCloned;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoTask;
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

		AtomicTaskCompletion completion = CourseManager.getAtomicTaskCompletionStudent(modelStore, 
				                                                                       message.coursePath(), 
				                                                                       message.getStudentId(), 
				                                                                       message.taskPath(), 
				                                                                       AtomicTask.idFromType(GitRepoTask.class));
		
		GitRepoCloned gitRepoCloned = new GitRepoCloned();
		
		if(completion instanceof GitRepoCompletion) {
			gitRepoCloned.setRepoUrl(((GitRepoCompletion) completion).getRepoUrl());
		}
		
		CourseManager.updateAtomicTaskCompletionStudent(modelStore, 
				                                        message.coursePath(), 
				                                        message.getStudentId(), 
				                                        message.taskPath(), 
				                                        AtomicTask.idFromType(GitRepoTask.class),
				                                        gitRepoCloned);

		CourseManager.updateAtomicTaskCompletionTeacher(modelStore, 
				                                        message.coursePath(), 
				                                        message.getStudentId(), 
				                                        message.taskPath(), 
				                                        AtomicTask.idFromType(GitRepoTask.class),
				                                        gitRepoCloned);
	}

}
