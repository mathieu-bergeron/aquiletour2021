package ca.aquiletour.core.models.courses.model;

import java.util.List;

import ca.aquiletour.core.models.courses.group_description.StudentIdList;
import ca.aquiletour.core.models.users.User;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public class GroupDescription implements NtroModelValue {

	private String groupId = "";
	private StudentIdList students = new StudentIdList();

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public StudentIdList getStudents() {
		return students;
	}

	public void setStudents(StudentIdList students) {
		this.students = students;
	}
	
	public boolean containsStudent(String studentId) {
		T.call(this);

		boolean contains = false;

		for(String candidateId : students.getValue()) {
			if(candidateId.equals(studentId)) {
				contains = true;
				break;
			}
		}
		
		return contains;
	}

	public void addStudents(List<User> studentsToAdd) {
		T.call(this);

		for(User student : studentsToAdd) {
			getStudents().addItem(student.getId());
		}
	}
}
