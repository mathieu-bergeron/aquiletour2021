package ca.aquiletour.core.pages.semester_list.models;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public class SemesterListModel implements NtroModel {
	
	private StoredString currentSemesterId = new StoredString(Constants.DRAFTS_SEMESTER_ID);
	private ObservableSemesterList semesters = new ObservableSemesterList();

	public ObservableSemesterList getSemesters() {
		return semesters;
	}

	public void setSemesters(ObservableSemesterList semesters) {
		this.semesters = semesters;
	}
	
	public StoredString getCurrentSemesterId() {
		return currentSemesterId;
	}

	public void setCurrentSemesterId(StoredString currentSemesterId) {
		this.currentSemesterId = currentSemesterId;
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

	public void addSemesterWeek(String semesterId, SemesterWeek semesterWeek) {
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

	public void addCourseGroup(String semesterId, String courseId, String groupId) {
		T.call(this);

		SemesterModel semester = semesterById(semesterId);
		
		if(semester != null) {
			
			semester.addCourseGroup(courseId, groupId);
			
		} else {
			
			Log.warning("Semester not found: " + semesterId);
		}
	}

	public void addScheduleItem(String semesterId, ScheduleItem scheduleItem) {
		T.call(this);

		SemesterModel semester = semesterById(semesterId);
		
		if(semester != null) {
			
			semester.addScheduleItem(scheduleItem);
			
		} else {
			
			Log.warning("Semester not found: " + semesterId);
		}
		
	}
}