package ca.aquiletour.web.pages.root;

import ca.aquiletour.core.messages.ShowDashboardMessage;
import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.queues.QueuesView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.users.UsersView;
import ca.aquiletour.core.pages.users.messages.ShowUsersMessage;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class RootViewWeb extends NtroViewWeb implements RootView {

	private HtmlElement dashboardLink;
	private HtmlElement usersLink;
	private HtmlElement loginLink;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		dashboardLink = getRootElement().find("#dashboard-link").get(0);
		usersLink = getRootElement().find("#users-link").get(0);
		loginLink = getRootElement().find("#login-link").get(0);

		MustNot.beNull(dashboardLink);
		MustNot.beNull(usersLink);
		MustNot.beNull(loginLink);

		addListeners();

		adjustLoginLinkText(context);

	}

	private void addListeners() {
		T.call(this);

		dashboardLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				Ntro.messages().send(Ntro.messages().create(ShowDashboardMessage.class));
			}
		});
		
		usersLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				ShowUsersMessage showUsersMessage = Ntro.messages().create(ShowUsersMessage.class);
				Ntro.messages().send(showUsersMessage);
			}
		});

		loginLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				ShowLoginMessage showLoginMessage = Ntro.messages().create(ShowLoginMessage.class);
				showLoginMessage.setMessageToUser("");
				Ntro.messages().send(showLoginMessage);
			}
		});
	}

	@Override
	public void adjustLoginLinkText(NtroContext<?> context) {
		T.call(this);

		User user = (User) context.user();
		String userName = user.getName();

		if(context.user() instanceof Guest) {

			String linkText = userName + " (se connecter)";
			loginLink.html(linkText);

		} else if(context.user() instanceof TeacherGuest || context.user() instanceof StudentGuest) {

			String linkText = "SVP valider que vous êtes bien " + userName;
			loginLink.html(linkText);

		}else if(context.user() instanceof Teacher || context.user() instanceof Student) {
			userName += " " + user.getSurname();

			String linkText = userName + " (se déconnecter)";
			loginLink.html(linkText);
		}
	}


	@Override
	public void showDashboard(DashboardView dashboardView) {
		T.call(this);


		showSubView(dashboardView);
	}

	private void showSubView(NtroView view) {
		T.call(this);

		NtroViewWeb viewWeb = (NtroViewWeb) view;

		HtmlElement container = this.getRootElement().find("#page-container").get(0);

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
