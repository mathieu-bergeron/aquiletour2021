package ca.aquiletour.web.pages.group_list;


import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.pages.group_list.models.GroupDescription;
import ca.aquiletour.core.pages.group_list.views.GroupDescriptionView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

import static ca.ntro.assertions.Factory.that;

public class GroupDescriptionViewWeb extends NtroViewWeb implements GroupDescriptionView {
	
	private HtmlElement studentList;
	private HtmlElements addGroupIdToText;
	
	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		studentList = this.getRootElement().find("#student-list").get(0);
		addGroupIdToText = this.getRootElement().find(".add-group-id-to-text");

		MustNot.beNull(studentList);

		Ntro.verify(that(addGroupIdToText.size() > 0).isTrue());
	}

	@Override
	public void displayGroupDescription(GroupDescription groupDescription) {
		T.call(this);
		
		addGroupIdToText.forEach(e -> {
			String text = e.text();
			text += groupDescription.getGroupId();
			e.text(text);
		});
	}

	@Override
	public void clearStudents() {
		T.call(this);
		
		studentList.removeChildrenFromDocument();
	}

	@Override
	public void appendStudent(StudentDescription student) {
		T.call(this);
		
		HtmlElement studentLi = studentList.createElement("<li class=\"list-group-item\"></li>");
		studentLi.text(student.getName() + " " + student.getSurname() + " (" + student.getId() + ")");
		
		studentList.appendElement(studentLi);
	}
}
