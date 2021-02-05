package ca.aquiletour.web.pages.settings;

import ca.aquiletour.core.pages.settings.SettingsView;
import ca.aquiletour.core.pages.settings.ShowSettingsMessage;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.MessageFactory;
import ca.ntro.web.dom.HtmlElement;
import ca.ntro.web.dom.HtmlEventListener;
import ca.ntro.web.mvc.NtroViewWeb;

public class SettingsViewWeb extends NtroViewWeb implements SettingsView {

	@Override
	public void initialize() {
		T.call(this);
		
		HtmlElement settingsLink = getRootElement().children("#settings-link").get(0);
		
		MustNot.beNull(settingsLink);
		
		settingsLink.addEventListener("onclick", new HtmlEventListener() {
			@Override
			public void onEvent() {
				T.call(this);
				
				ShowSettingsMessage showSettingsMessage = MessageFactory.getOutgoingMessage(ShowSettingsMessage.class);
				showSettingsMessage.sendMessage();
			}
		});
		
	}

}
