package ca.aquiletour.core.pages.git;

import ca.aquiletour.core.pages.git.commit_list.CommitListViewModel;
import ca.aquiletour.core.pages.git.commit_list.CommitView;
import ca.aquiletour.core.pages.git.messages.ShowCommitListHandler;
import ca.aquiletour.core.pages.git.messages.ShowCommitListMessage;
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
