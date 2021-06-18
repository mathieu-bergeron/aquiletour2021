package ca.aquiletour.web.pages.root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ca.ntro.assertions.Factory.that;
import java.util.Map;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.user.Guest;
import ca.aquiletour.core.models.user.StudentGuest;
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
import ca.aquiletour.core.pages.queue.views.QueueView;
import ca.aquiletour.core.pages.queue_list.views.QueueListView;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.mvc.NtroView;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.NtroMessage;
import ca.ntro.services.Ntro;
import ca.ntro.web.dom.AnimationListener;
import ca.ntro.web.dom.HtmlDocument;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlElements;
import ca.ntro.web.mvc.NtroViewWeb;

public class RootViewWeb extends NtroViewWeb implements RootView {
	
	private long FADE_OUT_LENGTH = 100;
	private long FADE_INT_LENGTH = 100;
	private long LOGIN_MENU_MESSAGE_DURATION = 1000*5;

	private HtmlElement logoImage;
	private HtmlElement logoLoading;
	
	private HtmlElement subViewContainer;
	private NtroViewWeb currentSubView;

	private HtmlElement homeLink;
	private HtmlElement dashboardLink;
	private HtmlElement courseListLink;
	private HtmlElement groupListLink;
	private HtmlElement queueListLink;
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
	private HtmlElement loginMenuNameContainer;
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
	
	private HtmlElements addDelayedMessagesToValue;
	private HtmlElements addUserIdToValue;

	private BootstrapAlert alertDanger;
	private BootstrapAlert alertPrimary;

	@Override
	public void initializeViewWeb(NtroContext<?,?> context) {
		T.call(this);

		logoImage = getRootElement().find("#logo-image").get(0);
		logoLoading = getRootElement().find("#logo-loading").get(0);

		subViewContainer = getRootElement().find("#page-container").get(0);
		homeLink = getRootElement().find("#home-link").get(0);
		dashboardLink = getRootElement().find("#dashboard-link").get(0);
		queueListLink = getRootElement().find("#queue-list-link").get(0);
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
		loginMenuNameContainer = getRootElement().find(".login-menu-name-container").get(0);
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

		addDelayedMessagesToValue = getRootElement().find(".add-delayed-messages-to-value");
		addUserIdToValue = getRootElement().find(".add-user-id-to-value");

		queueLink = getRootElement().find("#queue-link").get(0);


		HtmlElement alertDangerElement = getRootElement().find("#alert-danger").get(0);
		HtmlElement alertPrimaryElement = getRootElement().find("#alert-primary").get(0);

		MustNot.beNull(logoImage);
		MustNot.beNull(logoLoading);

		MustNot.beNull(subViewContainer);
		MustNot.beNull(homeLink);
		MustNot.beNull(dashboardLink);
		MustNot.beNull(queueListLink);
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
		
		adjustForSocketStatus(context);
		
		User user = (User) context.user();
		
		addUserIdToValue.appendToAttribute("value", user.getId());

		adjustLoginMenu(user);
		adjustLinks(user);
	}

	private void adjustForSocketStatus(NtroContext<?, ?> context) {
		T.call(this);

		logoImage.display(context.isSocketOpen());
		logoLoading.display(!context.isSocketOpen());

		HtmlElement body = getRootElement().parents("body").get(0);
		if(body == null) {
			return;
		}

		if(context.isSocketOpen()) {

			body.css("cursor", "auto");
			body.find("a").forEach(e -> {
				e.removeClass("link-disabled");
				e.removeClass("disabled");
				e.css("cursor", "pointer");
			});
			body.find("button").forEach(e -> {
				e.removeClass("disabled");
				e.css("cursor", "pointer");
			});
			body.find("input").forEach(e -> {
				e.removeClass("disabled");
				e.css("cursor", "pointer");
			});
			
		}else {

			body.css("cursor", "wait");
			body.find("a").forEach(e -> {
				e.addClass("link-disabled");
				e.addClass("disabled");
				e.css("cursor", "wait");
			});
			body.find("button").forEach(e -> {
				e.addClass("disabled");
				e.css("cursor", "wait");
			});
			body.find("input").forEach(e -> {
				e.addClass("disabled");
				e.css("cursor", "wait");
			});
		}
	}

	private void adjustLinks(User user) {
		T.call(this);
		
		courseListLink.hide();
		semesterListLink.hide();
		groupListLink.hide();
		dashboardLink.hide();

		queueLink.hide();

		queueListLink.show();
		queueListLink.setAttribute("href", "/" + Constants.QUEUE_LIST_URL_SEGMENT);

		homeLink.show();

		if(user.getHasPassword()) {
			showPasswordMenuLink.text("Modifier mon mot de passe");
		}else {
			showPasswordMenuLink.text("Ajouter un mot de passe");
		}
	
		if(user.actsAsStudent()) {
			
			homeLink.setAttribute("href", "/" + Constants.QUEUE_LIST_URL_SEGMENT);

			toggleStudentModeButton.text("Mode enseignant");
			
			
		}else if(user.actsAsTeacher()){

			homeLink.setAttribute("href", "/" + Constants.QUEUE_URL_SEGMENT + "/" + user.getId());
			queueLink.setAttribute("href", "/" + Constants.QUEUE_URL_SEGMENT + "/" + user.getId());
			queueLink.show();

			toggleStudentModeButton.text("Mode étudiant");
		}
	}


	private void adjustLoginMenu(User user) {
		T.call(this);
		
		loginMenuMessage.hide();
		loginMenuEnterId.hide();
		loginMenuEnterPassword.hide();
		loginMenuEnterCode.hide();
		loginMenuAddPassword.hide();
		loginMenuUserProfile.hide();
		loginMenuNameContainer.hide();
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
			
			if(shouldEnterName(user)) {
				loginMenuNameContainer.show();
			}

		}else if(user.isStudent()) {

			loginButton.text(user.displayName());
			loginMenuUserProfile.show();
			userNameContainer.hide();
			toggleStudentModeContainer.hide();

		}else if(user.isTeacher() && !user.actsAsAdmin()) {

			adjustLoginMenuForTeacher(user);

		}else if(user.actsAsAdmin()) {
			
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

		}

	}
	
	private boolean shouldEnterName(User user) {
		T.call(this);

		return !user.actsAsTeacher() && !user.getHasName();
	}

	private void adjustLoginMenuForTeacher(User user) {
		T.call(this);

		String displayName = user.displayName();
		loginMenuUserProfile.show();
		userNameInput.value(displayName);
		toggleStudentModeContainer.show();
		loginButton.text(displayName);
		
		if(user.isAdmin()) {

			toggleAdminModeContainer.show();
			toggleAdminModeButton.removeClass("btn-secondary");
			toggleAdminModeButton.addClass("btn-danger");
		}
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
	public void showDashboard(Class<? extends NtroView> subViewClass, DashboardView dashboardView) {
		T.call(this);

		showSubView(subViewClass, dashboardView);
	}

	private void showSubView(Class<? extends NtroView> subViewClass, NtroView subView) {
		T.call(this);
		
		String subViewId = Ntro.introspector().getSimpleNameForClass(subView.getClass());
		
		NtroViewWeb subViewWeb = (NtroViewWeb) subView;

		NtroViewWeb existingSubView = (NtroViewWeb) findSubView(subViewClass, subViewId);
		
		// XXX: re-initialize subView on existing rootElement (if it exists in DOM)
		if(existingSubView != null) {
			
			HtmlElement subViewRootElement = existingSubView.getRootElement();
			
			NtroContext<?,?> context = AquiletourMain.createNtroContext();

			subViewWeb.setRootElement(subViewRootElement);

			subViewWeb.initializeView(context);
			
			Log.info("[showSubView] using existing rootElement");
			
			currentSubView = subViewWeb;

		} else {
			
			subViewWeb.getRootElement().setAttribute("id", subViewId);
			changeSubView(subView);
		}
	}

	private void changeSubView(NtroView subView) {
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
	public void showQueue(Class<? extends NtroView> subViewClass, QueueView queueView) {
		T.call(this);

		showSubView(subViewClass, queueView);
	}

	@Override
	public void showLogin(Class<? extends NtroView> subViewClass, LoginView loginView) {
		T.call(this);

		showSubView(subViewClass, loginView);

	}

	@Override
	public void showQueues(Class<? extends NtroView> subViewClass, QueueListView currentView) {
		T.call(this);

		showSubView(subViewClass, currentView);
	}

	@Override
	public void showHome(Class<? extends NtroView> subViewClass, HomeView homeView) {
		T.call(this);
		
		showSubView(subViewClass, homeView);
	}

	@Override
	public void showCourse(Class<? extends NtroView> subViewClass, CourseView courseView) {
		T.call(this);

		showSubView(subViewClass, courseView);
	}

	@Override
	public void showGitCommitList(Class<? extends NtroView> subViewClass, CommitListView gitCommitListView) {
		T.call(this);
		
		showSubView(subViewClass, gitCommitListView);
	}

	@Override
	public void showGitLateStudents(Class<? extends NtroView> subViewClass, LateStudentsView gitLateStudentsView) {
		T.call(this);
		
		showSubView(subViewClass, gitLateStudentsView);
		
	}

	@Override
	public void showGitStudentSummaries(Class<? extends NtroView> subViewClass, StudentSummariesView gitStudentSummariesView) {
		T.call(this);
		
		showSubView(subViewClass, gitStudentSummariesView);
		
	}

	@Override
	public void showSemesterList(Class<? extends NtroView> subViewClass, SemesterListView calendarListView) {
		T.call(this);

		showSubView(subViewClass, calendarListView);
	}

	@Override
	public void showCourseList(Class<? extends NtroView> subViewClass, CourseListView courseListView) {
		T.call(this);

		showSubView(subViewClass, courseListView);
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
	public void showGroupList(Class<? extends NtroView> subViewClass, GroupListView groupListView) {
		T.call(this);

		showSubView(subViewClass, groupListView);
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
