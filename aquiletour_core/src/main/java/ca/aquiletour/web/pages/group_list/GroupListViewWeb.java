package ca.aquiletour.web.pages.group_list;

import ca.aquiletour.core.pages.group_list.views.GroupDescriptionView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.web.pages.bases.ListViewWeb;
import ca.ntro.core.system.trace.T;

public class GroupListViewWeb extends ListViewWeb<GroupDescriptionView> implements GroupListView {

	@Override
	public void identifyCurrentSemester(String semesterId) {
		T.call(this);
		super.identifyCurrentSemester(semesterId);

		String text = "Ajouter un groupe";

		getAddItemButton().text(text);
		getModelTitle().text(text);
	}
}
