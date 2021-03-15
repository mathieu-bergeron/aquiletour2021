package ca.aquiletour.core.pages.queues;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.queues.values.QueueSummary;
import ca.aquiletour.core.pages.users.UserView;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class QueuesViewModel extends ModelViewSubViewHandler<QueuesModel, QueuesView> {

	@Override
	protected void handle(QueuesModel model, QueuesView view, ViewLoader subViewLoader) {
		// TODO Auto-generated method stub
		model.getQueues().observe(new ListObserver<QueueSummary>() {
			
			@Override
			public void onItemRemoved(int index, QueueSummary item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, QueueSummary item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, QueueSummary item) {
				// TODO Auto-generated method stub
				T.call(this);
				T.here();
				QueueSummaryView queueSummaryView = (QueueSummaryView) subViewLoader.createView();
				queueSummaryView.displaySummary(item);
				
				view.appendQueue(queueSummaryView);
			}
			
			@Override
			public void onDeleted(List<QueueSummary> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<QueueSummary> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<QueueSummary> oldValue, List<QueueSummary> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
