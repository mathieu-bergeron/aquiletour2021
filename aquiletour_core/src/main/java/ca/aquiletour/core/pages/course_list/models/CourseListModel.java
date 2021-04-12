package ca.aquiletour.core.pages.course_list.models;

import ca.aquiletour.core.Constants;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredString;

public class CourseListModel implements NtroModel {
	
	private ObservableSemesterIdList semesters = new ObservableSemesterIdList();
	private StoredString currentSemester = new StoredString(Constants.COURSE_DRAFTS);

	public ObservableSemesterIdList getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterIdList semesters) {
		this.semesters = semesters;
	}

	public StoredString getCurrentSemester() {
		return currentSemester;
	}

	public void setCurrentSemester(StoredString currentSemester) {
		this.currentSemester = currentSemester;
	}
}
