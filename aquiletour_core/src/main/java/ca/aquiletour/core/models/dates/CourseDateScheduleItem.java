package ca.aquiletour.core.models.dates;

import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.ntro.core.system.trace.T;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroDayOfWeek;

public class CourseDateScheduleItem extends CourseDate {
	
	public static final String END_OF_CLASS = "end";
	public static final String START_OF_CLASS = "start";

	private String scheduleItemId = "";
	private String startOrEnd = END_OF_CLASS;

	public CourseDateScheduleItem() {
		T.call(this);
	}

	public CourseDateScheduleItem(int semesterWeek, String scheduleItemId, String startOrEnd) {
		super(semesterWeek);
		T.call(this);
		
		this.scheduleItemId = scheduleItemId;
		this.startOrEnd = startOrEnd;
	}

	public String getScheduleItemId() {
		return scheduleItemId;
	}

	public void setScheduleItemId(String scheduleItemId) {
		this.scheduleItemId = scheduleItemId;
	}

	public String getStartOrEnd() {
		return startOrEnd;
	}

	public void setStartOrEnd(String startOrEnd) {
		this.startOrEnd = startOrEnd;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Semaine ");

		builder.append(getSemesterWeek());
		
		if(getWeekOf().isDefined()) {
			builder.append(" (");
			builder.append(getWeekOf().format("d MMM"));
			builder.append("), ");
		}else {
			builder.append(", ");
		}
		
		if(getStartOrEnd().equals(END_OF_CLASS)) {
			builder.append("à la fin");
		}else {
			builder.append("au début");
		}
		builder.append(" de la séance ");
		builder.append(getScheduleItemId());
		
		return builder.toString();
	}

	@Override
	public SemesterDate resolveDate(String courseId,
									String groupId, 
			                        SemesterSchedule semesterSchedule, 
			                        TeacherSchedule teacherSchedule) {
		T.call(this);

		SemesterDate date = null;
		
		ScheduleItem scheduleItem = teacherSchedule.findScheduleItem(courseId, groupId, scheduleItemId);

		if(scheduleItem != null) {

			date = semesterSchedule.resolveDate(getSemesterWeek(), scheduleItem.getDayOfWeek());

			if(date != null) {
				if(getStartOrEnd().equals(END_OF_CLASS)) {
					date.adjustTime(scheduleItem.getEndTime());
				}else {
					date.adjustTime(scheduleItem.getStartTime());
				}
			}
		}

		return date;
	}

}
