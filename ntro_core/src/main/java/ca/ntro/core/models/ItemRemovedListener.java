package ca.ntro.core.models;

public interface ItemRemovedListener<I extends Object>{
	
	void onItemRemoved(int index, I item);

}
