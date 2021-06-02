package ca.aquiletour.core.views;

import ca.aquiletour.core.views.widgets.SemesterDropdown;
import ca.ntro.core.mvc.NtroView;

public interface ListView<IV extends ItemView> extends NtroView, SemesterDropdown {
	
	void appendItem(IV itemView);

	void displayActiveSemesters(String semesterId);

	void clearItems();
}
