package ca.aquiletour.javafx.pages.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.dashboard.CourseSummaryView;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroViewFx;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CourseSummaryViewFx extends NtroViewFx implements CourseSummaryView {
	
	@FXML
	private Text courseTitle;
	
	@FXML
	private Text summaryText;
	
	@FXML
	private Text summaryDate;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.call(this);
		
		MustNot.beNull(courseTitle);
		MustNot.beNull(summaryText);
		MustNot.beNull(summaryDate);
		
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);
		
		courseTitle.setText(course.getTitle());
		summaryText.setText(course.getSummary());
		summaryDate.setText(course.getDate());
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
