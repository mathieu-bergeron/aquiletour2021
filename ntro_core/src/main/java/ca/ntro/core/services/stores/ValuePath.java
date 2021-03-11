package ca.ntro.core.services.stores;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;

public class ValuePath implements JsonSerializable {
	
	// JSWEET: adding a default constructor to DocumentPath leads to invalid overload error
	private DocumentPath documentPath = new DocumentPath(null,null);
	private List<String> fieldPath = new ArrayList<>();

	private ValuePath() {
	}
	
	private ValuePath(String collection) {
		documentPath.setCollection(collection);
	}

	public static ValuePath forCollection(String collection) {
		T.call(ValuePath.class);
		return new ValuePath(collection);
	}
	
	public ValuePath setDocumentId(String documentId) {
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

	public void setDocumentPath(DocumentPath documentPath) {
		T.call(this);
		this.documentPath = documentPath;
	}

	public void addFieldName(String fieldName) {
		T.call(this);
		
		fieldPath.add(fieldName);
	}

	private void setFieldPath(List<String> fieldPath) {
		T.call(this);

		this.fieldPath = fieldPath;
	}
	
	public ValuePath clone() {
		
		ValuePath clone = new ValuePath();
		clone.setDocumentPath(documentPath);
		for(String fieldName : fieldPath) {
			clone.addFieldName(fieldName);
		}

		return clone;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(documentPath.toString());
		for(String fieldName : fieldPath) {
			builder.append("/");
			builder.append(fieldName);
		}
		
		return builder.toString();
	}
	
}
