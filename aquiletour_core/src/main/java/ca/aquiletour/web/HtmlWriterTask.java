package ca.aquiletour.web;

import ca.ntro.core.tasks.NtroTask;

public interface HtmlWriterTask extends NtroTask {

	void writeHtml(StringBuilder out);

}
