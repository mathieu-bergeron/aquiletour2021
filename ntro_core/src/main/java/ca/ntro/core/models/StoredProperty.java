package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.listeners.DeletionListener;
import ca.ntro.core.models.listeners.ValueListener;
import ca.ntro.core.models.listeners.ValueObserver;
import ca.ntro.core.system.trace.T;

public abstract class StoredProperty<V extends Object> extends StoredValue {

	private V value;

	private List<ValueObserver<V>> observers = new ArrayList<>();

	public StoredProperty() {
		T.call(this);
	}

	public StoredProperty(V value) {
		T.call(this);
		
		this.value = value;
	}
	
	// FIXME: this should be package-private
	//        and have "ModelWalker" in the models package
	//        then JsonSerialization can use the ModelWalker
	public V getValue() {
		T.call(this);
		
		return value;
	}

	// FIXME: this should be package-private
	//        and have "ModelWalker" in the models package
	//        then JsonSerialization can use the ModelWalker
	public void setValue(V value) {
		T.call(this);
		
		this.value = value;
	}
	
	
	public void get(ValueListener<V> valueListener) {
		T.call(this);
		
		valueListener.onValue(getValue());
	}

	public void onDeleted(DeletionListener<V> deletionListener) {
		// TODO
	}

	public void set(V newValue) {
		T.call(this);

		V oldValue = value;
		value = newValue;

		if(ifStoredConnected()) {

			List<Object> args = new ArrayList<>();
			args.add(newValue);
			modelStore().onValueMethodInvoked(valuePath(),"set",args);

		}
		
		for(ValueObserver<V> observer : observers) {
			observer.onValueChanged(oldValue, newValue);
		}
	}

	
	public void observe(ValueObserver<V> observer) {
		T.call(this);

		this.observers.add(observer);
		observer.onValue(getValue());
	}

	public void removeObservers() {
		T.call(this);
		
		this.observers.clear();
	}
}
