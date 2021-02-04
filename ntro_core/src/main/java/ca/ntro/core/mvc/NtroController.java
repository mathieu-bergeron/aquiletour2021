package ca.ntro.core.mvc;

import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.tasks.NtroTaskSync;

public abstract class NtroController extends NtroTaskSync {

	protected abstract ViewLoader createViewLoader(String lang);

}
