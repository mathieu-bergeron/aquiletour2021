package ca.aquiletour.web.pages.group_list;


import ca.aquiletour.core.pages.group_list.models.GroupDescription;
import ca.aquiletour.core.pages.group_list.views.GroupDescriptionView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.mvc.NtroViewWeb;

public class GroupDescriptionViewWeb extends NtroViewWeb implements GroupDescriptionView {
	
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
	}

	@Override
	public void displayGroupDescription(GroupDescription groupDescription) {
		T.call(this);
		
	}
}
