package ca.ntro.jdk.test.json;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.json.JsonObjectIO;

public class MyList extends JsonObjectIO {
	private static final long serialVersionUID = 8669571506241213302L;

	private List<ListItem> value = new ArrayList<>();

	public List<ListItem> getValue() {
		return value;
	}

	public void setValue(List<ListItem> value) {
		this.value = value;
	}
	
	@Override 
	public int hashCode() {
		return value.hashCode();
	}

	@Override 
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof MyList) {
			MyList otherList = (MyList) other;
			return value.equals(otherList.value);
		}
		return false;
	}

}
