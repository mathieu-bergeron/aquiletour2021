package ca.ntro.stores;

import ca.ntro.core.json.JsonSerializable;

public class DocumentPath implements JsonSerializable {
	
	private String collection;
	private String documentId;

	public String getCollection() {
		return collection;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}
	
	public DocumentPath cloneModelValue() {
		DocumentPath clone = new DocumentPath();

		clone.setCollection(collection);
		clone.setDocumentId(documentId);

		return clone;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(collection);
		builder.append("/");
		builder.append(documentId);
		
		return builder.toString();
	}

	public ValuePath toValuePath() {
		return ValuePath.forCollection(getCollection()).setDocumentId(getDocumentId());
	}
	
	@Override
	public int hashCode() {
		//MustNot.beNull(collection);
		//MustNot.beNull(documentId);
		
		int code = 0;
		if(collection != null) {
			code += collection.hashCode();
		}
		if(documentId != null) {
			code += documentId.hashCode();
		}

		return code;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof DocumentPath) {
			DocumentPath otherPath = (DocumentPath) other;
			
			if(collection == null && otherPath.collection != null) return false;
			if(documentId == null && otherPath.documentId != null) return false;
			
			if(collection != null && !(collection.equals(otherPath.collection))) return false;
			if(documentId != null && !(documentId.equals(otherPath.documentId))) return false;
			
			return true;
		}
		return false;
	}

}
