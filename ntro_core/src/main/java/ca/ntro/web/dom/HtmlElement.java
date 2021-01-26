package ca.ntro.web.dom;


public interface HtmlElement {
	
	void appendHtml(String html);
	void text(String newText);
	void addEventListener(String event, HtmlEventListener listener);

}
