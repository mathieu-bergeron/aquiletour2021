package ca.aquiletour.javafx.pages.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.dashboard.messages.AddCourseMessage;
import ca.aquiletour.core.pages.dashboard.values.Course;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroViewFx;
import ca.ntro.messages.MessageFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DashboardViewFx extends NtroViewFx implements DashboardView {
	
	@FXML
	private Button newCourseButton;
	
	@FXML 
	private TextField newCourseText;
	
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
				
				addCourseMessage.setText(newCourseText.getText());
				addCourseMessage.sendMessage();
				
				newCourseText.clear();
			}
		});
	}

	@Override
	public void appendCourse(Course course) {
		T.call(this);
		
		courseContainer.getChildren().add(new Text(course));
	}

}
