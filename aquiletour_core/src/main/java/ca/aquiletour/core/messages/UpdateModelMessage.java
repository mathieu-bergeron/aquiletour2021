package ca.aquiletour.core.messages;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.messages.NtroMessage;

public class UpdateModelMessage extends NtroMessage implements JsonSerializable {
	
	private DocumentPath documentPath;
	private NtroModel model;
	
	
	public UpdateModelMessage(DocumentPath documentPath, NtroModel model) {
		this.documentPath = documentPath;
		this.model = model;
	}

	public DocumentPath getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(DocumentPath documentPath) {
		this.documentPath = documentPath;
	}

	public NtroModel getModel() {
		return model;
	}

	public void setModel(NtroModel model) {
		this.model = model;
	}
}
