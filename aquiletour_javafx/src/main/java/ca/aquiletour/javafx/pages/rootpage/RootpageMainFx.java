package ca.aquiletour.javafx.pages.rootpage;

import ca.aquiletour.core.pages.rootpage.RootpageController;
import ca.aquiletour.javafx.NtroWindowFx;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.ViewLoaderFx;
import javafx.stage.Stage;

public class RootpageMainFx extends RootpageController {
	
	private NtroWindowFx window;

	public RootpageMainFx(String lang, Stage primaryStage) {
		super(lang);
		
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
}
