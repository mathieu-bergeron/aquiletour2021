package ca.aquiletour.core.pages.queue.student;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queue.QueueViewModel;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueHandler;
import ca.aquiletour.core.pages.queue.student.messages.ShowStudentQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueHandler;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowTeacherQueueMessage;
import ca.ntro.core.system.trace.T;

public  class StudentQueueController extends QueueController {

	@Override
	protected Class<? extends QueueView> viewClass() {
		T.call(this);

		return StudentQueueView.class;
	}


	@Override
	protected void installParentViewMessageHandler() {
		T.call(this);
		
		addControllerMessageHandler(ShowStudentQueueMessage.class, new ShowStudentQueueHandler());
		addSubViewLoader(StudentAppointmentView.class, context().lang());
		
		addModelViewSubViewHandler(StudentAppointmentView.class, new QueueViewModel()); //TODO doesn't do anything
	}

}
