package ca.aquiletour.web.pages.root;

import java.util.HashMap;
import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.users.Guest;
import ca.aquiletour.core.models.users.Student;
import ca.aquiletour.core.models.users.StudentGuest;
import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.TeacherGuest;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course_list.messages.ShowCourseListMessage;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
import ca.aquiletour.core.pages.git.CommitListView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.home.ShowHomeMessage;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.semester_list.messages.ShowSemesterListMessage;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.web.widgets.BootstrapAlert;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.login.ShowLoginMessage;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.open_queue_list.messages.ShowOpenQueueListMessage;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.AnimationListener;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class RootViewWeb extends NtroViewWeb implements RootView {
	
	private long ANIMATION_LENGTH = 100;
	
	private HtmlElement subViewContainer;
	private NtroViewWeb currentSubView;

	private HtmlElement homeLink;
	private HtmlElement dashboardLink;
	private HtmlElement coursesLinkTeacher;
	private HtmlElement coursesLinkStudent;
	private HtmlElement groupListLink;
	private HtmlElement openQueueListLink;
	private HtmlElement calendarListLink;
	private HtmlElement queueLink;

	private HtmlElement loginDropdown;
	private HtmlElement loginButton;
	private HtmlElement loginMenuEnterId;
	private HtmlElement loginMenuEnterPassword;
	private HtmlElement loginMenuEnterCode;
	private HtmlElement loginMenuAddPassword;
	private HtmlElement loginMenuStudentProfile;
	private HtmlElement loginMenuTeacherProfile;
	private HtmlElement userProfileNameInput;
	private HtmlElement toggleStudentModeButton;
	private HtmlElement logoutLinkStudent;
	private HtmlElement logoutLinkTeacher;

	private BootstrapAlert alertDanger;
	private BootstrapAlert alertPrimary;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		subViewContainer = getRootElement().find("#page-container").get(0);
		homeLink = getRootElement().find("#home-link").get(0);
		dashboardLink = getRootElement().find("#dashboard-link").get(0);
		openQueueListLink = getRootElement().find("#open-queue-list-link").get(0);
		coursesLinkTeacher = getRootElement().find("#courses-link-teacher").get(0);
		coursesLinkStudent = getRootElement().find("#courses-link-student").get(0);
		calendarListLink = getRootElement().find("#calendar-list-link").get(0);
		groupListLink = getRootElement().find("#group-list-link").get(0);

		loginDropdown = getRootElement().find("#login-dropdown").get(0);
		loginButton = getRootElement().find("#login-button").get(0);
		loginMenuEnterId = getRootElement().find("#login-menu-enter-id").get(0);
		loginMenuEnterPassword = getRootElement().find("#login-menu-enter-password").get(0);
		loginMenuEnterCode = getRootElement().find("#login-menu-enter-code").get(0);
		loginMenuAddPassword = getRootElement().find("#login-menu-add-password").get(0);
		loginMenuStudentProfile = getRootElement().find("#login-menu-student-profile").get(0);
		loginMenuTeacherProfile = getRootElement().find("#login-menu-teacher-profile").get(0);

		logoutLinkStudent = getRootElement().find("#logout-link-student").get(0);
		logoutLinkTeacher = getRootElement().find("#logout-link-teacher").get(0);

		queueLink = getRootElement().find("#queue-link").get(0);
		userProfileNameInput = getRootElement().find("#user-profile-name-input").get(0);
		toggleStudentModeButton = getRootElement().find("#toggle-student-mode-button").get(0);
		HtmlElement alertDangerElement = getRootElement().find("#alert-danger").get(0);
		HtmlElement alertPrimaryElement = getRootElement().find("#alert-primary").get(0);

		MustNot.beNull(subViewContainer);
		MustNot.beNull(homeLink);
		MustNot.beNull(dashboardLink);
		MustNot.beNull(openQueueListLink);
		MustNot.beNull(calendarListLink);
		MustNot.beNull(loginButton);
		MustNot.beNull(loginDropdown);
		MustNot.beNull(loginMenuEnterId);
		MustNot.beNull(loginMenuEnterPassword);
		MustNot.beNull(loginMenuEnterCode);
		MustNot.beNull(loginMenuAddPassword);
		MustNot.beNull(loginMenuStudentProfile);
		MustNot.beNull(loginMenuTeacherProfile);
		MustNot.beNull(logoutLinkStudent);
		MustNot.beNull(logoutLinkTeacher);
		MustNot.beNull(groupListLink);
		MustNot.beNull(queueLink);
		MustNot.beNull(userProfileNameInput);
		MustNot.beNull(coursesLinkTeacher);
		MustNot.beNull(coursesLinkStudent);
		MustNot.beNull(toggleStudentModeButton);
		MustNot.beNull(alertDangerElement);
		MustNot.beNull(alertPrimaryElement);

		alertDanger = new BootstrapAlert(alertDangerElement);
		alertPrimary = new BootstrapAlert(alertPrimaryElement);

		initializeLinks();

		onContextChange(context);
		
		initializeAlerts();
	}

	private void initializeAlerts() {
		T.call(this);
		
		alertDanger.hide();
		alertPrimary.hide();
	}

	private void initializeLinks() {
		T.call(this);
		
		homeLink.setAttribute("href", "/" + Constants.HOME_URL_SEGMENT);

		dashboardLink.setAttribute("href", "/" + Constants.DASHBOARD_URL_SEGMENT);

		coursesLinkTeacher.setAttribute("href", "/" + Constants.COURSE_LIST_URL_SEGMENT);
		coursesLinkStudent.setAttribute("href", "/" + Constants.COURSE_LIST_URL_SEGMENT);


		loginButton.setAttribute("href", "/" + Constants.LOGIN_URL_SEGMENT);

		loginButton.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				ShowLoginMessage showLoginMessage = Ntro.messages().create(ShowLoginMessage.class);
				showLoginMessage.setMessageToUser("");
				Ntro.messages().send(showLoginMessage);
			}
		});

		logoutLinkStudent.setAttribute("href", "/" + Constants.LOGOUT_URL_SEGMENT);
		logoutLinkTeacher.setAttribute("href", "/" + Constants.LOGOUT_URL_SEGMENT);
	}

	@Override
	public void onContextChange(NtroContext<?,?> context) {
		T.call(this);
		
		User user = (User) context.user();
		String userName = user.getName();

		loginMenuEnterId.hide();
		loginMenuEnterPassword.hide();
		loginMenuEnterCode.hide();
		loginMenuAddPassword.hide();
		loginMenuStudentProfile.hide();
		loginMenuTeacherProfile.hide();

		if(context.user() instanceof Guest) {

			loginButton.html("Connexion");
			loginMenuEnterId.show();

		} else if(context.user() instanceof TeacherGuest || context.user() instanceof StudentGuest) {

			String linkText = "Valider " + user.getEmail();
			loginButton.html(linkText);
			loginMenuEnterCode.show();

		}else if(context.user() instanceof Teacher) {
			userName += " " + user.getSurname();
			
			loginButton.text(userName);
			loginMenuTeacherProfile.show();
			userProfileNameInput.value(userName);

		}else if(context.user() instanceof Student) {
			userName += " " + user.getSurname();
			
			loginButton.text(userName);
			loginMenuStudentProfile.show();
		}
		
		coursesLinkTeacher.hide();
		openQueueListLink.hide();
		calendarListLink.hide();
		groupListLink.hide();
		queueLink.hide();
		
		if(((User)context.user()).actsAsTeacher()) {
			
			toggleStudentModeButton.text("Mode étudiant");

			calendarListLink.setAttribute("href", "/" + Constants.SEMESTER_LIST_URL_SEGMENT);
			calendarListLink.show();

			groupListLink.setAttribute("href", "/" + Constants.GROUP_LIST_URL_SEGMENT);
			groupListLink.show();
			
			queueLink.setAttribute("href", "/" + Constants.QUEUE_URL_SEGMENT + "/" + Ntro.currentUser().getId());
			queueLink.show();
			
			coursesLinkStudent.hide();
			coursesLinkTeacher.show();
			
		} else {

			toggleStudentModeButton.text("Mode enseignant");

			openQueueListLink.setAttribute("href", "/" + Constants.QUEUES_URL_SEGMENT);
			openQueueListLink.show();
		}
		
	}


	@Override
	public void showDashboard(DashboardView dashboardView) {
		T.call(this);

		showSubView(dashboardView);
	}

	private void showSubView(NtroView subView) {
		T.call(this);
		
		if(currentSubView != null && currentSubView != subView) {
			
			Map<String, Object> properties = new HashMap<>();
			properties.put("opacity", 0.0);
			currentSubView.getRootElement().animate(properties, 
					                                ANIMATION_LENGTH, 
					                                new AnimationListener() {
				@Override
				public void animationFinished() {
					T.call(this);
					
					currentSubView.getRootElement().removeFromDocument();
					currentSubView.getRootElement().css("opacity", 1.0);
					currentSubView = (NtroViewWeb) subView;
					animateAppendSubView();
				}
			});

		}else if(currentSubView == null) {

			currentSubView = (NtroViewWeb) subView;
			animateAppendSubView();
		}
	}

	private void animateAppendSubView() {
		T.call(this);


		HtmlElement subViewElement = currentSubView.getRootElement();
		subViewElement.css("opacity", 0.0);

		subViewContainer.removeChildrenFromDocument();
		subViewContainer.appendElement(subViewElement);
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("opacity", 1.0);
		subViewElement.animate(properties, 
				               ANIMATION_LENGTH, 
				               new AnimationListener() {
			@Override
			public void animationFinished() {
				T.call(this);
			}
		});
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
	public void showCalendarList(SemesterListView calendarListView) {
		T.call(this);

		showSubView(calendarListView);
	}

	@Override
	public void showCourseList(CourseListView courseListView) {
		T.call(this);

		showSubView(courseListView);
	}

	@Override
	public void displayErrorMessage(String message) {
		T.call(this);
		
		alertDanger.displayMessage(message);
		
	}

	@Override
	public void displayPrimaryMessage(String message) {
		T.call(this);
		
		alertPrimary.displayMessage(message);
		
	}

	@Override
	public void showGroupList(GroupListView groupListView) {
		T.call(this);

		showSubView(groupListView);
	}

	@Override
	public void displayUserScreenName(String screenName) {
		T.call(this);
		
		loginButton.text(screenName);
	}

	@Override
	public void showLoginMenu() {
		T.call(this);

		loginDropdown.addClass("show");
	}
}
