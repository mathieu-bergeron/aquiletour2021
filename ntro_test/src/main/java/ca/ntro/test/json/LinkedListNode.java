package ca.ntro.test.json;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.services.Ntro;

public class LinkedListNode implements JsonSerializable {
	
	private LinkedListNode next;
	/*
	private List<LinkedListNode> nextList = new ArrayList<>();
	private Map<String, LinkedListNode> nextMap = new HashMap<>();
	*/
	private String value;

	public LinkedListNode() {
	}
	
	public LinkedListNode(String value) {
		this.value = value;
	}

	public LinkedListNode getNext() {
		return next;
	}

	public void setNext(LinkedListNode next) {
		this.next = next;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/*
	public List<LinkedListNode> getNextList() {
		return nextList;
	}

	public void setNextList(List<LinkedListNode> nextList) {
		this.nextList = nextList;
	}

	public Map<String, LinkedListNode> getNextMap() {
		return nextMap;
	}

	public void setNextMap(Map<String, LinkedListNode> nextMap) {
		this.nextMap = nextMap;
	}*/

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof LinkedListNode) {
			LinkedListNode otherNode = (LinkedListNode) other;
			return value.equals(otherNode.value);
		}

		return false;
	}
	
	@Override
	public String toString() {
		return Ntro.introspector().getSimpleNameForClass(getClass());
	}

}
