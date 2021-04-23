package ca.aquiletour.web.pages.group_list;


import ca.aquiletour.core.models.courses.group.StudentDescription;
import ca.aquiletour.core.pages.group_list.models.GroupItem;
import ca.aquiletour.core.pages.group_list.views.GroupView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

import static ca.ntro.assertions.Factory.that;

public class GroupViewWeb extends NtroViewWeb implements GroupView {
	
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
	public void displayGroupDescription(GroupItem groupDescription) {
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
		
		String liHtml = "<li class='list-group-item'>"
				+ "<div class='d-flex justify-content-start'>"
				+ "<div>"
				+ student.getName()
				+ " "  
				+ student.getSurname()
				+ " ("
				+ student.getId()
				+ ")"
				+ "</div>"
				+ "<div class='flex-fill' style='min-width:4rem;'>"
				+ "</div>"
				+ "<button class='btn btn-danger btn-sm' style='line-height:0px'>"
				+ "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-x\" viewBox=\"0 0 16 16\">\n"
				+ "  <path d=\"M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z\"/>\n"
				+ "</svg>"
				+ "</button>"
				+ "</div>"
				+ "</li>";
		
		HtmlElement studentLi = studentList.createElement(liHtml);
		studentList.appendElement(studentLi);
	}
}
