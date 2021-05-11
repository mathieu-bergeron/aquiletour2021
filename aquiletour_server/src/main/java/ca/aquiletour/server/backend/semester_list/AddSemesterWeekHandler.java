package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.users.Admin;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.semester_list.admin.models.SemesterListModelAdmin;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.aquiletour.server.backend.schedule.ScheduleUpdater;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddSemesterWeekHandler extends BackendMessageHandler<AddSemesterWeekMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSemesterWeekMessage message) {
		T.call(this);
		
		String semesterId = message.getSemesterId();
		CalendarWeek semesterWeek = message.getSemesterWeek();
		User user = message.getUser();
		
		if(message.getUser() instanceof Admin && message.getUser().actsAsAdmin()) {

			SemesterListManager.addSemesterWeekForUser(modelStore, 
													   SemesterListModelAdmin.class,
					                                   semesterId, 
					                                   semesterWeek, 
					                                   user);

		}else {

			SemesterListManager.addSemesterWeekForUser(modelStore, 
													   SemesterListModelTeacher.class,
					                                   semesterId, 
					                                   semesterWeek, 
					                                   user);
		}
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSemesterWeekMessage message) {
		T.call(this);

		ScheduleUpdater.updateSchedulesForUser(modelStore, message.getSemesterId(), message.getUser());
	}

}
