package ca.ntro.core.services.stores;

import java.util.List;

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
	
	public DocumentPath clone() {
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
		return collection.hashCode() + documentId.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof DocumentPath) {
			DocumentPath otherPath = (DocumentPath) other;

			return collection.equals(otherPath.collection)  
					&& documentId.equals(otherPath.documentId);
		}
		return false;
	}

}
