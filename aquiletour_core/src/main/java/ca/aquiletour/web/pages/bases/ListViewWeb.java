package ca.aquiletour.web.pages.bases;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.views.ItemView;
import ca.aquiletour.core.views.ListView;
import ca.aquiletour.web.widgets.BootstrapDropdown;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.mvc.NtroViewWeb;

public abstract class ListViewWeb<IV extends ItemView> extends NtroViewWeb implements ListView<IV> {
	
	private HtmlElement coursesContainer;
	private BootstrapDropdown semesterDropdown;
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

		HtmlElement semesterDropdownHead = getRootElement().find("#semester-dropdown-head").get(0);
		HtmlElement semesterDropdownTail = getRootElement().find("#semester-dropdown-tail").get(0);
		addItemButton = getRootElement().find("#add-item-button").get(0);
		modalTitle = getRootElement().find("#modal-title").get(0);
		coursesContainer = this.getRootElement().find("#courses-container").get(0);
		semesterIdInput = this.getRootElement().find("#semester-id-input").get(0);
		
		MustNot.beNull(semesterDropdownHead);
		MustNot.beNull(semesterDropdownTail);
		MustNot.beNull(addItemButton);
		MustNot.beNull(modalTitle);
		MustNot.beNull(coursesContainer);
		MustNot.beNull(semesterIdInput);
		
		semesterDropdownHead.setAttribute("id", "semester-dropdown-head-" + Ntro.introspector().getSimpleNameForClass(getClass()));
		semesterDropdownTail.setAttribute("id", "semester-dropdown-tail-" + Ntro.introspector().getSimpleNameForClass(getClass()));

		semesterDropdown = new BootstrapDropdown(semesterDropdownHead, semesterDropdownTail);
	}

	@Override
	public void insertIntoSemesterDropdown(int index, String semesterId) {
		T.call(this);
		
		semesterDropdown.insert(index, semesterId,  "?" + Constants.SEMESTER_URL_PARAM + "=" + semesterId, semesterId);
	}

	@Override
	public void selectSemester(String semesterId) {
		T.call(this);
		
		semesterDropdown.select(semesterId);
	}

	@Override
	public void identifyCurrentSemester(String semesterId) {
		T.call(this);
		
		semesterIdInput.value(semesterId);
	}
	
	@Override
	public void appendItem(IV itemView) {

		NtroViewWeb itemViewWeb = (NtroViewWeb) itemView;
		
		coursesContainer.appendElement(itemViewWeb.getRootElement());
	}

	@Override
	public void clearItems() {
		T.call(this);
		
		coursesContainer.removeChildrenFromDocument();
		
	}

}
