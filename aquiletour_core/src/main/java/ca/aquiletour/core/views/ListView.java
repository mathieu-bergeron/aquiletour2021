package ca.aquiletour.core.views;

import ca.aquiletour.core.views.widgets.CategoryDropdown;
import ca.ntro.core.mvc.NtroView;

public interface ListView<IV extends ItemView> extends NtroView, CategoryDropdown {
	
	void appendItem(IV itemView);

	void displayActiveSemesters(String semesterId);

	void clearItems();

}
