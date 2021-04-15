package ca.aquiletour.core.pages.group_list.views;

import ca.aquiletour.core.pages.group_list.models.GroupDescription;
import ca.ntro.core.mvc.NtroView;

public interface GroupDescriptionView extends NtroView {

	void displayGroupDescription(GroupDescription groupDescription);

}
