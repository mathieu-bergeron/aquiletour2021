package ca.ntro.test.json;


public class ListItemA implements ListItem {

	private int propA = 0;

	public int getPropA() {
		return propA;
	}

	public void setPropA(int propA) {
		this.propA = propA;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other == this) return true;
		if(other instanceof ListItemA) {
			ListItemA otherItem = (ListItemA) other;
			return propA == otherItem.propA;
		}
		return false;
	}
}
