package ca.ntro.web;

import ca.ntro.NtroApp;

public interface NtroAppWeb extends NtroApp {
	
	void registerViews(ViewRegistrarWeb registrar);
	void registerRouters(RouterRegistrar registrar);

}
