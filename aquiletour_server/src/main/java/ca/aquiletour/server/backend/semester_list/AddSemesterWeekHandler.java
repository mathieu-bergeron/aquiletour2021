package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterWeekMessage;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddSemesterWeekHandler extends BackendMessageHandler<AddSemesterWeekMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSemesterWeekMessage message) {
		T.call(this);
		
		String semesterId = message.getSemesterId();
		SemesterWeek semesterWeek = message.getSemesterWeek();
		User user = message.getUser();
		
		SemesterListUpdater.addSemesterWeekForUser(modelStore, semesterId, semesterWeek, user);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSemesterWeekMessage message) {
		T.call(this);
	}

}
