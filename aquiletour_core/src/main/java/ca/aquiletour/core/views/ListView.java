package ca.aquiletour.core.views;

import ca.ntro.core.mvc.NtroView;

public interface ListView<IV extends ItemView> extends NtroView {
	
	void appendItem(IV itemView);

	void insertIntoSemesterDropdown(int index, String semesterId);
	void appendToSemesterDropdown(String semesterId);
	void selectSemester(String semesterId);
	void identifyCurrentSemester(String semesterId);
	void clearItems();
	
	

}
