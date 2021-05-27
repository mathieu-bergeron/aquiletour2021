package ca.aquiletour.core.pages.group_list.views;

import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.pages.group_list.models.GroupItem;
import ca.aquiletour.core.views.ItemView;

public interface GroupView extends ItemView {

	void displayGroupDescription(GroupItem groupDescription);

	void clearStudents();
	void appendStudent(StudentDescription student);

	void displayStudentsSummary(String summary);

}
