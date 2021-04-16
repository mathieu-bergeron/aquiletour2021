package ca.aquiletour.core.pages.group_list.models;

import java.util.List;

import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.models.users.User;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;

public class GroupDescription implements NtroModelValue {
	
	private String semesterId = "";
	private String courseId = "";
	private String groupId = "";
	private ObservableStudentList students = new ObservableStudentList();

	public GroupDescription() {
		T.call(this);
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void addStudents(List<User> studentsToAdd) {
		T.call(this);

		for(User studentToAdd : studentsToAdd) {
			StudentDescription studentDescription = new StudentDescription();
			studentDescription.setName(studentToAdd.getName());
			studentDescription.setSurname(studentToAdd.getSurname());
			
			MustNot.beNull(studentToAdd.getId());
			
			studentDescription.setId(studentToAdd.getId());

			students.addItem(studentDescription);
		}
	}

	public ObservableStudentList getStudents() {
		return students;
	}

	public void setStudents(ObservableStudentList students) {
		this.students = students;
	}
}
