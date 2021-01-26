package ca.ntro.core.mvc;

import ca.ntro.core.mvc.view.ViewLoader;

public abstract class NtroWindow<VL extends ViewLoader> {

	public abstract void installRootView(VL viewLoader);
	
}
