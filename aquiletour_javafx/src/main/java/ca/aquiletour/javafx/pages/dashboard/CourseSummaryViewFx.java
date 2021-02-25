package ca.aquiletour.javafx.pages.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroViewFx;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class CourseSummaryViewFx extends NtroViewFx implements CourseSummaryView {
	
	@FXML
	private Text courseTitle;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.call(this);
		
		MustNot.beNull(courseTitle);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySummary(CourseSummary course) {
		T.call(this);
		
		courseTitle.setText(course.getTitle());
	}


}
