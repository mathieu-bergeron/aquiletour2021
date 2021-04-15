package ca.aquiletour.web.pages.dashboard.teacher;

import ca.aquiletour.core.messages.AddStudentCsvMessage;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.dom.HtmlFileListener;

// JSWEET: could not compile two embedded anonymous classes
public class CsvSubmitListener implements HtmlEventListener {
	
	private String courseId;
	private HtmlElement csvFileInput;
	
	public CsvSubmitListener(String courseId, HtmlElement csvFileInput) {
		this.courseId = courseId;
		this.csvFileInput = csvFileInput;
	}

	@Override
	public void onEvent() {
		T.call(this);

		csvFileInput.readFileFromInput(new HtmlFileListener() {
			@Override
			public void onReady(String fileContent) {
				T.call(this);
				
				AddStudentCsvMessage addStudentCsvMessage = Ntro.messages().create(AddStudentCsvMessage.class);
				addStudentCsvMessage.setCourseId(courseId);
				
				addStudentCsvMessage.setCsvString(fileContent);
				
				Ntro.messages().send(addStudentCsvMessage);
				
				csvFileInput.value("");
			}
		});
	}

}
