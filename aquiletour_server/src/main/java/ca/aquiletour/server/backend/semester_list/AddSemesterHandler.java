package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.pages.semester_list.messages.AddSemesterMessage;
import ca.aquiletour.server.backend.course_list.CourseListUpdater;
import ca.aquiletour.server.backend.group_list.GroupListUpdater;
import ca.ntro.backend.BackendMessageHandler;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;

public class AddSemesterHandler extends BackendMessageHandler<AddSemesterMessage> {

	@Override
	public void handleNow(ModelStoreSync modelStore, AddSemesterMessage message) {
		T.call(this);
		
		SemesterListUpdater.addSemesterForUser(modelStore, message.getSemesterId(), message.getUser());
	}

	@Override
	public void handleLater(ModelStoreSync modelStore, AddSemesterMessage message) {
		T.call(this);
		
		CourseListUpdater.addSemesterForUser(modelStore, message.getSemesterId(), message.getUser());
		GroupListUpdater.addSemesterForUser(modelStore, message.getSemesterId(), message.getUser());
	}

}
