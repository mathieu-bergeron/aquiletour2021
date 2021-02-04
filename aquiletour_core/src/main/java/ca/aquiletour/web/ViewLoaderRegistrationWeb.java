package ca.aquiletour.web;

import ca.aquiletour.core.pages.dashboard.CourseSummaryView;
import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.queue.AppointmentView;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.settings.SettingsView;
import ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb;
import ca.aquiletour.web.pages.dashboard.DashboardViewWeb;
import ca.aquiletour.web.pages.queue.AppointmentViewWeb;
import ca.aquiletour.web.pages.queue.QueueViewWeb;
import ca.aquiletour.web.pages.root.RootViewWeb;
import ca.aquiletour.web.pages.settings.SettingsViewWeb;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.ViewLoaders;
import ca.ntro.core.system.trace.T;

public class ViewLoaderRegistrationWeb {
	
	public static void registerViewLoaders() {
		T.call(ViewLoaderRegistrationWeb.class);
		
		ViewLoaders.registerViewLoader(RootView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/root/structure.html")
			     	.setCssUrl("/views/root/style.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(RootViewWeb.class));

		ViewLoaders.registerViewLoader(SettingsView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/settings/structure.html")
			     	.setCssUrl("/views/settings/style.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(SettingsViewWeb.class));

		ViewLoaders.registerViewLoader(DashboardView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/dashboard/structure.html")
			     	.setCssUrl("/views/dashboard/style.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(DashboardViewWeb.class));

		ViewLoaders.registerViewLoader(CourseSummaryView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/course_summary/structure.html")
			     	.setCssUrl("/views/course_summary/style.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(CourseSummaryViewWeb.class));

		ViewLoaders.registerViewLoader(QueueView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/queue/structure.html")
			     	.setCssUrl("/views/queue/style.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(QueueViewWeb.class));

		ViewLoaders.registerViewLoader(AppointmentView.class,
				"fr"
				, Ntro.viewLoaderWeb()
			     	.setHtmlUrl("/views/appointment/structure.html")
			     	.setCssUrl("/views/appointment/style.css")
			     	.setTranslationsUrl("/i18n/fr/string.json")
			     	.setTargetClass(AppointmentViewWeb.class));

	}
}
