package ca.aquiletour.web.pages.bases;

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
	private BootstrapDropdown categoryDropdown;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		HtmlElement categoryDropdownHead = getRootElement().find("#semester-dropdown-head").get(0);
		HtmlElement categoryDropdownTail = getRootElement().find("#semester-dropdown-tail").get(0);
		itemContainer = this.getRootElement().find("#item-container").get(0);
		
		MustNot.beNull(categoryDropdownHead);
		MustNot.beNull(categoryDropdownTail);
		MustNot.beNull(itemContainer);
		
		categoryDropdownHead.setAttribute("id", "semester-dropdown-head-" + Ntro.introspector().getSimpleNameForClass(getClass()));
		categoryDropdownTail.setAttribute("id", "semester-dropdown-tail-" + Ntro.introspector().getSimpleNameForClass(getClass()));

		categoryDropdown = new BootstrapDropdown(categoryDropdownHead, categoryDropdownTail);
	}

	@Override
	public void insertIntoCategoryDropdown(int index, String categoryId, String href, String text) {
		T.call(this);

		categoryDropdown.insert(index, categoryId, href, text);
	}

	@Override
	public void appendToCategoryDropdown(String categoryId, String href, String text) {
		T.call(this);

		categoryDropdown.append(categoryId, href, text);
	}

	@Override
	public void selectCategory(String semesterId) {
		T.call(this);
		
		categoryDropdown.select(semesterId);
	}

	@Override
	public void displayActiveSemesters(String semesterId) {
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
