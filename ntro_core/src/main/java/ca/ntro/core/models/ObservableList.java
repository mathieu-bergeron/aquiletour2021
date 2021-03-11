package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.Ntro;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.services.NtroCollections;
import ca.ntro.core.services.stores.ValuePath;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;

@SuppressWarnings("serial")
public abstract class ObservableList<I extends Object> extends ObservableProperty<List<I>> {
	
	private List<ListObserver<I>> listObservers = new ArrayList<>();
	
	private ModelStore modelStore;
	private ValuePath valuePath;
	
	public ObservableList() {
		T.call(this);

		setValue(NtroCollections.synchronizedList(new ArrayList<>()));
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
		
		for(ListObserver<I> listObserver : listObservers) {
			listObserver.onClearItems();
		}
	}
	
	
	public void addItem(I item) {
		T.call(this);
		getValue().add(item);
		
		List<Object> args = new ArrayList<>();
		args.add(item);

		modelStore.onValueMethodInvoked(valuePath,"addItem",item);

		for(ListObserver<I> listObserver : listObservers) {
			T.here();
			listObserver.onItemAdded(getValue().indexOf(item), item);
		}
	}

	public void removeItem(I item) {
		T.call(this);
		
		int index = getValue().lastIndexOf(item);
		
		getValue().remove(index);

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
