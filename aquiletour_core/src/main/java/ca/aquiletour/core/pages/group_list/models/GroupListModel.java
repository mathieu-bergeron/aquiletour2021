package ca.aquiletour.core.pages.group_list.models;

import java.util.List;

import ca.aquiletour.core.models.user.User;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class GroupListModel implements NtroModel {
	
	private SemesterCourses semesterCourses = new SemesterCourses();
	private ObservableGroupList groups = new ObservableGroupList();

	public SemesterCourses getSemesterCourses() {
		return semesterCourses;
	}

	public void setSemesterCourses(SemesterCourses semesterCourses) {
		this.semesterCourses = semesterCourses;
	}

	public ObservableGroupList getGroups() {
		return groups;
	}

	public void setGroups(ObservableGroupList groups) {
		this.groups = groups;
	}

	public void addGroup(String semesterId, String courseId, String groupId, List<User> studentsToAdd) {
		T.call(this);
		
		GroupItem groupDescription = new GroupItem();
		groupDescription.setSemesterId(semesterId);
		groupDescription.setCourseId(courseId);
		groupDescription.setGroupId(groupId);
		
		getGroups().addItem(groupDescription);

		groupDescription.addStudents(studentsToAdd);
	}
	
	public void addSemester(String semesterId) {
		T.call(this);
		
		getSemesterCourses().addSemester(semesterId);
	}

	public void addCourse(String semesterId, String courseId) {
		T.call(this);
		
		if(!getSemesterCourses().containsKey(semesterId)) {
			addSemester(semesterId);
		}
		
		getSemesterCourses().addCourse(semesterId, courseId);
	}


}
