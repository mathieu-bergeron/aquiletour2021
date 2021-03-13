package ca.ntro;

import ca.ntro.web.RouterRegistrar;

public interface NtroBackend {

	void registerHandlers(HandlerRegistrar registrar);
	void registerRouters(RouterRegistrar registrar);

}
