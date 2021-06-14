package ca.aquiletour.core.pages.queue.student.handlers;

import ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.student.messages.ShowQueueMessageStudent;
import ca.aquiletour.core.pages.queue.student.views.QueueViewStudent;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.system.trace.T;

public class ShowQueueHandlerStudent extends ShowQueueHandler<QueueViewStudent, ShowQueueMessageStudent> {

	@Override
	protected Class<? extends QueueView> queueViewClass() {
		T.call(this);

		return QueueViewStudent.class;
	}

}
