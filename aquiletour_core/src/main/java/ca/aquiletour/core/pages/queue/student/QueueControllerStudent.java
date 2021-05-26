package ca.aquiletour.core.pages.queue.student;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queue.student.handlers.QueueViewModelStudent;
import ca.aquiletour.core.pages.queue.student.handlers.ShowQueueHandlerStudent;
import ca.aquiletour.core.pages.queue.student.messages.ShowQueueMessageStudent;
import ca.aquiletour.core.pages.queue.student.views.AppointmentViewStudent;
import ca.aquiletour.core.pages.queue.student.views.QueueViewStudent;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.system.trace.T;

public  class QueueControllerStudent extends QueueController {

	@Override
	protected Class<? extends QueueView> viewClass() {
		T.call(this);

		return QueueViewStudent.class;
	}

	@Override
	protected QueueViewModel<?> viewModel() {
		T.call(this);

		return new QueueViewModelStudent();
	}

	@Override 
	protected ShowQueueHandler<?, ?> showHandler() {
		T.call(this);

		return new ShowQueueHandlerStudent();
	}

	@Override
	protected Class<? extends ShowQueueMessage> showMessageClass() {
		T.call(this);

		return ShowQueueMessageStudent.class;
	}

	@Override
	protected Class<? extends AppointmentView> subViewClass() {
		T.call(this);

		return AppointmentViewStudent.class;
	}
}
