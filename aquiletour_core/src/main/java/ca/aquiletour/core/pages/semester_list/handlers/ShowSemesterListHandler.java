package ca.aquiletour.core.pages.semester_list.handlers;

import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.semester_list.messages.ShowSemesterListMessage;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.ntro.core.mvc.ParentViewMessageHandler;
import ca.ntro.core.system.trace.T;

public abstract class ShowSemesterListHandler extends ParentViewMessageHandler<RootView,
                                                                      SemesterListView,
                                                                      ShowSemesterListMessage> {

	@Override
	protected void handle(RootView parentView, 
			              SemesterListView currentView, 
			              ShowSemesterListMessage message) {
		T.call(this);
		
		parentView.showSemesterList(semesterListViewClass(), currentView);
	}
	
	protected abstract Class<? extends SemesterListView> semesterListViewClass();

}
