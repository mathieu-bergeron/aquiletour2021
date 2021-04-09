package ca.ntro.web;

import ca.ntro.NtroBackend;

public interface NtroBackendWeb extends NtroBackend {

	void registerRouters(RouterRegistrar registrar);

}
