package ca.aquiletour.server.backend.git;

import ca.aquiletour.core.messages.git.RegisterGitRepo;
import ca.aquiletour.core.models.courses.atomic_tasks.git_repo.GitRepoSubmitted;
import ca.aquiletour.core.pages.course.student.messages.StudentRegistersRepoMessage;
import ca.aquiletour.server.backend.course.CourseManager;
import ca.ntro.backend.BackendError;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStoreSync;

public class StudentRegistersRepoHandler extends BackendMessageHandler<StudentRegistersRepoMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, StudentRegistersRepoMessage message) throws BackendError {
		T.call(this);
		
		GitMessages.sendMessage(new RegisterGitRepo(message));

		GitRepoSubmitted gitRepoSubmitted = new GitRepoSubmitted();
		gitRepoSubmitted.setAtomicTaskId(message.getAtomicTaskId());
		gitRepoSubmitted.setRepoUrl(message.getRepoUrl());
		gitRepoSubmitted.setStudentId(message.getStudentId());

		CourseManager.updateAtomicTaskCompletionStudent(modelStore, 
				                                  		message.coursePath(), 
				                                  		message.getStudentId(),
				                                  		message.getTaskPath(), 
				                                  		message.getAtomicTaskId(), 
				                                  		gitRepoSubmitted);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, StudentRegistersRepoMessage message) throws BackendError {
		T.call(this);

		GitRepoSubmitted gitRepoSubmitted = new GitRepoSubmitted();
		gitRepoSubmitted.setAtomicTaskId(message.getAtomicTaskId());
		gitRepoSubmitted.setRepoUrl(message.getRepoUrl());
		gitRepoSubmitted.setStudentId(message.getStudentId());

		CourseManager.updateAtomicTaskCompletionTeacher(modelStore, 
				                                 		message.coursePath(), 
				                                 		message.getStudentId(),
				                                 		message.getTaskPath(), 
				                                 		message.getAtomicTaskId(), 
				                                 		gitRepoSubmitted);
	}

}
