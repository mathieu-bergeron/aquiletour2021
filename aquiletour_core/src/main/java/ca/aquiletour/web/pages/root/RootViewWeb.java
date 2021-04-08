package ca.aquiletour.web.pages.root;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.messages.ShowDashboardMessage;
import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.admin.calendar_list.messages.ShowCalendarListMessage;
import ca.aquiletour.core.pages.admin.calendar_list.views.CalendarListView;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.dashboards.DashboardView;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.open_queue_list.messages.ShowOpenQueueListMessage;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class RootViewWeb extends NtroViewWeb implements RootView {

	private HtmlElement homeLink;
	private HtmlElement dashboardLink;
	private HtmlElement coursesLink;
	private HtmlElement groupsLink;
	private HtmlElement openQueueListLink;
	private HtmlElement calendarListLink;
	private HtmlElement loginLink;

	@Override
	public void initializeViewWeb(NtroContext<?> context) {
		T.call(this);

		homeLink = getRootElement().find("#home-link").get(0);
		dashboardLink = getRootElement().find("#dashboard-link").get(0);
		openQueueListLink = getRootElement().find("#open-queue-list-link").get(0);
		calendarListLink = getRootElement().find("#calendar-list-link").get(0);
		loginLink = getRootElement().find("#login-link").get(0);

		MustNot.beNull(homeLink);
		MustNot.beNull(dashboardLink);
		MustNot.beNull(openQueueListLink);
		MustNot.beNull(calendarListLink);
		MustNot.beNull(loginLink);

		initializeLinks();

		adjustLoginLinkText(context);
	}

	private void initializeLinks() {
		T.call(this);
		
		homeLink.setAttribute("href", "/" + Constants.HOME_URL_SEGMENT);

		homeLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				Ntro.messages().send(Ntro.messages().create(ShowHomeMessage.class));
			}
		});
		
		dashboardLink.setAttribute("href", "/" + Constants.DASHBOARD_URL_SEGMENT 
				                               + "?" + Constants.USER_URL_PARAM + "=" + Ntro.userService().user().getId()
				                               + "&" + Constants.SEMESTER_URL_PARAM + "=" + "H2021");

		dashboardLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				Ntro.messages().send(Ntro.messages().create(ShowDashboardMessage.class));
			}
		});

		openQueueListLink.setAttribute("href", "/" + Constants.QUEUES_URL_SEGMENT);
		
		openQueueListLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				Ntro.messages().send(Ntro.messages().create(ShowOpenQueueListMessage.class));
			}
		});

		loginLink.setAttribute("href", "/" + Constants.LOGIN_URL_SEGMENT);

		loginLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				ShowLoginMessage showLoginMessage = Ntro.messages().create(ShowLoginMessage.class);
				showLoginMessage.setMessageToUser("");
				Ntro.messages().send(showLoginMessage);
			}
		});

		calendarListLink.setAttribute("href", "/" + Constants.CALENDAR_LIST_URL_SEGMENT);

		calendarListLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				
				ShowCalendarListMessage showCalendarListMessage = Ntro.messages().create(ShowCalendarListMessage.class);
				Ntro.messages().send(showCalendarListMessage);
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

			String linkText = "Valider " + user.getEmail();
			loginLink.html(linkText);

		}else if(context.user() instanceof Teacher || context.user() instanceof Student) {
			userName += " " + user.getSurname();
			
			loginLink.html(userName + " (se déconnecter)");
			loginLink.removeListeners();
			loginLink.setAttribute("href", "/deconnexion");

			//HtmlElement nameElement = loginLink.newElement("<span class=\"nav-link\">" + userName + "&nbsp;</span>");
			//loginLink.insertBefore(nameElement);
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

		container.removeChildrenFromDocument();
		container.appendElement(subViewElement);
	}

	@Override
	public void showQueue(QueueView queueView) {
		T.call(this);

		showSubView(queueView);
	}

	@Override
	public void showLogin(LoginView loginView) {
		T.call(this);

		showSubView(loginView);

	}

	@Override
	public void showQueues(OpenQueueListView currentView) {
		T.call(this);

		showSubView(currentView);
	}

	@Override
	public void showHome(HomeView homeView) {
		T.call(this);
		
		showSubView(homeView);
	}

	@Override
	public void showGit(CommitListView commitListView) {
		T.call(this);
		
		showSubView(commitListView);
	}

	@Override
	public void showCourse(CourseView courseView) {
		T.call(this);

		showSubView(courseView);
	}

	@Override
	public void showCalendarList(CalendarListView calendarListView) {
		T.call(this);

		showSubView(calendarListView);
	}
}
