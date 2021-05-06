package ca.aquiletour.core.pages.git.commit_list;

import ca.aquiletour.core.pages.git.values.Commit;
import ca.ntro.core.mvc.NtroView;

public interface CommitListView extends NtroView  {
	
	void appendCommit(Commit commit, CommitView commitView);
	void displayCommitList(CommitListModel commitListModel, long deadline);
}
