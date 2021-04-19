package ca.aquiletour.web.widgets;

import ca.ntro.core.system.trace.T;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;

public class BootstrapDropdown {
	
	private HtmlElement dropdownHead;
	private HtmlElement dropdownTail;
	
	public BootstrapDropdown(HtmlElement dropdownHead, HtmlElement dropdownTail) {
		T.call(this);
		
		this.dropdownHead = dropdownHead;
		this.dropdownTail = dropdownTail;
	}

	public void insert(int index, String id, String href, String text) {
		T.call(this);
		
		HtmlElement link = createLink(id, href, text);

		if(index >= 0 && index < dropdownTail.children("*").size()){
			
			HtmlElement anchorElement = dropdownTail.children("*").get(index);
			link.insertBefore(anchorElement);

		} else {
			
			dropdownTail.appendElement(link);
		}
		
	}

	public void select(String id) {
		T.call(this);

		HtmlElements links = dropdownTail.find("#" + id);
		
		if(links != null) {

			HtmlElement link = links.get(0);
			
			if(link != null) {

				clearSelection();

				String linkId = link.getAttribute("id");
				String linkHref = link.getAttribute("href");
				String linkText = link.text();
				
				dropdownHead.setAttribute("link-id", linkId);
				dropdownHead.setAttribute("href", linkHref);
				dropdownHead.text(linkText);
				
				link.removeFromDocument();
			}
		}
	}

	private void clearSelection() {
		T.call(this);
		
		String linkId = dropdownHead.getAttribute("link-id");
		
		if(linkId != null && !linkId.isEmpty()) {
			
			String headHref = dropdownHead.getAttribute("href");
			String headText = dropdownHead.text();
			
			insert(0, linkId, headHref, headText);
			
			dropdownHead.setAttribute("href", "#");
			dropdownHead.text("");
			dropdownHead.setAttribute("link-id", "");
		}
	}
	
	private HtmlElement createLink(String id, String href, String text) {
		T.call(this);
		
		HtmlElement semesterLink = dropdownHead.createElement("<a></a");
		semesterLink.setAttribute("class", "dropdown-item");
		semesterLink.setAttribute("id", id);
		semesterLink.setAttribute("href", href);
		semesterLink.text(text);
		
		return semesterLink;
	}

	public void clear() {
		T.call(this);
		
		dropdownHead.removeChildrenFromDocument();
		dropdownTail.removeChildrenFromDocument();
	}

	public int size() {
		T.call(this);
		
		// FIXME
		return 0;
	}

}
