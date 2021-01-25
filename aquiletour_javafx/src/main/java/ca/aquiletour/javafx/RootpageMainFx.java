package ca.aquiletour.javafx;

import ca.aquiletour.core.rootpage.RootpageMain;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.mvc.ViewLoaderFx;

public class RootpageMainFx extends RootpageMain {

	public RootpageMainFx(String lang) {
		super(lang);
	}

	@Override
	protected ViewLoader loadView(String lang) {
		T.call(this);

		return new ViewLoaderFx().setFxmlUrl("/views/rootpage/structure.xml")
				                 .setCssUrl("/views/rootpage/style.css")
				                 .setTranslationsName("i18.strings");
	}

	@Override
	protected NtroWindow getWindow() {
		return new NtroWindowFx();
	}

}
