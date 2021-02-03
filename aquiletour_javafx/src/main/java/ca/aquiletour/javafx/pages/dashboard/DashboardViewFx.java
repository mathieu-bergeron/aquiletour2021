package ca.aquiletour.javafx.pages.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.values.CourseSummary;
import ca.ntro.core.mvc.MessageFactory;
import ca.ntro.core.mvc.ViewLoader;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroViewFx;
import ca.ntro.javafx.ViewLoaderFx;
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
	
	private ViewLoaderFx courseSummaryViewLoader;

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
				
				addCourseMessage.setCourse(new CourseSummary(newCourseText.getText(), newSummaryText.getText(), newSummaryDate.getText()));
				addCourseMessage.sendMessage();
				
				newCourseText.clear();
				newSummaryText.clear();
				newSummaryDate.clear();
			}
		});
	}

	@Override
	public void appendCourse(CourseSummary course) {
		T.call(this);
		
		CourseSummaryViewFx courseView = (CourseSummaryViewFx) courseSummaryViewLoader.createView();
		
		courseView.displaySummary(course);
		
		courseContainer.getChildren().add(courseView.getParent());
	}

	@Override
	public void setCourseSummaryViewLoader(ViewLoader courseSummaryViewLoader) {
		T.call(this);
		
		this.courseSummaryViewLoader = (ViewLoaderFx) courseSummaryViewLoader;
	}

}
