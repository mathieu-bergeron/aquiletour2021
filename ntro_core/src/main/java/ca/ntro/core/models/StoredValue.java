package ca.ntro.core.models;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.services.stores.ValuePath;

public abstract class StoredValue implements JsonSerializable {
	
	private ValuePath valuePath;
	private ModelStore modelStore;
	
	
	ValuePath valuePath() {
		return valuePath;
	}

	// FIXME: this should be package-private
	//        and have "ModelWalker" in the models package
	//        then JsonSerialization can use the ModelWalker
	public void setValuePath(ValuePath valuePath) {
		System.out.println(valuePath.toString());
		this.valuePath = valuePath;
	}

	ModelStore modelStore() {
		return modelStore;
	}

	// FIXME: this should be package-private
	//        and have "ModelWalker" in the models package
	//        then JsonSerialization can use the ModelWalker
	public void setModelStore(ModelStore modelStore) {
		this.modelStore = modelStore;
	}
}
