package ca.aquiletour.web;

import ca.aquiletour.core.pages.dashboard.CourseSummaryView;
import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.login.LoginView;
import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.settings.SettingsView;
import ca.aquiletour.core.pages.users.UserView;
import ca.aquiletour.core.pages.users.UsersView;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.aquiletour.web.pages.dashboard.DashboardViewWeb;
import ca.aquiletour.web.pages.login.LoginViewWeb;
import ca.aquiletour.web.pages.queue.AppointmentViewWeb;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.aquiletour.web.pages.root.RootViewWeb;
import ca.aquiletour.web.pages.settings.SettingsViewWeb;
import ca.aquiletour.web.pages.users.UserViewWeb;
import ca.aquiletour.web.pages.users.UsersViewWeb;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.ViewLoaders;
import ca.ntro.core.system.trace.T;

public class ViewLoaderRegistrationWeb {
	
	public static void registerViewLoaders() {
		T.call(ViewLoaderRegistrationWeb.class);
		
		ViewLoaders.registerViewLoader(RootView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/root/root.html")
			     	.setCssUrl("/views/root/root.css")
			     	.setTranslationsUrl("/i18n/fr/strings.json")
			     	.setTargetClass(RootViewWeb.class));

		ViewLoaders.registerViewLoader(DashboardView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/dashboard/dashboard.html")
			     	.setCssUrl("/views/dashboard/dashboard.css")
			     	.setTranslationsUrl("/i18n/fr/strings.json")
			     	.setTargetClass(DashboardViewWeb.class));

		ViewLoaders.registerViewLoader(CourseSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course_summary/course_summary.html")
			     	.setCssUrl("/views/course_summary/course_summary.css")
			     	.setTranslationsUrl("/i18n/fr/strings.json")
			     	.setTargetClass(CourseSummaryViewWeb.class));

		ViewLoaders.registerViewLoader(QueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queue/queue.html")
			     	.setCssUrl("/views/queue/queue.css")
			     	.setTranslationsUrl("/i18n/fr/strings.json")
			     	.setTargetClass(QueueViewWeb.class));

		ViewLoaders.registerViewLoader(AppointmentView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/appointment/appointment.html")
			     	.setCssUrl("/views/appointment/appointment.css")
			     	.setTranslationsUrl("/i18n/fr/strings.json")
			     	.setTargetClass(AppointmentViewWeb.class));
		
		ViewLoaders.registerViewLoader(UsersView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/users/users.html")
			     	.setCssUrl("/views/users/users.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(UsersViewWeb.class));

		ViewLoaders.registerViewLoader(UserView.class,
				"fr"
				, Ntro.viewLoaderWeb()
				.setHtmlUrl("/views/user/user.html")
				.setCssUrl("/views/user/user.css")
				.setTranslationsUrl("/i18n/fr/string.json")
				.setTargetClass(UserViewWeb.class));
		
		ViewLoaders.registerViewLoader(LoginView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/login/login.html")
			     	.setCssUrl("/views/login/login.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(LoginViewWeb.class));

	}
}
