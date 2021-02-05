package ca.ntro.core.json;

import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.tasks.NtroTaskAsync;

public abstract class  JsonLoader extends NtroTaskAsync {
	
	public abstract JsonObject getJsonObject();
	public abstract DocumentPath getDocumentPath();

}
