package ca.ntro.web;

import ca.ntro.core.NtroUser;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;

public interface NtroRouter<U extends NtroUser> {
	
	void route(Path path, NtroContext<U> context);

}
