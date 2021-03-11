package ca.ntro.core.models;

public interface ItemUpdatedListener<I extends Object>{

	void onItemUpdated(int index, I item);

}
