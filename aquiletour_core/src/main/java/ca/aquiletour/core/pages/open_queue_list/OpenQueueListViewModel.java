package ca.aquiletour.core.pages.open_queue_list;

import java.util.List;

import ca.aquiletour.core.pages.open_queue_list.values.OpenQueue;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class OpenQueueListViewModel extends ModelViewSubViewHandler<OpenQueueListModel, OpenQueueListView> {

	@Override
	protected void handle(OpenQueueListModel model, OpenQueueListView view, ViewLoader subViewLoader) {
		
		model.getQueues().observe(new ListObserver<OpenQueue>() {
			
			@Override
			public void onItemRemoved(int index, OpenQueue item) {
				T.call(this);

				view.deleteQueue(item.getId());
			}
			
			@Override
			public void onItemUpdated(int index, OpenQueue item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, OpenQueue item) {
				T.call(this);

				OpenQueueView queueSummaryView = (OpenQueueView) subViewLoader.createView();
				queueSummaryView.displaySummary(item);
				
				view.appendQueue(item.getId(), queueSummaryView);
			}
			
			@Override
			public void onDeleted(List<OpenQueue> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<OpenQueue> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<OpenQueue> oldValue, List<OpenQueue> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
			}
		});
	}

}
