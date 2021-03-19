package ca.aquiletour.core.pages.git;

import ca.aquiletour.core.pages.git.commit_list.CommitListModel;
import ca.aquiletour.core.pages.git.commit_list.CommitListViewModel;
import ca.aquiletour.core.pages.git.commit_list.CommitView;
import ca.aquiletour.core.pages.git.messages.ShowCommitListHandler;
import ca.aquiletour.core.pages.git.messages.ShowCommitListMessage;
import ca.aquiletour.core.pages.queue.QueueViewModel;
import ca.aquiletour.core.pages.queue.teacher.TeacherAppointmentView;
import ca.aquiletour.core.pages.root.RootController;
import ca.ntro.core.models.EmptyModelLoader;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroController;
import ca.ntro.core.system.trace.T;

public class CommitListController extends NtroController<RootController> {

	@Override
	protected void onCreate() {
		T.call(this);

		setViewLoader(CommitListView.class, "fr");
		setModelLoader(new EmptyModelLoader());

		addControllerMessageHandler(ShowCommitListMessage.class, new ShowCommitListHandler());
		addSubViewLoader(CommitView.class, currentContext().lang());
		addModelViewSubViewHandler(CommitView.class, new CommitListViewModel());
	}

	@Override
	protected void onChangeContext(NtroContext<?> previousContext) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onFailure(Exception e) {
		T.call(this);

	}

}
