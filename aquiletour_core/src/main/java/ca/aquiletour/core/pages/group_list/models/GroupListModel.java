package ca.aquiletour.core.pages.group_list.models;

import ca.aquiletour.core.pages.course_list.models.ObservableSemesterIdList;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredList;

public class GroupListModel implements NtroModel {
	
	private ObservableSemesterIdList semesters = new ObservableSemesterIdList();
	private StoredList<GroupDescription> groups = new StoredList<>();

	public ObservableSemesterIdList getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterIdList semesters) {
		this.semesters = semesters;
	}

	public StoredList<GroupDescription> getGroups() {
		return groups;
	}

	public void setGroups(StoredList<GroupDescription> groups) {
		this.groups = groups;
	}
}
