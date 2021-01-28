package ca.aquiletour.javafx.pages.root;

import ca.aquiletour.core.pages.dashboard.DashboardController;
import ca.aquiletour.core.pages.dashboard.ShowDashboardTask;
import ca.aquiletour.core.pages.root.RootController;
import ca.aquiletour.core.pages.settings.SettingsController;
import ca.aquiletour.core.pages.settings.ShowSettingsTask;
import ca.aquiletour.javafx.NtroWindowFx;
import ca.aquiletour.javafx.pages.dashboard.DashboardControllerFx;
import ca.aquiletour.javafx.pages.settings.SettingsControllerFx;
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
	protected ViewLoader createViewLoader(String lang) {
		T.call(this);

		return new ViewLoaderFx().setFxmlUrl("/views/root/structure.xml")
				                 .setCssUrl("/views/root/style.css")
				                 .setTranslationsName("i18n.strings");
	}

	@Override
	protected NtroWindow getWindow() {
		return window;
	}

	@Override
	public SettingsController createSettingsController() {
		T.call(this);

		return new SettingsControllerFx(this);
	}

	@Override
	public DashboardController createDashboardController() {
		T.call(this);
		
		return new DashboardControllerFx(this);
	}
}
