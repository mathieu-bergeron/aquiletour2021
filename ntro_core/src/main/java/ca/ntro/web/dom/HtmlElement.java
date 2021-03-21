package ca.ntro.web.dom;

import ca.ntro.core.system.trace.T;

public abstract class HtmlElement {

	public abstract HtmlElement createElement(String html);

	public abstract void appendHtml(String html);
	public abstract void appendElement(HtmlElement element);
	public abstract void insertBefore(HtmlElement element);
	public abstract void insertAfter(HtmlElement element);
	public abstract void text(String newText);

	public abstract void removeListeners();
	public abstract void addEventListener(String event, HtmlEventListener listener);

	public abstract HtmlElements children(String cssQuery);
	public abstract HtmlElements find(String cssQuery);

	public abstract String id();

	public abstract String getAttribute(String name);
	public abstract void setAttribute(String name, String value);

	public void clearChildren() {
		T.call(this);

		for (int i = 0; i < children("*").size(); i++) {
			HtmlElement child = children("*").get(0);
			child.removeFromDocument();
		}
	}

	public abstract void empty();
	public abstract void removeFromDocument();
	public abstract void deleteForever();
	public abstract void value(String value);
	public abstract String html();
	public abstract void html(String htmlString);
	public abstract String value();
	
	public abstract void show();
	public abstract void hide();
	
	public abstract void readFileFromInput(HtmlFileListener listener);

	public abstract void invoke(String method, Object[] objects);

	public abstract void trigger(String event);
}
