package ca.ntro.web.dom;

import java.util.Map;

import ca.ntro.core.system.trace.T;

public abstract class HtmlElement {

	public abstract HtmlElement createTag(String tagName);
	public abstract HtmlElement createElement(String html);

	public abstract void animate(Map<String,Object> properties, long duration, AnimationListener listener);

	public abstract void appendHtml(String html);
	public abstract void appendElement(HtmlElement element);
	public abstract void insertBefore(HtmlElement element);
	public abstract void insertAfter(HtmlElement element);
	public abstract void text(String newText);
	public abstract String text();

	public abstract void removeListeners();
	public abstract void addEventListener(String event, HtmlEventListener listener);

	public abstract HtmlElements children(String cssQuery);
	public abstract HtmlElements find(String cssQuery);

	public abstract String id();

	public abstract String getAttribute(String name);
	public abstract void setAttribute(String name, String value);
	public abstract void removeAttribute(String name);

	public void removeChildrenFromDocument() {
		T.call(this);

		HtmlElements children = children("*");

		for (int i = 0; i < children.size(); i++) {
			HtmlElement child = children.get(i);
			child.removeFromDocument();
		}
	}

	public void deleteChildrenForever() {
		T.call(this);
		
		HtmlElements children = children("*");

		for (int i = 0; i < children.size(); i++) {
			HtmlElement child = children.get(i);
			child.deleteForever();
		}
	}

	public abstract void empty();
	public abstract void removeFromDocument();
	public abstract void deleteForever();
	public abstract void value(String value);
	public abstract String html();
	public abstract void html(String htmlString);
	public abstract String value();
	
	public abstract void readFileFromInput(HtmlFileListener listener);

	public abstract void invoke(String method, Object[] objects);

	public abstract void trigger(String event);
	
	
	
	public void show() {
		String styleString = getStyleString();

		styleString = styleString.replace("display:none !important;", "");

		setAttribute("style", styleString);
	}

	public void hide() {
		String styleString = getStyleString();
		
		if(!styleString.contains("display:none !important;")){
			styleString += " display:none !important;";
		}
		
		setAttribute("style", styleString);
	}

	private String getStyleString() {
		String styleString = getAttribute("style");
		if(styleString == null) {
			styleString = "";
		}
		return styleString;
	}

	public abstract void css(String property, String value);
	public abstract void css(String property, double value);

}
