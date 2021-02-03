package ca.aquiletour.javafx.pages.root;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.root.QuitMessage;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.settings.ShowSettingsMessage;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.NtroViewFx;
import ca.ntro.messages.MessageFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class RootViewFx extends NtroViewFx implements RootView {

	@FXML
	private MenuItem dashboard;

	@FXML
	private MenuItem settings;
	
	@FXML
	private MenuItem quit;
	
	@FXML
	private VBox pageContainer;
	
	private QuitMessage quitMessage = MessageFactory.getOutgoingMessage(QuitMessage.class);
	private ShowDashboardMessage showDashboardMessage = MessageFactory.getOutgoingMessage(ShowDashboardMessage.class);
	private ShowSettingsMessage showSettingsMessage = MessageFactory.getOutgoingMessage(ShowSettingsMessage.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.call(this);
		
		MustNot.beNull(dashboard);
		MustNot.beNull(settings);
		MustNot.beNull(quit);
		MustNot.beNull(pageContainer);
		
		dashboard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				T.call(this);
				
				showDashboardMessage.sendMessage();
			}
		});

		settings.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				T.call(this);
				
				showSettingsMessage.sendMessage();
			}
		});
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				T.call(this);

				quitMessage.sendMessage();
			}
		});
		
	}

	@Override
	public void showSettings(NtroView view) {
		T.call(this);
		
		NtroViewFx viewFx = (NtroViewFx) view;
		
		pageContainer.getChildren().clear();
		pageContainer.getChildren().add(viewFx.getParent());
		
		
		
	}

}
