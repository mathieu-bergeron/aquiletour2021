package ca.ntro.messages.ntro_messages;

import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.messages.NtroUserMessage;

public class GetModelNtroMessage extends NtroUserMessage {
	
	private DocumentPath documentPath;

	public DocumentPath getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(DocumentPath documentPath) {
		this.documentPath = documentPath;
	}
}
