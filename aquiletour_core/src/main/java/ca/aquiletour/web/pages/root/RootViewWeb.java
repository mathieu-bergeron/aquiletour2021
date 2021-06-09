package ca.aquiletour.web.pages.root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ca.ntro.assertions.Factory.that;
import java.util.Map;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.user.Admin;
import ca.aquiletour.core.models.user.Guest;
import ca.aquiletour.core.models.user.Student;
import ca.aquiletour.core.models.user.StudentGuest;
import ca.aquiletour.core.models.user.Teacher;
import ca.aquiletour.core.models.user.TeacherGuest;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.course.views.CourseView;
import ca.aquiletour.core.pages.course_list.views.CourseListView;
import ca.aquiletour.core.pages.dashboard.views.DashboardView;
import ca.aquiletour.core.pages.group_list.views.GroupListView;
import ca.aquiletour.core.pages.git.commit_list.views.CommitListView;
import ca.aquiletour.core.pages.git.late_students.LateStudentsView;
import ca.aquiletour.core.pages.git.student_summaries.StudentSummariesView;
import ca.aquiletour.core.pages.home.HomeView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.semester_list.views.SemesterListView;
import ca.aquiletour.web.widgets.BootstrapAlert;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.open_queue_list.OpenQueueListView;
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.AnimationListener;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public class RootViewWeb extends NtroViewWeb implements RootView {
	
	private long FADE_OUT_LENGTH = 100;
	private long FADE_INT_LENGTH = 100;
	private long LOGIN_MENU_MESSAGE_DURATION = 1000*5;
	
	private HtmlElement subViewContainer;
	private NtroViewWeb currentSubView;

	private HtmlElement homeLink;
	private HtmlElement dashboardLink;
	private HtmlElement courseListLink;
	private HtmlElement groupListLink;
	private HtmlElement openQueueListLink;
	private HtmlElement semesterListLink;
	private HtmlElement queueLink;

	private HtmlElement loginDropdown;
	private HtmlElement loginButton;
	private HtmlElement loginMenuMessage;
	private HtmlElement loginMenuMessageText;
	private HtmlElement loginMenuEnterId;
	private HtmlElement loginMenuEnterPassword;
	private HtmlElement loginMenuEnterCode;
	private HtmlElement loginMenuAddPassword;
	private HtmlElement currentPasswordContainer;
	private HtmlElement modifyPasswordButton;
	private HtmlElement loginMenuUserProfile;
	private HtmlElement userNameContainer;
	private HtmlElement userNameInput;
	private HtmlElement showPasswordMenuLink;
	private HtmlElement toggleStudentModeButton;
	private HtmlElement toggleStudentModeContainer;
	private HtmlElement toggleAdminModeButton;
	private HtmlElement toggleAdminModeContainer;
	
	private HtmlElements logoutLinks;
	private HtmlElements addDelayedMessagesToValue;
	private HtmlElements addUserIdToValue;

	private BootstrapAlert alertDanger;
	private BootstrapAlert alertPrimary;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		subViewContainer = getRootElement().find("#page-container").get(0);
		homeLink = getRootElement().find("#home-link").get(0);
		dashboardLink = getRootElement().find("#dashboard-link").get(0);
		openQueueListLink = getRootElement().find("#open-queue-list-link").get(0);
		courseListLink = getRootElement().find("#course-list-link").get(0);
		semesterListLink = getRootElement().find("#calendar-list-link").get(0);
		groupListLink = getRootElement().find("#group-list-link").get(0);

		loginDropdown = getRootElement().find("#login-dropdown").get(0);
		loginButton = getRootElement().find("#login-button").get(0);
		loginMenuMessage = getRootElement().find("#login-menu-message").get(0);
		loginMenuMessageText = getRootElement().find("#login-menu-message-text").get(0);
		loginMenuEnterId = getRootElement().find("#login-menu-enter-id").get(0);
		loginMenuEnterPassword = getRootElement().find("#login-menu-enter-password").get(0);
		loginMenuEnterCode = getRootElement().find("#login-menu-enter-code").get(0);
		loginMenuAddPassword = getRootElement().find("#login-menu-add-password").get(0);
		currentPasswordContainer = getRootElement().find("#current-password-container").get(0);
		modifyPasswordButton = getRootElement().find("#modify-password-button").get(0);

		loginMenuUserProfile = getRootElement().find("#login-menu-user-profile").get(0);
		userNameContainer = getRootElement().find("#user-name-container").get(0);
		userNameInput = getRootElement().find("#user-name-input").get(0);
		showPasswordMenuLink = getRootElement().find("#show-password-menu-link").get(0);
		toggleStudentModeContainer = getRootElement().find("#toggle-student-mode-container").get(0);
		toggleStudentModeButton = getRootElement().find("#toggle-student-mode-button").get(0);

		toggleAdminModeContainer = getRootElement().find("#toggle-admin-mode-container").get(0);
		toggleAdminModeButton = getRootElement().find("#toggle-admin-mode-button").get(0);

		logoutLinks = getRootElement().find(".logout-link");
		addDelayedMessagesToValue = getRootElement().find(".add-delayed-messages-to-value");
		addUserIdToValue = getRootElement().find(".add-user-id-to-value");

		queueLink = getRootElement().find("#queue-link").get(0);


		HtmlElement alertDangerElement = getRootElement().find("#alert-danger").get(0);
		HtmlElement alertPrimaryElement = getRootElement().find("#alert-primary").get(0);

		MustNot.beNull(subViewContainer);
		MustNot.beNull(homeLink);
		MustNot.beNull(dashboardLink);
		MustNot.beNull(openQueueListLink);
		MustNot.beNull(semesterListLink);
		MustNot.beNull(loginButton);
		MustNot.beNull(loginDropdown);
		MustNot.beNull(loginMenuMessage);
		MustNot.beNull(loginMenuMessageText);
		MustNot.beNull(loginMenuEnterId);
		MustNot.beNull(loginMenuEnterPassword);
		MustNot.beNull(loginMenuEnterCode);
		MustNot.beNull(loginMenuAddPassword);
		MustNot.beNull(currentPasswordContainer);
		MustNot.beNull(loginMenuUserProfile);
		MustNot.beNull(toggleStudentModeContainer);
		MustNot.beNull(toggleStudentModeButton);
		MustNot.beNull(toggleAdminModeContainer);
		MustNot.beNull(toggleAdminModeButton);
		MustNot.beNull(userNameContainer);
		MustNot.beNull(groupListLink);
		MustNot.beNull(queueLink);
		MustNot.beNull(userNameInput);
		MustNot.beNull(showPasswordMenuLink);
		MustNot.beNull(courseListLink);
		MustNot.beNull(alertDangerElement);
		MustNot.beNull(alertPrimaryElement);

		Ntro.verify(that(logoutLinks.size() > 0).isTrue());
		Ntro.verify(that(addDelayedMessagesToValue.size() > 0).isTrue());
		Ntro.verify(that(addUserIdToValue.size() > 0).isTrue());

		alertDanger = new BootstrapAlert(alertDangerElement);
		alertPrimary = new BootstrapAlert(alertPrimaryElement);

		onContextChange(context);

		initializeAlerts();
	}


	private void initializeAlerts() {
		T.call(this);
		
		alertDanger.hide();
		alertPrimary.hide();
	}

	@Override
	public void onContextChange(NtroContext<?,?> context) {
		T.call(this);
		
		User user = (User) context.user();
		String userName = user.getFirstname();
		
		addUserIdToValue.appendToAttribute("value", user.getId());

		adjustLoginMenu(user, userName);
		adjustLinks(user);
	}

	private void adjustLinks(User user) {
		T.call(this);
		
		courseListLink.hide();
		openQueueListLink.hide();
		semesterListLink.hide();
		groupListLink.hide();
		queueLink.hide();
		dashboardLink.hide();

		//loginButton.setAttribute("href", "/" + Constants.LOGIN_URL_SEGMENT);

		logoutLinks.forEach(e -> {
			e.setAttribute("href", "/" + Constants.LOGOUT_URL_SEGMENT);
		});

		homeLink.show();
		
		if(!user.actsAsAdmin()) {
			homeLink.setAttribute("href", "/" + Constants.DASHBOARD_URL_SEGMENT);
			dashboardLink.setAttribute("href", "/" + Constants.DASHBOARD_URL_SEGMENT);
			courseListLink.setAttribute("href", "/" + Constants.COURSE_LIST_URL_SEGMENT);
			
			dashboardLink.show();
			courseListLink.show();

		}else {
			homeLink.setAttribute("href", "/" + Constants.SEMESTER_LIST_URL_SEGMENT);
		}

		if(user.actsAsTeacher()) {
			semesterListLink.setAttribute("href", "/" + Constants.SEMESTER_LIST_URL_SEGMENT);
			semesterListLink.show();
		}
		
		if(user.actsAsTeacher() && !user.actsAsAdmin()) {

			groupListLink.setAttribute("href", "/" + Constants.GROUP_LIST_URL_SEGMENT);

			queueLink.setAttribute("href", "/" + Constants.QUEUE_URL_SEGMENT + "/" + user.getId());
			
			toggleStudentModeButton.text("Mode étudiant");
			
			if(user.getHasPassword()) {
				showPasswordMenuLink.text("Modifier mon mot de passe");
			}else {
				showPasswordMenuLink.text("Ajouter un mot de passe");
			}

			groupListLink.show();
			queueLink.show();
		}
		
		if(!user.actsAsTeacher() && !user.actsAsTeacher()) {
			toggleStudentModeButton.text("Mode enseignant");

			openQueueListLink.setAttribute("href", "/" + Constants.QUEUES_URL_SEGMENT);
			openQueueListLink.show();
		}
	}


	private void adjustLoginMenu(User user, String userName) {
		T.call(this);
		
		System.out.println("adjustLoginMenu");
		System.out.println(user);
		
		loginMenuMessage.hide();
		loginMenuEnterId.hide();
		loginMenuEnterPassword.hide();
		loginMenuEnterCode.hide();
		loginMenuAddPassword.hide();
		loginMenuUserProfile.hide();
		toggleAdminModeContainer.hide();
		toggleStudentModeContainer.hide();

		loginButton.removeClass("btn-danger");
		loginButton.addClass("btn-secondary");

		if(user instanceof Guest) {

			loginButton.text("Connexion");
			loginMenuEnterId.show();

		} else if(shouldValidatePassword(user)) {

			String linkText = "Valider " + user.getEmail();
			loginButton.text(linkText);
			loginMenuEnterPassword.show();

		} else if(shouldValidateLoginCode(user)) {

			String linkText = "Valider " + user.getEmail();
			loginButton.text(linkText);
			loginMenuEnterCode.show();

		}else if(user instanceof Teacher && !(user instanceof Admin)) {

			adjustLoginMenuForTeacher(user, userName);

		}else if(user instanceof Admin && !user.actsAsAdmin()) {

			adjustLoginMenuForTeacher(user, userName);

			toggleAdminModeContainer.show();
			toggleAdminModeButton.removeClass("btn-secondary");
			toggleAdminModeButton.addClass("btn-danger");

		}else if(user instanceof Admin && user.actsAsAdmin()) {
			
			loginMenuUserProfile.show();

			toggleStudentModeContainer.hide();
			userNameContainer.hide();
			showPasswordMenuLink.hide();

			loginButton.text("Admin");
			loginButton.removeClass("btn-secondary");
			loginButton.addClass("btn-danger");
			toggleAdminModeContainer.show();
			toggleAdminModeButton.text("Quitter le mode admin");
			toggleAdminModeButton.removeClass("btn-danger");
			toggleAdminModeButton.addClass("btn-secondary");

		}else if(user instanceof Student) {
			userName = displayName(user, userName);
			
			loginButton.text(userName);
			loginMenuUserProfile.show();
			userNameContainer.hide();
			toggleStudentModeContainer.hide();
		}
	}


	private void adjustLoginMenuForTeacher(User user, String userName) {
		T.call(this);

		userName = displayName(user, userName);
		loginMenuUserProfile.show();
		userNameInput.value(userName);
		toggleStudentModeContainer.show();
		loginButton.text(userName);
	}

	private String displayName(User user, String userName) {
		if(user.getLastname() != null && !user.getLastname().isEmpty()) {
			userName += " " + user.getLastname();
		}
		return userName;
	}


	private boolean shouldValidateLoginCode(User user) {
		T.call(this);

		return (user instanceof TeacherGuest || user instanceof StudentGuest)
				&& !user.getHasPassword();
	}

	private boolean shouldValidatePassword(User user) {
		T.call(this);

		return (user instanceof TeacherGuest || user instanceof StudentGuest)
				&& user.getHasPassword();
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
					                                FADE_OUT_LENGTH, 
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
				               FADE_OUT_LENGTH, 
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
	public void showCourse(CourseView courseView) {
		T.call(this);

		showSubView(courseView);
	}

	@Override
	public void showGitCommitList(CommitListView gitCommitListView) {
		T.call(this);
		
		showSubView(gitCommitListView);
	}

	@Override
	public void showGitLateStudents(LateStudentsView gitLateStudentsView) {
		T.call(this);
		
		showSubView(gitLateStudentsView);
		
	}

	@Override
	public void showGitStudentSummaries(StudentSummariesView gitStudentSummariesView) {
		T.call(this);
		
		showSubView(gitStudentSummariesView);
		
	}

	@Override
	public void showSemesterList(SemesterListView calendarListView) {
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
	public void showLoginMenu(String messageToUser, List<NtroMessage> delayedMessages) {
		T.call(this);

		loginDropdown.addClass("show");

		String delayedMessagesText = delayedMessagesText(delayedMessages);

		addDelayedMessagesToValue.setAttribute("value", delayedMessagesText);
		
		if(messageToUser != null && !messageToUser.isEmpty()) {
			loginMenuMessage.show();
			loginMenuMessageText.text(messageToUser);

			/*
			loginMenuMessage.animate(new HashMap<>(), 
									 LOGIN_MENU_MESSAGE_DURATION,
					                 new AnimationListener() {
				@Override
				public void animationFinished() {
					T.call(this);

					Map<String, Object> properties = new HashMap<>();
					properties.put("opacity", 0.0);

					loginMenuMessage.animate(new HashMap<>(), 
											 FADE_OUT_LENGTH,
											 new AnimationListener() {
						@Override
						public void animationFinished() {
							T.call(this);
							loginMenuMessage.hide();
							loginMenuMessage.css("opacity", 1.0);
						}
					});
				}
			});*/
		}
	}

	private String delayedMessagesText(List<NtroMessage> delayedMessages) {
		T.call(this);

		String delayedMessagesText = Ntro.jsonService().toString(delayedMessages);
		delayedMessagesText = delayedMessagesText.replaceAll("\"", "\\\"");

		return delayedMessagesText;
	}


	@Override
	public void showPasswordMenu() {
		T.call(this);
		
		showLoginMenu(null, new ArrayList<>());
		loginMenuAddPassword.show();
		loginMenuUserProfile.hide();

		User user = (User) Ntro.currentUser();
		if(user.getHasPassword()) {
			currentPasswordContainer.show();
			modifyPasswordButton.text("Modifier mon mot de passe");
		}else {
			currentPasswordContainer.hide();
			modifyPasswordButton.text("Ajouter le mot de passe");
		}
	}
	
}
