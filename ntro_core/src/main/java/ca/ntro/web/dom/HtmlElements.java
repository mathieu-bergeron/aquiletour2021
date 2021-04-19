package ca.ntro.web.dom;


public interface HtmlElements {
	
	HtmlElement get(int index);
	int size();
	
	void forEach(HtmlElementLambda lambda);
	
}
