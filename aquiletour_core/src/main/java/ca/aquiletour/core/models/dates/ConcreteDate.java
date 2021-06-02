package ca.aquiletour.core.models.dates;

import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;

public class ConcreteDate extends AquiletourDate {
	
	private NtroDate value = new NtroDate();

	public NtroDate getValue() {
		return value;
	}

	public void setValue(NtroDate value) {
		this.value = value;
	}

	@Override
	public  boolean isDefined() {
		T.call(this);
		
		return getValue().isDefined();
	}

	@Override
	public boolean updateDate(SemesterSchedule semesterSchedule) {
		T.call(this);

		return false;
	}

	@Override
	public SemesterDate resolveDate(String courseId, 
									String groupId, 
									SemesterSchedule semesterSchedule, 
									TeacherSchedule teacherSchedule) {
		T.call(this);

		return null;
	}
}
