package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.course.student.messages.StudentRegistersRepoMessage;
import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;

public class RegisterGitRepo extends StudentExerciseMessage {
	
	private String repoPath;
	private String repoUrl;
	
	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public String getRepoPath() {
		return repoPath;
	}

	public void setRepoPath(String repoPath) {
		this.repoPath = repoPath;
	}

	public static RegisterGitRepo fromStudentRegistersRepoMessage(StudentRegistersRepoMessage message) {
		T.call(RegisterGitRepo.class);
		
		RegisterGitRepo registerRepo = Ntro.messages().create(RegisterGitRepo.class);
		
		registerRepo.setCourseId(message.getTeacherId() + "/" + message.getCourseId());
		registerRepo.setExercisePath(message.getTaskPath().toString());
		registerRepo.setGroupId(message.getGroupId());
		registerRepo.setRepoPath(message.getRepoPath().toString());
		registerRepo.setRepoUrl(message.getRepoUrl());
		registerRepo.setSemesterId(message.getSemesterId());
		registerRepo.setStudentId(message.getStudentId());
		
		return registerRepo;
	}
}
