package ca.ntro.web;

import java.util.Map;

import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.users.NtroUser;

public interface NtroRouter<U extends NtroUser> {
	
	void route(Path path, Map<String,String[]> parameters, NtroContext<U> context);

}
