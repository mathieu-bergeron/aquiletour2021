package ca.ntro.core.services.stores;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;

public class ValuePath implements JsonSerializable {
	
	private DocumentPath documentPath = new DocumentPath();
	private List<String> fieldPath = new ArrayList<>();
	
	private ValuePath(String collection) {
		documentPath.setCollection(collection);
	}

	public static ValuePath collection(String collection) {
		T.call(ValuePath.class);
		return new ValuePath(collection);
	}
	
	public ValuePath documentId(String documentId) {
		T.call(this);
		documentPath.setDocumentId(documentId);
		return this;
	}
	
	public ValuePath field(String... fieldPath) {
		T.call(this);
		
		for(String fieldName : fieldPath) {
			this.fieldPath.add(fieldName);
		}

		return this;
	}
	
	public DocumentPath getDocumentPath() {
		T.call(this);
		return documentPath;
	}

	public void addFieldName(String fieldName) {
		T.call(this);
		
		this.fieldPath.add(fieldName);
	}
}
