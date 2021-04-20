package ca.aquiletour.core.models.schedule;

import ca.ntro.core.models.NtroModel;

public class TeacherSchedule implements NtroModel {
	
	private String semesterId = "";
	private String teacherId = "";
	private ObservableScheduleItems scheduleItems = new ObservableScheduleItems();

}
