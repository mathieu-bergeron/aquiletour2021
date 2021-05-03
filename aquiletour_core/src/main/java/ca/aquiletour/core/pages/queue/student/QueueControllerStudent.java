package ca.aquiletour.core.pages.queue.student;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueHandler;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueHandler;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.system.trace.T;

public  class QueueControllerStudent extends QueueController {

	@Override
	protected Class<? extends QueueView> viewClass() {
		T.call(this);

		return StudentQueueView.class;
	}


	@Override
	protected void installParentViewMessageHandler() {
		T.call(this);
		
		addControllerMessageHandler(ShowStudentQueueMessage.class, new ShowStudentQueueHandler());
		addSubViewLoader(AppointmentViewStudent.class, context().lang());
		
		addModelViewSubViewHandler(AppointmentViewStudent.class, new QueueViewModel()); //TODO doesn't do anything
	}

}
