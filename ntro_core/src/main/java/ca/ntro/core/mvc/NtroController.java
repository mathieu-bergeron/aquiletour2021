package ca.ntro.core.mvc;

import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.tasks.NtroTaskImpl;

public abstract class NtroController extends NtroTaskImpl {

	protected abstract ViewLoader loadView(String lang);

}
