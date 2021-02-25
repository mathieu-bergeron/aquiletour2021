package ca.aquiletour.core.pages.queue.teacher;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.QueueViewModel;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueHandler;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.ntro.core.system.trace.T;

public  class TeacherQueueController extends QueueController {

	@Override
	protected Class<? extends QueueView> viewClass() {
		T.call(this);

		return TeacherQueueView.class;
	}


	@Override
	protected void installParentViewMessageHandler() {
		T.call(this);
		
		addControllerMessageHandler(ShowTeacherQueueMessage.class, new ShowTeacherQueueHandler());
		addSubViewLoader(TeacherAppointmentView.class, currentContext().getLang());
		
		addModelViewSubViewHandler(TeacherAppointmentView.class, new QueueViewModel()); //TODO doesn't do anything
	}

}
