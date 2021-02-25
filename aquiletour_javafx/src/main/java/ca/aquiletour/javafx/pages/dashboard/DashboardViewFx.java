package ca.aquiletour.javafx.pages.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.dashboards.CourseSummaryView;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboards.values.CourseSummary;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroViewFx;
import ca.ntro.javafx.ViewLoaderFx;
import ca.ntro.messages.MessageFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DashboardViewFx extends NtroViewFx implements DashboardView {
	
	@FXML
	private Button newCourseButton;
	
	@FXML 
	private TextField newCourseText;

	@FXML 
	private TextField newSummaryText;

	@FXML 
	private TextField newSummaryDate;
	
	@FXML
	private VBox courseContainer;

	private AddCourseMessage addCourseMessage = MessageFactory.getOutgoingMessage(AddCourseMessage.class);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.call(this);
		
		MustNot.beNull(newCourseButton);
		MustNot.beNull(newCourseText);
		MustNot.beNull(courseContainer);
		
		newCourseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				T.call(this);
				
				addCourseMessage.setCourse(new CourseSummary(newCourseText.getText(), newSummaryText.getText(), false, newSummaryDate.getText(), 0));
				addCourseMessage.sendMessage();
				
				newCourseText.clear();
				newSummaryText.clear();
				newSummaryDate.clear();
			}
		});
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public void appendCourse(CourseSummaryView courseView) {
		T.call(this);
		
		CourseSummaryViewFx courseViewFx = (CourseSummaryViewFx) courseView;
		
		
		courseContainer.getChildren().add(courseViewFx.getParent());
	}
}
