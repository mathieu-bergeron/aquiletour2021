package ca.aquiletour.core.models.dates;

import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public abstract class CourseDate extends AquiletourDate {

	private int semesterWeek;                  // typically 1-15
	private NtroDate weekOf = new NtroDate();

	public CourseDate() {
		T.call(this);
	}

	public CourseDate(int semesterWeek) {
		T.call(this);
		
		this.semesterWeek = semesterWeek;
	}

	public int getSemesterWeek() {
		return semesterWeek;
	}

	public void setSemesterWeek(int semesterWeek) {
		this.semesterWeek = semesterWeek;
	}

	public NtroDate getWeekOf() {
		return weekOf;
	}

	public void setWeekOf(NtroDate weekOf) {
		this.weekOf = weekOf;
	}

	public boolean updateDate(SemesterSchedule semesterSchedule) {
		boolean updated = false;
		
		NtroDate startDate = semesterSchedule.startDateForSemesterWeek(semesterWeek);

		if(startDate != null) {
			this.weekOf = startDate;
			updated = true;
		}

		return updated;
	}

	public abstract SemesterDate resolveDate(String courseId,
											 String groupId, 
											 SemesterSchedule 
											 semesterSchedule, 
											 TeacherSchedule teacherSchedule);
}
