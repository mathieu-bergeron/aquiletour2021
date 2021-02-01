package ca.ntro.core.json;

import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.tasks.NtroTaskImpl;

public abstract class  JsonLoader extends NtroTaskImpl {
	
	public abstract JsonObject getJsonObject();
	public abstract DocumentPath getDocumentPath();

}
