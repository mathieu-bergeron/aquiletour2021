package ca.aquiletour.core.pages.queue.teacher.handlers;

import ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler;
import ca.aquiletour.core.pages.queue.teacher.messages.ShowQueueMessageTeacher;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.system.trace.T;

public class ShowTeacherQueueHandler extends ShowQueueHandler<QueueViewTeacher, ShowQueueMessageTeacher> {

	@Override
	protected Class<? extends QueueView> queueViewClass() {
		T.call(this);
		
		return QueueViewTeacher.class;
	}
}
