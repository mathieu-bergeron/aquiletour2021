package ca.ntro.core.models.listeners;

public interface ItemUpdatedListener<I extends Object>{

	void onItemUpdated(int index, I item);

}
