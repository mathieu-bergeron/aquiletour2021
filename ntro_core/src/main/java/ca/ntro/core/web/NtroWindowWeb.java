package ca.ntro.core.web;

import ca.ntro.core.mvc.NtroWindow;

public abstract class NtroWindowWeb extends NtroWindow {
	
	public abstract void writeHtml(StringBuilder out);

}
