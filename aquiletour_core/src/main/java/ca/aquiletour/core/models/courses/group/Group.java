package ca.aquiletour.core.models.courses.group;

import ca.ntro.core.models.NtroModelValue;

public class Group implements NtroModelValue {

	private String groupId;
	private ObservableStudentMap students = new ObservableStudentMap();

	public ObservableStudentMap getStudents() {
		return students;
	}

	public void setStudents(ObservableStudentMap students) {
		this.students = students;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}
