package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.git.values.Commit;

public class OnExerciceCompletedMessage {

	private String courseId;
	private String semesterId;
	private String studentId;
	private String exercicePath;
	private Commit finalCommit;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getExercicePath() {
		return exercicePath;
	}

	public void setExercicePath(String exercicePath) {
		this.exercicePath = exercicePath;
	}

	public Commit getFinalCommit() {
		return finalCommit;
	}

	public void setFinalCommit(Commit finalCommit) {
		this.finalCommit = finalCommit;
	}
}
