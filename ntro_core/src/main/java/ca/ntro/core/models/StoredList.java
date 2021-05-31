package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.lambdas.Break;
import ca.ntro.core.models.lambdas.ListIterator;
import ca.ntro.core.models.lambdas.ListMatcher;
import ca.ntro.core.models.lambdas.ListReducer;
import ca.ntro.core.models.listeners.ClearItemsListener;
import ca.ntro.core.models.listeners.ItemAddedListener;
import ca.ntro.core.models.listeners.ItemRemovedListener;
import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

@SuppressWarnings("serial")   
public abstract class StoredList<I extends Object> extends StoredProperty<List<I>> { //implements NtroModelValue {
	                                                                                 // JSWEET??: need to redeclare here event if the superclass StoredValue implements it

	private List<ListObserver<I>> listObservers = new ArrayList<>();
	
	public StoredList() {
		T.call(this);

		setValue(Ntro.collections().synchronizedList(new ArrayList<>()));
	}
	
	public int size() {
		T.call(this);
		
		return getValue().size();
	}

	public int indexOf(I item) {
		T.call(this);

		return getValue().indexOf(item);
	}
	
	public I item(int id) {
		T.call(this);
		
		I item = null;

		synchronized (getValue()) {
			item = getValue().get(id);
		}

		return item;
	}

	public void insertItem(int index, I item) {
		T.call(this);
		
		getValue().add(index, item);
		
		if(ifStoredConnected()) {

			modelStore().updateStoreConnectionsByPath(valuePath().getDocumentPath());

			List<Object> args = new ArrayList<>();
			args.add(index);
			args.add(item);
			modelStore().onValueMethodInvoked(valuePath(),"insertItem",args);
			
		}
		
		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onItemAdded(index, item);
		}
	}

	public void updateItem(int index, I item) {
		
		getValue().set(index, item);

		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onItemUpdated(index, item);
		}
	}

	public void clearItems() {
		getValue().clear();
		
		List<Object> args = new ArrayList<>();
		
		if(ifStoredConnected()) {
			
			modelStore().onValueMethodInvoked(valuePath(),"clearItems",args);

		}

		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onClearItems();
		}
	}
	
	
	public void addItem(I item) {
		T.call(this);

		getValue().add(item);
		
		if(ifStoredConnected()) {

			modelStore().updateStoreConnectionsByPath(valuePath().getDocumentPath());
			
			List<Object> args = new ArrayList<>();
			args.add(item);
			modelStore().onValueMethodInvoked(valuePath(),"addItem",args);
			
		}

		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onItemAdded(getValue().indexOf(item), item);
		}
	}

	public void removeItem(I item) {
		T.call(this);
		
		int index = Ntro.collections().indexOfEquals(getValue(), item);
		
		if(index != -1) {
			removeItemAtIndex(index);
		}
	}

	public void removeItemAtIndex(int index) {
		T.call(this);
		
		I item = item(index);

		getValue().remove(index);
		
		if(ifStoredConnected()) {

			modelStore().updateStoreConnectionsByPath(valuePath().getDocumentPath());

			List<Object> args = new ArrayList<>();
			args.add(index);
			modelStore().onValueMethodInvoked(valuePath(),"removeItemAtIndex",args);

		}

		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onItemRemoved(index, item);
		}
	}
	
	public void removeObserver(ListObserver<I> listObserver) {
		T.call(this);
		
		boolean removed = this.listObservers.remove(listObserver);
		//MustNot.beTrue(!removed);
	}

	public void removeObservers() {
		T.call(this);
		
		this.listObservers.clear();
	}

	public void onItemAdded(ItemAddedListener<I> listener) {
		T.call(this);
		
		observe(new ListObserver<I>() {

			@Override
			public void onValueChanged(List<I> oldValue, List<I> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValue(List<I> value) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeleted(List<I> lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemAdded(int index, I item) {
				T.call(this);

				listener.onItemAdded(index, item);
			}

			@Override
			public void onItemUpdated(int index, I item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onItemRemoved(int index, I item) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onClearItems() {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void onClearItems(ClearItemsListener listener) {
		T.call(this);
		
		observe(new ListObserver<I>() {

			@Override
			public void onValueChanged(List<I> oldValue, List<I> value) {
			}

			@Override
			public void onValue(List<I> value) {
			}

			@Override
			public void onDeleted(List<I> lastValue) {
			}

			@Override
			public void onItemAdded(int index, I item) {
			}

			@Override
			public void onItemUpdated(int index, I item) {
			}

			@Override
			public void onItemRemoved(int index, I item) {
			}

			@Override
			public void onClearItems() {
				listener.onClearItems();
			}
		});
	}

	public void observe(ListObserver<I> listObserver) {
		T.call(this);
		
		this.listObservers.add(listObserver);
		
		List<I> list = getValue();
		
		if(list != null) {
			
			synchronized (list) {
				for(int i = 0; i < list.size(); i++) {
					listObserver.onItemAdded(i, list.get(i));
				}
			}
		}

		//super.observe(listObserver);
	}

	public boolean contains(I item) {
		T.call(this);

		boolean contains = false;

		synchronized (getValue()) {
			contains = Ntro.collections().listContainsEquals(getValue(), item);
		}

		return contains;
	}

	public void onItemRemoved(ItemRemovedListener<I> listener) {
		T.call(this);
		
		observe(new ListObserver<I>() {
			@Override
			public void onValueChanged(List<I> oldValue, List<I> value) {
			}

			@Override
			public void onValue(List<I> value) {
			}

			@Override
			public void onDeleted(List<I> lastValue) {
			}

			@Override
			public void onItemAdded(int index, I item) {
			}

			@Override
			public void onItemUpdated(int index, I item) {
			}

			@Override
			public void onItemRemoved(int index, I item) {
				listener.onItemRemoved(index, item);
			}

			@Override
			public void onClearItems() {
			}
		});
	}

	public boolean isEmpty() {
		T.call(this);

		return size() == 0;
	}
	
	public void forEachItem(ListIterator<I> lambda) {
		T.call(this);

		synchronized (getValue()) {
			for(int i = 0; i < size(); i++) {
				try {

					lambda.on(i, item(i));

				}catch(Break b) {
					break;
				}
			}
		}
	}
	
	public <R extends Object> R reduceTo(Class<R> accumlatorClass, R accumulator, ListReducer<R,I> reducer) {
		T.call(this);

		synchronized (getValue()) {
			for(int i = 0; i < size(); i++) {
				try {

					accumulator = reducer.reduce(i, item(i), accumulator);

				}catch(Break b) {
					break;
				}
			}
		}

		return accumulator;
	}

	public I findFirst(Class<I> itemClass, ListMatcher<I> matcher) {
		T.call(this);

		return reduceTo(itemClass, null, (index, item, accumulator) ->{
			if(accumulator != null) {
				throw new Break();
			}
			
			if(matcher.match(index, item)) {
				accumulator = item;
			}
			
			return accumulator;
		});
	}
	
}
