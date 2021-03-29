package ca.aquiletour.core.pages.git.commit_list;



import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.git.values.Commit;
import ca.ntro.core.mvc.ModelViewSubViewHandler;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.trace.T;

public class CommitListViewModel extends ModelViewSubViewHandler<CommitListModel, CommitListView>  {
	
	@Override
	protected void handle(CommitListModel model, CommitListView view, ViewLoader subViewLoader) {
		T.call(this);
		view.displayCommitList(model);
		
		for(Commit commit : model.getCommits()) {

			CommitView commitView = (CommitView) subViewLoader.createView();
			
			commitView.displayCommit(commit);
			
			view.appendCommit(commit, commitView);
		}
	}
}
