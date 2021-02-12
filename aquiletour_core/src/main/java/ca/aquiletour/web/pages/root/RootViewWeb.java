package ca.aquiletour.web.pages.root;

import ca.aquiletour.core.pages.dashboard.DashboardView;
import ca.aquiletour.core.pages.dashboard.messages.ShowDashboardMessage;
import ca.aquiletour.core.pages.queue.QueueView;
import ca.aquiletour.core.pages.root.RootView;
import ca.aquiletour.core.pages.settings.SettingsView;
import ca.aquiletour.core.pages.settings.ShowSettingsMessage;
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

		HtmlElement settingsLink = getRootElement().find("#settings-link").get(0);
		HtmlElement dashboardLink = getRootElement().find("#dashboard-link").get(0);

		MustNot.beNull(settingsLink);

		settingsLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				ShowSettingsMessage showSettingsMessage = MessageFactory.getOutgoingMessage(ShowSettingsMessage.class);
				showSettingsMessage.sendMessage();
			}
		});

		dashboardLink.addEventListener("click", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);

				ShowDashboardMessage showDashboardMessage = MessageFactory.getOutgoingMessage(ShowDashboardMessage.class);
				showDashboardMessage.sendMessage();
			}
		});
	}

	@Override
	public void showSettings(SettingsView settingsView) {
		T.call(this);

		showSubView(settingsView);
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



}
