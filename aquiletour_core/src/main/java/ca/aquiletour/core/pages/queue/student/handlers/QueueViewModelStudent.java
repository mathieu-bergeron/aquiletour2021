package ca.aquiletour.core.pages.queue.student.handlers;

import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.models.QueueSettings;
import ca.aquiletour.core.pages.queue.student.views.AppointmentViewStudent;
import ca.aquiletour.core.pages.queue.student.views.QueueViewStudent;
import ca.aquiletour.core.pages.queue.views.AppointmentView;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class QueueViewModelStudent extends QueueViewModel<QueueViewStudent> {

	@Override
	protected void handle(QueueModel model, QueueViewStudent view, ViewLoader subViewLoader) {
		T.call(this);
		super.handle(model, view, subViewLoader);
		
		String queueMessage = QueueSettings.removeSettingsFromQueueMessage(model.getMainSettings().getQueueMessage().getValue());
		
		if(queueMessage != null 
				&& !queueMessage.isEmpty()) {
			
			view.displayQueueMessage(queueMessage);
		}
	}
}
