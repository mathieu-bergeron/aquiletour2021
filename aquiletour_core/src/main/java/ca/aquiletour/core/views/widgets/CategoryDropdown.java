package ca.aquiletour.core.views.widgets;

public interface CategoryDropdown {

	void insertIntoCategoryDropdown(int index, String categoryId, String href, String text);

	void appendToCategoryDropdown(String categoryId, String href, String text);

	void selectCategory(String categoryId);

	void updateCategory(String categoryId, String href, String text);

}
