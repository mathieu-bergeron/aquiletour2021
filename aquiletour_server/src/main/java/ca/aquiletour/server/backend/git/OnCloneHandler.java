package ca.aquiletour.server.backend.git;

import ca.aquiletour.core.messages.git.OnClone;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTask;
import ca.aquiletour.core.models.courses.atomic_tasks.AtomicTaskCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoCloned;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoCompletion;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoTask;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.backend.BackendError;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ValueReader;
import ca.ntro.core.system.trace.T;

public class OnCloneHandler extends BackendMessageHandler<OnClone> {

	@Override
	public void handleNow(ModelStoreSync modelStore, OnClone message) throws BackendError {
		T.call(this);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, OnClone message) throws BackendError {
		T.call(this);

		GitRepoCloned gitRepoCloned = new GitRepoCloned();
		
		String repoUrl = message.getRepoUrl();
		
		if(repoUrl == null || repoUrl.isEmpty()) {

			fetchRepoUrlFromPreviousCompletion(modelStore, message, gitRepoCloned);

		}else {

			gitRepoCloned.setRepoUrl(repoUrl);
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

	private void fetchRepoUrlFromPreviousCompletion(ModelStoreSync modelStore, 
			                                        OnClone message, 
			                                        GitRepoCloned gitRepoCloned) {
		T.call(this);

		CourseManager.readAtomicTaskCompletionStudent(modelStore, 
													  message.coursePath(), 
													  message.getStudentId(), 
													  message.taskPath(), 
													  AtomicTask.idFromType(GitRepoTask.class),
													  new ValueReader<AtomicTaskCompletion>() {
														@Override
														public void read(AtomicTaskCompletion completion) {
															T.call(this);
															
															if(completion instanceof GitRepoCompletion) {
																gitRepoCloned.setRepoUrl(((GitRepoCompletion) completion).getRepoUrl());
															}
														}
												});
	}
}
