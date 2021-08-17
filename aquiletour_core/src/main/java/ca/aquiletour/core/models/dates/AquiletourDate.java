package ca.aquiletour.core.models.dates;

import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.core.system.trace.T;

public abstract class AquiletourDate implements NtroModelValue {

	public static AquiletourDate undefined() {
		T.call(AquiletourDate.class);
		
		return new ConcreteDate();
	}

	public abstract boolean isDefined();

	public abstract boolean updateDate(SemesterSchedule semesterSchedule);

	public abstract SemesterDate resolveDate(String courseId,
											 String groupId, 
											 SemesterSchedule semesterSchedule, 
											 TeacherSchedule teacherSchedule);
}
