package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;

public abstract class ObservableProperty<V extends Object> extends StoreConnectedValue implements JsonSerializable {
	
	private V value;

	private ValueListener<V> valueListener;
	private DeletionListener<V> deletionListener;
	private List<ValueObserver<V>> observers = new ArrayList<>();

	public ObservableProperty() {
		T.call(this);
	}

	public ObservableProperty(V value) {
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
		
		valueListener.onValue(value);
	}

	public void onDeleted(DeletionListener<V> deletionListener) {
		// TODO
	}

	public void set(V newValue) {
		T.call(this);

		V oldValue = value;
		value = newValue;
		
		for(ValueObserver<V> observer : observers) {
			observer.onValueChanged(oldValue, newValue);
		}
	}
	
	public void observe(ValueObserver<V> observer) {
		T.call(this);

		this.observers.add(observer);
		observer.onValue(value);
	}

}
