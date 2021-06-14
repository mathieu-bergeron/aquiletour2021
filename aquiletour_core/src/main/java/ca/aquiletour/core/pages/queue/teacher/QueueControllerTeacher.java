package ca.aquiletour.core.pages.queue.teacher;

import ca.aquiletour.core.pages.queue.QueueController;
import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.messages.ShowQueueMessage;
import ca.aquiletour.core.pages.queue.teacher.handlers.QueueViewModelTeacher;
import ca.aquiletour.core.pages.queue.teacher.handlers.ShowTeacherQueueHandler;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowQueueMessageTeacher;
import ca.aquiletour.core.pages.queue.teacher.views.AppointmentViewTeacher;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;

public  class QueueControllerTeacher extends QueueController {

	@Override
	protected void onChangeContext(NtroContext<?,?> previousContext, NtroContext<?,?> context) {
		T.call(this);

		QueueViewTeacher view = (QueueViewTeacher) getView();
		
		if(view != null) {
			view.onContextChange(context);
		}
	}

	@Override
	protected Class<? extends QueueView> viewClass() {
		T.call(this);

		return QueueViewTeacher.class;
	}

	@Override
	protected QueueViewModel<?> viewModel() {
		T.call(this);
		
		return new QueueViewModelTeacher();
	}

	@Override 
	protected ShowQueueHandler<?, ?> showHandler() {
		T.call(this);
		
		return new ShowTeacherQueueHandler();
	}

	@Override
	protected Class<? extends ShowQueueMessage> showMessageClass() {
		T.call(this);
		
		return ShowQueueMessageTeacher.class;
	}

	@Override
	protected Class<? extends AppointmentView> subViewClass() {
		T.call(this);
		
		return AppointmentViewTeacher.class;
	}
}
