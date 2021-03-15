package ca.ntro.core.json;

import ca.ntro.core.tasks.NtroTaskAsync;
import ca.ntro.stores.DocumentPath;

public abstract class  JsonLoader extends NtroTaskAsync {
	
	public abstract String getJsonString();
	public abstract DocumentPath getDocumentPath();

}
