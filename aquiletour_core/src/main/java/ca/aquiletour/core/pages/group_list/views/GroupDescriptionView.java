package ca.aquiletour.core.pages.group_list.views;

import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.pages.group_list.models.GroupDescription;
import ca.aquiletour.core.views.ItemView;

public interface GroupDescriptionView extends ItemView {

	void displayGroupDescription(GroupDescription groupDescription);

	void clearStudents();
	void appendStudent(StudentDescription student);

}
