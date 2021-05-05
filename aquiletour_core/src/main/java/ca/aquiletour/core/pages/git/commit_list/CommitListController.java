package ca.aquiletour.core.pages.git.commit_list;

import ca.aquiletour.core.messages.git.OnNewCommits;
import ca.aquiletour.core.pages.git.commit_list.messages.OnNewCommitsHandler;
import ca.aquiletour.core.pages.git.commit_list.messages.ShowCommitListHandler;
import ca.aquiletour.core.pages.git.commit_list.messages.ShowCommitListMessage;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class CommitListController extends NtroController<RootController> {

	@Override
	protected void onCreate(NtroContext<?> context) {
		T.call(this);

		setViewLoader(CommitListView.class, "fr");

		setModelLoader(new EmptyModelLoader());
		setSubModelLoader(new EmptyModelLoader());

		addControllerMessageHandler(ShowCommitListMessage.class, new ShowCommitListHandler());
		addModelViewSubViewMessageHandler(CommitView.class, OnNewCommits.class, new OnNewCommitsHandler());
		
		addSubViewLoader(CommitView.class, context.lang());
		addModelSubModelViewSubViewHandler(CommitView.class, new CommitListViewModel());
	}


	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}

	@Override
	protected void onChangeContext(NtroContext<?> oldContext, NtroContext<?> context) {
		// TODO Auto-generated method stub
		
	}

}
