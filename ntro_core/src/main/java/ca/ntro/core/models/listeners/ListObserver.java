package ca.ntro.core.models.listeners;

import java.util.List;

public interface ListObserver<I extends Object> extends ValueObserver<List<I>>, ItemAddedListener<I>, ItemUpdatedListener<I>, ItemRemovedListener<I>, ClearItemsListener {

}
