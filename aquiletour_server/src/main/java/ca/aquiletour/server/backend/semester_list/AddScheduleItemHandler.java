package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.semester_list.messages.AddScheduleItemMessage;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddScheduleItemHandler extends BackendMessageHandler<AddScheduleItemMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddScheduleItemMessage message) {
		T.call(this);
		
		SemesterListUpdater.addScheduleItemForUser(modelStore, 
				                                   message.getSemesterId(), 
				                                   message.getScheduleItem(), 
				                                   message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddScheduleItemMessage message) {
		T.call(this);
	}

}
