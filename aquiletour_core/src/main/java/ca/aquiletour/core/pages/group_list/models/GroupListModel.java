package ca.aquiletour.core.pages.group_list.models;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course_list.models.ObservableSemesterIdList;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredList;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

public class GroupListModel implements NtroModel {
	
	private ObservableSemesterIdList semesters = new ObservableSemesterIdList();
	private ObservableGroupList groups = new ObservableGroupList();

	public ObservableSemesterIdList getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterIdList semesters) {
		this.semesters = semesters;
	}

	public ObservableGroupList getGroups() {
		return groups;
	}

	public void setGroups(ObservableGroupList groups) {
		this.groups = groups;
	}

	public void addGroup(String semesterId, String courseId, String groupId, List<User> studentsToAdd) {
		T.call(this);
		
		GroupDescription groupDescription = new GroupDescription();
		groupDescription.setSemesterId(semesterId);
		groupDescription.setCourseId(courseId);
		groupDescription.setGroupId(groupId);
		
		getGroups().addItem(groupDescription);

		// FIXME: the App should not have to do this!
		Ntro.modelStore().updateStoreConnections(this);

		groupDescription.addStudents(studentsToAdd);
	}
}
