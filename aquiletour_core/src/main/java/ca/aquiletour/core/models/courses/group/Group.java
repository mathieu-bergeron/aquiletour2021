package ca.aquiletour.core.models.courses.group;

import java.util.List;

import ca.aquiletour.core.models.users.Student;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

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

	public static Group createGroup(String groupId, List<Student> studentsToAdd) {
		T.call(Group.class);
		
		Group group = new Group();
		group.setGroupId(groupId);
		
		ObservableStudentMap studentMap = new ObservableStudentMap();
		
		for(Student student : studentsToAdd) {
			
			
		}
		
		
		return null;
	}
	
}
