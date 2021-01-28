package ca.ntro.web.dom;


public interface HtmlElement {
	
	void appendHtml(String html);
	void appendElement(HtmlElement element);
	void text(String newText);
	void addEventListener(String event, HtmlEventListener listener);
	HtmlElements children(String cssQuery);
	void setAttribute(String name, String value);

}
