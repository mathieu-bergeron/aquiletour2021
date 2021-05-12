package ca.aquiletour.core.pages.semester_list.models;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public abstract class SemesterList<SM extends SemesterModel> implements NtroModel {
	
	private StoredString currentSemesterId = new StoredString(Constants.DRAFTS_SEMESTER_ID);
	private ObservableSemesterList<SM> semesters = new ObservableSemesterList<SM>();

	public ObservableSemesterList<SM> getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterList<SM> semesters) {
		this.semesters = semesters;
	}
	
	public StoredString getCurrentSemesterId() {
		return currentSemesterId;
	}

	public void setCurrentSemesterId(StoredString currentSemesterId) {
		this.currentSemesterId = currentSemesterId;
	}


	public SM semesterById(String semesterId) {
		T.call(this);

		int semesterIndex = semesterIndexById(semesterId);
		SM semester = null;
		
		if(semesterIndex != -1) {
			semester = getSemesters().item(semesterIndex);
		}

		return semester;
	}

	public int semesterIndexById(String semesterId) {
		T.call(this);

		int index = -1;
		
		for(int i = 0; i < getSemesters().size(); i++) {
			SemesterModel candidate = getSemesters().item(i);
			if(candidate.getSemesterId().equals(semesterId)) {
				index = i;
				break;
			}
		}

		return index;
	}

	public boolean ifSemesterIdExists(String semesterId) {
		return semesterIndexById(semesterId) != -1;
	}

	public void addSemester(SM semester) {
		T.call(this);
		
		if(!ifSemesterIdExists(semester.getSemesterId())) {
			semesters.insertItem(0, semester);
		}
	}

	public void addSemesterWeek(String semesterId, CalendarWeek semesterWeek) {
		T.call(this);
		
		SemesterModel semester = semesterById(semesterId);
		
		if(semester != null) {
			
			semester.addWeek(semesterWeek);
			
		} else {
			
			Log.warning("Semester not found: " + semesterId);
		}
	}

	public void selectCurrentSemester(String semesterId) {
		T.call(this);

		getCurrentSemesterId().set(semesterId);
	}



	public SemesterSchedule semesterSchedule(String semesterId) {
		T.call(this);

		SemesterModel semester = semesterById(semesterId);
		SemesterSchedule schedule = null;
		
		if(semester != null) {
			
			schedule = semester.getSemesterSchedule();
			
		} else {
			
			Log.warning("Semester not found: " + semesterId);
		}
		
		return schedule;
	}


	public void removeSemester(String semesterId) {
		T.call(this);

		int semesterIndex = semesterIndexById(semesterId);

		if(semesterIndex != -1) {
			
			getSemesters().removeItemAtIndex(semesterIndex);
			
		} else {
			
			Log.warning("Semester not found: " + semesterId);
		}
	}
}
