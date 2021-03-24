package ca.aquiletour.core.pages.git.commit_list;


import java.util.List;

import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.values.Appointment;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CommitListViewModel extends ModelViewSubViewHandler<CommitListModel, CommitListView>  {
	
	@Override
	protected void handle(CommitListModel model, CommitListView view, ViewLoader subViewLoader) {
		T.call(this);
		view.displayCommitList(model);
		
		model.getCommits().observe(new ListObserver<Commit>() {

			@Override
			public void onValueChanged(List<Commit> oldValue, List<Commit> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<Commit> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<Commit> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, Commit item) {
				// TODO Auto-generated method stub
				T.call(this);
				T.here();
				
				CommitView commitView = (CommitView) subViewLoader.createView();
				
				commitView.displayCommit(item);
				
				view.appendCommit(item, commitView);
			}

			@Override
			public void onItemUpdated(int index, Commit item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, Commit item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
