package ca.ntro.jsweet.test.json;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.json.JsonObjectIO;

public class MyList extends JsonObjectIO {
	private static final long serialVersionUID = 8669571506241213302L;

	private List<ListItem> list = new ArrayList<>();

	public List<ListItem> getList() {
		return list;
	}

	public void setList(List<ListItem> list) {
		this.list = list;
	}

	@Override 
	public int hashCode() {
		return list.hashCode();
	}

	@Override 
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof MyList) {
			MyList otherList = (MyList) other;
			return list.equals(otherList.list);
		}
		return false;
	}

}
