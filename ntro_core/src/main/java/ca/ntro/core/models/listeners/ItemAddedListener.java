package ca.ntro.core.models.listeners;

public interface ItemAddedListener<I extends Object>{
	
	void onItemAdded(int index, I item);

}
