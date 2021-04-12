package ca.aquiletour.core.pages.semester_list.models;

import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;

public class SemesterListModel implements NtroModel {
	
	private ObservableSemesterList semesters = new ObservableSemesterList();

	public ObservableSemesterList getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterList semesters) {
		this.semesters = semesters;
	}
	
	public SemesterModel semesterById(String semesterId) {
		SemesterModel semester = null;
		
		for(int i = 0; i < getSemesters().size(); i++) {
			SemesterModel candidate = getSemesters().item(i);
			if(candidate.getSemesterId().equals(semesterId)) {
				semester = candidate;
				break;
			}
		}

		return semester;
	}

	public boolean ifSemesterIdExists(String semesterId) {
		return semesterById(semesterId) != null;
	}

	public void addSemester(SemesterModel semester) {
		T.call(this);
		
		if(!ifSemesterIdExists(semester.getSemesterId())) {
			semesters.addItem(semester);
		}
	}
}