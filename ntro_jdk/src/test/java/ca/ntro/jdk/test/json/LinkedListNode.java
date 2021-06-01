package ca.ntro.jdk.test.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ca.ntro.core.json.JsonObjectIO;

public class LinkedListNode extends JsonObjectIO implements Serializable {
	private static final long serialVersionUID = 2667416858048644003L;
	
	private LinkedListNode next;
	private List<LinkedListNode> nextList = new ArrayList<>();
	private Map<String, LinkedListNode> nextMap = new HashMap<>();
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
	}

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

}
