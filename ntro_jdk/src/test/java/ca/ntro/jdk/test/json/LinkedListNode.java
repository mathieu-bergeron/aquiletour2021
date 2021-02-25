package ca.ntro.jdk.test.json;

import java.io.Serializable;

import ca.ntro.core.json.JsonObjectIO;

public class LinkedListNode extends JsonObjectIO implements Serializable {
	private static final long serialVersionUID = 2667416858048644003L;
	
	private LinkedListNode next;

	public LinkedListNode getNext() {
		return next;
	}

	public void setNext(LinkedListNode next) {
		this.next = next;
	}
}
