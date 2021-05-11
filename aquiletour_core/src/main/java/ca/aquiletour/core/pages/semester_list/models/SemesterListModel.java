package ca.aquiletour.core.pages.semester_list.models;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

public abstract class SemesterListModel implements NtroModel {
	
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

	public TeacherSchedule teacherSchedule(String semesterId) {
		T.call(this);

		SemesterModel semester = semesterById(semesterId);
		TeacherSchedule schedule = null;
		
		if(semester != null) {
			
			schedule = semester.getTeacherSchedule();
			
		} else {
			
			Log.warning("Semester not found: " + semesterId);
		}
		
		return schedule;
	}
}
