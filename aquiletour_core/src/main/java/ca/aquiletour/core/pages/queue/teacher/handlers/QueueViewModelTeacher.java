package ca.aquiletour.core.pages.queue.teacher.handlers;

import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class QueueViewModelTeacher extends QueueViewModel<QueueViewTeacher> {

	@Override
	protected void handle(QueueModel model, QueueViewTeacher view, ViewLoader subViewLoader) {
		T.call(this);

		super.handle(model, view, subViewLoader);
	}

}
