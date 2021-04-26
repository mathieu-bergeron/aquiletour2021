package ca.ntro.core.json;

import ca.ntro.core.tasks.NtroTaskAsync;

public abstract class  JsonLoader extends NtroTaskAsync {
	
	public abstract String getJsonString();

}
