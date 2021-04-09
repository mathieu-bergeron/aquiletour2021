package ca.aquiletour.javafx.pages.root;

import java.net.URL;
import java.util.ResourceBundle;

import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.QuitMessage;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.users.UsersView;
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
	private ShowStudentDashboardMessage showStudentDashboardMessage = MessageFactory.getOutgoingMessage(ShowStudentDashboardMessage.class);
	private ShowTeacherDashboardMessage showTeacherDashboardMessage = MessageFactory.getOutgoingMessage(ShowTeacherDashboardMessage.class);

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
				
				showStudentDashboardMessage.sendMessage();
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
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showDashboard(DashboardView dashboardView) {
		T.call(this);
		
		showSubView(dashboardView);
	}

	private void showSubView(NtroView view) {
		T.call(this);

		NtroViewFx viewFx = (NtroViewFx) view;
		
		pageContainer.getChildren().clear();
		pageContainer.getChildren().add(viewFx.getParent());
	}

	@Override
	public void showQueue(QueueView queueView) {
		T.call(this);
		
		showSubView(queueView);
	}


	@Override
	public void showUsers(UsersView usersView) {
		T.call(this);
		
		showSubView(usersView);
	}

	@Override
	public void showLogin(LoginView loginView) {
		T.call(this);
		
		showSubView(loginView);
	}

	@Override
	public void showQueues(QueuesView queuesView) {

		showSubView(queuesView);
	}

	@Override
	public void showHome(HomeView homeView) {

		showSubView(homeView);
	}
}
