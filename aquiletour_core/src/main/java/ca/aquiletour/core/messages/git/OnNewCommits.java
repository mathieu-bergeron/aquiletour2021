package ca.aquiletour.core.messages.git;

import ca.aquiletour.core.messages.git.base.GitApiExerciseMessage;
import ca.aquiletour.core.pages.git.commit_list.models.CommitListModel;

public class OnNewCommits extends GitApiExerciseMessage {

	private CommitListModel commitList;

	public CommitListModel getCommitList() {
		return commitList;
	}

	public void setCommitList(CommitListModel commitList) {
		this.commitList = commitList;
	}
}
