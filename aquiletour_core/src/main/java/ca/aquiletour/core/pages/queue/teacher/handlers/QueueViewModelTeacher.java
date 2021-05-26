package ca.aquiletour.core.pages.queue.teacher.handlers;

import java.util.Map;

import ca.aquiletour.core.pages.queue.handlers.QueueViewModel;
import ca.aquiletour.core.pages.queue.models.QueueModel;
import ca.aquiletour.core.pages.queue.teacher.views.QueueViewTeacher;
import ca.ntro.core.models.listeners.MapObserver;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class QueueViewModelTeacher extends QueueViewModel<QueueViewTeacher> {
	
	private boolean isQueueOpen = false;

	@Override
	protected void handle(QueueModel model, QueueViewTeacher view, ViewLoader subViewLoader) {
		T.call(this);

		super.handle(model, view, subViewLoader);
		
		observeIsOpenByCourseId(model, view);
		observeIsOpenByGroupId(model, view);
	}

	private void observeIsOpenByGroupId(QueueModel model, QueueViewTeacher view) {
		T.call(this);
		
		model.getIsOpenByGroupId().removeObservers();
		model.getIsOpenByGroupId().observe(new MapObserver<Boolean>() {

			@Override
			public void onValueChanged(Map<String, Boolean> oldValue, Map<String, Boolean> value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onValue(Map<String, Boolean> value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onDeleted(Map<String, Boolean> lastValue) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onClearEntries() {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onEntryAdded(String key, Boolean value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}

			@Override
			public void onEntryRemoved(String key, Boolean value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}
		});
	}

	private void observeIsOpenByCourseId(QueueModel model, QueueViewTeacher view) {
		T.call(this);
		
		model.getIsOpenByCourseId().removeObservers();
		model.getIsOpenByCourseId().observe(new MapObserver<Boolean>() {
			@Override
			public void onEntryAdded(String key, Boolean value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.updateIsQueueOpenCheckbox(key, value);
			}

			@Override
			public void onClearEntries() {
				T.call(this);
				updateIsQueueOpen(model, view);
			}
			
			@Override
			public void onDeleted(Map<String, Boolean> lastValue) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}
			
			@Override
			public void onValue(Map<String, Boolean> value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}
			
			@Override
			public void onValueChanged(Map<String, Boolean> oldValue, Map<String, Boolean> value) {
				T.call(this);
				updateIsQueueOpen(model, view);
			}
			
			@Override
			public void onEntryRemoved(String key, Boolean value) {
				T.call(this);
				updateIsQueueOpen(model, view);
				view.updateIsQueueOpenCheckbox(key, false);
			}
		});
	}
	
	private void updateIsQueueOpen(QueueModel model, QueueViewTeacher view) {
		T.call(this);
		
		boolean isQueueOpenNow = model.isQueueOpen();
		
		if(isQueueOpen != isQueueOpenNow) {
			isQueueOpen = isQueueOpenNow;
			view.displayIfQueueOpen(isQueueOpen);
		}
	}

}
