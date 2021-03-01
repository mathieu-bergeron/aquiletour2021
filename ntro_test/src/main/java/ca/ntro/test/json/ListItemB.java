package ca.ntro.test.json;

import ca.ntro.core.json.JsonObjectIO;

public class ListItemB extends JsonObjectIO implements ListItem {

	private int propB = 1;

	public int getPropB() {
		return propB;
	}

	public void setPropB(int probB) {
		this.propB = probB;
	}

	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof ListItemB) {
			ListItemB otherItem = (ListItemB) other;
			return propB == otherItem.propB;
		}
		return false;
	}
}
