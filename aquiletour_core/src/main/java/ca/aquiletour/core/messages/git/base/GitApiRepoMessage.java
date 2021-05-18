package ca.aquiletour.core.messages.git.base;

import ca.aquiletour.core.models.courses.CoursePath;
import ca.aquiletour.core.pages.course.student.messages.AquiletourGitMessage;
import ca.ntro.core.Path;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;

public class GitApiRepoMessage extends NtroMessage {

	private String courseId;
	private String semesterId;
	private String groupId;
	private String studentId;
	private String repoPath;
	
	public GitApiRepoMessage() {
		super();
		T.call(this);
		
	}

	public GitApiRepoMessage(AquiletourGitMessage message) {
		super();
		T.call(this);

		setCourseId(gitApiCourseId(message));
		setSemesterId(message.getSemesterId());
		setGroupId(message.getGroupId());
		setStudentId(message.getStudentId());
		setRepoPath(gitApiRepoPath(message));
	}

	private String gitApiCourseId(AquiletourGitMessage message) {
		T.call(this);
		
		Path teacherCourse = new Path();
		teacherCourse.addName(message.getTeacherId());
		teacherCourse.addName(message.getCourseId());

		return teacherCourse.toString();
	}

	private String gitApiRepoPath(AquiletourGitMessage message) {
		T.call(this);

		Path repoPath = message.getTaskPath().clone();
		repoPath.addName(message.getAtomicTaskId());
		
		return repoPath.toString();
	}

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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getRepoPath() {
		return repoPath;
	}

	public void setRepoPath(String repoPath) {
		this.repoPath = repoPath;
	}

	public CoursePath coursePath() {
		T.call(this);
		
		Path teacherCourse = new Path(getCourseId());
		CoursePath coursePath = null;
		
		if(teacherCourse.nameCount() >= 2) {
			
			coursePath = new CoursePath(teacherCourse.name(0), getSemesterId(), teacherCourse.name(1));

		}else {

			coursePath = new CoursePath();
			
		}

		return coursePath;
	}

	public Path taskPath() {
		T.call(this);
		
		Path repoPath = new Path(getRepoPath());
		Path taskPath = new Path();
		
		if(repoPath.nameCount() >= 2) {
			taskPath = repoPath.subPath(0, repoPath.nameCount()-2);
		}
		
		return taskPath;
	}

	public String atomicTaskId() {
		T.call(this);

		return new Path(getRepoPath()).lastName();
	}
}
