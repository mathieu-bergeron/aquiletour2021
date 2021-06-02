package ca.aquiletour.core.views.widgets;

public interface SemesterDropdown {

	void insertIntoCategoryDropdown(int index, String semesterId, String href, String text);
	void appendToCategoryDropdown(String semesterId, String href, String text);
	void selectCategory(String semesterId);

}
