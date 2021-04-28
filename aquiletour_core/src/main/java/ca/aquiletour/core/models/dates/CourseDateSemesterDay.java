package ca.aquiletour.core.models.dates;

import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.models.NtroDayOfWeek;

public class CourseDateSemesterDay extends CourseDate {
	
	private NtroDayOfWeek semesterDay;

	public NtroDayOfWeek getSemesterDay() {
		return semesterDay;
	}

	public void setSemesterDay(NtroDayOfWeek semesterDay) {
		this.semesterDay = semesterDay;
	}

	@Override
	public SemesterDate resolveDate(String courseId, 
			                        String groupId, 
			                        SemesterSchedule semesterSchedule, 
			                        TeacherSchedule teacherSchedule) {
		return null;
	}

}
