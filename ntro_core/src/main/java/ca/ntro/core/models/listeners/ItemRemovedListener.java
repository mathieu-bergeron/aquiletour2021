package ca.ntro.core.models.listeners;

public interface ItemRemovedListener<I extends Object>{
	
	void onItemRemoved(int index, I item);

}
