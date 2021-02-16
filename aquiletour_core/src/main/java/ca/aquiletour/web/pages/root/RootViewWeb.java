package ca.aquiletour.web.pages.root;

import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.users.UsersView;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class RootViewWeb extends NtroViewWeb implements RootView {

	@Override
	public void initialize() {
		T.call(this);

		HtmlElement dashboardLink = getRootElement().find("#dashboard-link").get(0);

		MustNot.beNull(dashboardLink);

		dashboardLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				ShowStudentDashboardMessage showDashboardMessage = MessageFactory.getOutgoingMessage(ShowStudentDashboardMessage.class);
				showDashboardMessage.sendMessage();
			}
		});
	}

	@Override
	public void showDashboard(DashboardView dashboardView) {
		T.call(this);

		showSubView(dashboardView);
	}

	private void showSubView(NtroView view) {
		T.call(this);

		NtroViewWeb viewWeb = (NtroViewWeb) view;

		HtmlElement container = this.getRootElement().children("#page-container").get(0);

		MustNot.beNull(container);

		HtmlElement subViewElement = viewWeb.getRootElement();

		container.clearChildren();
		container.appendElement(subViewElement);
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
	public void showQueues(QueuesView currentView) {
		T.call(this);

		showSubView(currentView);
	}

	@Override
	public void showHome(HomeView homeView) {
		T.call(this);
		showSubView(homeView);
	}
}
