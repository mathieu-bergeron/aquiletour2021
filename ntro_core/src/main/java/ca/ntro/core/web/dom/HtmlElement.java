package ca.ntro.core.web.dom;


public interface HtmlElement {
	
	void text(String newText);
	void addEventListener(String event, HtmlEventListener listener);

}
