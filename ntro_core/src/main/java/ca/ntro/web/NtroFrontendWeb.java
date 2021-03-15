package ca.ntro.web;

import ca.ntro.NtroFrontend;

public interface NtroFrontendWeb extends NtroFrontend {
	
	void registerViews(ViewRegistrarWeb registrar);
	void registerRouters(RouterRegistrar registrar);

}
