package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.semester_list.messages.AddSemesterMessage;
import ca.ntro.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddSemesterHandler extends BackendMessageHandler<AddSemesterMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSemesterMessage message) {
		T.call(this);
		
		String semesterId = message.getSemesterId();
		User user = message.getUser();
		
		SemesterListUpdater.addSemesterForUser(modelStore, semesterId, user);
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSemesterMessage message) {
		T.call(this);
		
	}

}
