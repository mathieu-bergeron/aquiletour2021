package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.pages.git.commit_list.CommitListModel;

public class OnNewCommits extends StudentExerciseMessage {

	private CommitListModel commitList;

	public CommitListModel getCommitList() {
		return commitList;
	}

	public void setCommitList(CommitListModel commitList) {
		this.commitList = commitList;
	}
}
