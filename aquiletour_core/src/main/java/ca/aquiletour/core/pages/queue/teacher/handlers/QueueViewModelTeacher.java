package ca.aquiletour.core.pages.queue.teacher.handlers;


import ca.aquiletour.core.Constants;
import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class QueueViewModelTeacher extends QueueViewModel<QueueViewTeacher> {
	
	private boolean isQueueOpen = false;

	@Override
	protected void handle(QueueModel model, QueueViewTeacher view, ViewLoader subViewLoader) {
		T.call(this);

		super.handle(model, view, subViewLoader);
		
		model.getMainSettings().getIsQueueOpen().observe(new ValueObserver<Boolean>() {
			@Override
			public void onDeleted(Boolean lastValue) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.updateIsQueueOpenCheckbox(Constants.ALL_COURSES_ID, false);
			}
			
			@Override
			public void onValue(Boolean value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.updateIsQueueOpenCheckbox(Constants.ALL_COURSES_ID, value);
			}
			
			@Override
			public void onValueChanged(Boolean oldValue, Boolean value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.updateIsQueueOpenCheckbox(Constants.ALL_COURSES_ID, value);
			}
		});
	}

	
	private void updateIsQueueOpen(QueueModel model, QueueViewTeacher view) {
		T.call(this);
		
		boolean isQueueOpenNow = model.isQueueOpen();
		
		System.out.println(isQueueOpenNow);
		
		if(isQueueOpen != isQueueOpenNow) {
			isQueueOpen = isQueueOpenNow;
			view.displayIfQueueOpen(isQueueOpen);
		}
	}

}
