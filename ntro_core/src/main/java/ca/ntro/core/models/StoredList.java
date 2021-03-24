package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.listeners.ListObserver;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

@SuppressWarnings("serial")   
public class StoredList<I extends Object> extends StoredProperty<List<I>> {          //implements NtroModelValue {
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
		return getValue().get(id);
	}

	public void insertItem(int index, I item) {
		T.call(this);
		
		getValue().add(index, item);

		List<Object> args = new ArrayList<>();
		args.add(index);
		args.add(item);
		
		modelStore().onValueMethodInvoked(valuePath(),"insertItem",args);
		
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

		modelStore().onValueMethodInvoked(valuePath(),"clearItems",args);

		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onClearItems();
		}
	}
	
	
	public void addItem(I item) {
		T.call(this);
		getValue().add(item);
		
		List<Object> args = new ArrayList<>();
		args.add(item);
		
		modelStore().onValueMethodInvoked(valuePath(),"addItem",args);

		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onItemAdded(getValue().indexOf(item), item);
		}
	}

	public void removeItem(I item) {
		T.call(this);
		
		int index = Ntro.collections().indexOfEquals(getValue(), item);

		getValue().remove(index);
		
		List<Object> args = new ArrayList<>();
		args.add(item);

		modelStore().onValueMethodInvoked(valuePath(),"removeItem",args);

		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onItemRemoved(index, item);
		}
	}
	
	public void removeObserver(ListObserver<I> listObserver) {
		T.call(this);
		
		boolean removed = this.listObservers.remove(listObserver);
		//MustNot.beTrue(!removed);
	}

	public void removeAllObservers() {
		T.call(this);
		
		this.listObservers.clear();
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
}
