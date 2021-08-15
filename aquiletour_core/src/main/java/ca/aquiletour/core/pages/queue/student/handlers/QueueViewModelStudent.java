package ca.aquiletour.core.pages.queue.student.handlers;

import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.models.QueueSettings;
import ca.aquiletour.core.pages.queue.student.views.QueueViewStudent;
import ca.ntro.core.models.listeners.ValueObserver;
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
		
		model.getTeacherName().removeObservers();
		model.getTeacherName().observe(new ValueObserver<String>() {
			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(String value) {
				T.call(this);

				view.displayTeacherName(value);
			}
			
			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);

				view.displayTeacherName(value);
			}
		});
	}
}
