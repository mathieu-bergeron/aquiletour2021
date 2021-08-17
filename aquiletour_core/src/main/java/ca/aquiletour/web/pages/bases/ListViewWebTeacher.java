package ca.aquiletour.web.pages.bases;

import ca.aquiletour.core.views.ItemView;
import ca.aquiletour.core.views.ListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;

public abstract class ListViewWebTeacher<IV extends ItemView> extends ListViewWeb<IV> implements ListView<IV> {
	
	private HtmlElement addItemButton;
	private HtmlElement modalTitle;
	private HtmlElement semesterIdInput;

	protected HtmlElement getAddItemButton() {
		return addItemButton;
	}
	
	protected HtmlElement getModelTitle() {
		return modalTitle;
	}

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);
		super.initializeViewWeb(context);

		addItemButton = getRootElement().find("#add-item-button").get(0);
		modalTitle = getRootElement().find("#modal-title").get(0);
		semesterIdInput = this.getRootElement().find("#semester-id-input").get(0);
		
		MustNot.beNull(addItemButton);
		MustNot.beNull(modalTitle);
		MustNot.beNull(semesterIdInput);
	}

	@Override
	public void displayCurrentSemester(String semesterId) {
		T.call(this);
		super.displayCurrentSemester(semesterId);
		
		semesterIdInput.value(semesterId);
	}
}
