package ca.aquiletour.core.views.widgets;

public interface SemesterDropdown {

	void insertIntoSemesterDropdown(int index, String semesterId, String href, String text);
	void appendToSemesterDropdown(String semesterId, String href, String text);
	void selectSemester(String semesterId);

}
