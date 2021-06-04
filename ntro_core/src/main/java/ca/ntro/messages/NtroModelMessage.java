package ca.ntro.messages;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.models.NtroModel;
import ca.ntro.stores.DocumentPath;

public interface NtroModelMessage extends JsonSerializable {

	DocumentPath documentPath();
	Class<? extends NtroModel> targetClass();

}
