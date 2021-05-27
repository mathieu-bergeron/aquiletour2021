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
	
	private HtmlElement itemContainer;
	private BootstrapDropdown semesterDropdown;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		HtmlElement semesterDropdownHead = getRootElement().find("#semester-dropdown-head").get(0);
		HtmlElement semesterDropdownTail = getRootElement().find("#semester-dropdown-tail").get(0);
		itemContainer = this.getRootElement().find("#item-container").get(0);
		
		MustNot.beNull(semesterDropdownHead);
		MustNot.beNull(semesterDropdownTail);
		MustNot.beNull(itemContainer);
		
		semesterDropdownHead.setAttribute("id", "semester-dropdown-head-" + Ntro.introspector().getSimpleNameForClass(getClass()));
		semesterDropdownTail.setAttribute("id", "semester-dropdown-tail-" + Ntro.introspector().getSimpleNameForClass(getClass()));

		semesterDropdown = new BootstrapDropdown(semesterDropdownHead, semesterDropdownTail);
	}

	@Override
	public void insertIntoSemesterDropdown(int index, String semesterId, String href, String text) {
		T.call(this);

		semesterDropdown.insert(index, semesterId, href, text);
	}

	@Override
	public void appendToSemesterDropdown(String semesterId, String href, String text) {
		T.call(this);

		semesterDropdown.append(semesterId, href, text);
	}

	@Override
	public void selectSemester(String semesterId) {
		T.call(this);
		
		semesterDropdown.select(semesterId);
	}

	@Override
	public void identifyCurrentSemester(String semesterId) {
		T.call(this);
	}

	@Override
	public void appendItem(IV itemView) {

		NtroViewWeb itemViewWeb = (NtroViewWeb) itemView;
		
		itemContainer.appendElement(itemViewWeb.getRootElement());
	}

	@Override
	public void clearItems() {
		T.call(this);
		
		itemContainer.removeChildrenFromDocument();
		
	}
}
