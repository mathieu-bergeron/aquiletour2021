package ca.ntro.web.dom;

import ca.ntro.core.system.trace.T;

public abstract class HtmlElement {

<<<<<<< HEAD
	void appendHtml(String html);
	void appendElement(HtmlElement element);
	void text(String newText);
	void addEventListener(String event, HtmlEventListener listener);
	HtmlElements children(String cssQuery);
	HtmlElements find(String cssQuery);
	void setAttribute(String name, String value);
	void clearChildren();
	void remove();

=======
	public abstract void appendHtml(String html);
	public abstract void appendElement(HtmlElement element);
	public abstract void text(String newText);
	public abstract void addEventListener(String event, HtmlEventListener listener);
	public abstract HtmlElements children(String cssQuery);
	public abstract HtmlElements find(String cssQuery);
	public abstract void setAttribute(String name, String value);

	public void clearChildren() {
		T.call(this);

		for (int i = 0; i < children("*").size(); i++) {
			HtmlElement child = children("*").get(i);
			child.remove();
		}
	}

	public abstract void remove();
	public abstract void value(String value);
	public abstract String getValue();
>>>>>>> main
}
