package ca.ntro.core.models;

public interface ItemAddedListener<I extends Object>{
	
	void onItemAdded(int index, I item);

}
