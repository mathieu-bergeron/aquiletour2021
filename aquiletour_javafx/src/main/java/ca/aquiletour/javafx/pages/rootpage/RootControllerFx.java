package ca.aquiletour.javafx.pages.rootpage;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.root.ShowDashboardTask;
import ca.aquiletour.core.pages.root.ShowSettingsTask;
import ca.aquiletour.core.pages.settings.SettingsController;
import ca.aquiletour.javafx.NtroWindowFx;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.ViewLoaderFx;
import javafx.stage.Stage;

public class RootControllerFx extends RootController {
	
	private NtroWindowFx window;

	public RootControllerFx(Stage primaryStage) {
		T.call(this);

		window = new NtroWindowFx(primaryStage);
	}

	@Override
	protected ViewLoader loadView(String lang) {
		T.call(this);

		return new ViewLoaderFx().setFxmlUrl("/views/rootpage/structure.xml")
				                 .setCssUrl("/views/rootpage/style.css")
				                 .setTranslationsName("i18n.strings");
	}

	@Override
	protected NtroWindow getWindow() {
		return window;
	}

	@Override
	public SettingsController createSettingsController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DashboardController createDashboardController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShowSettingsTask showSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShowDashboardTask showDashboard() {
		// TODO Auto-generated method stub
		return null;
	}
}
