package ca.ntro.core.models;

import ca.ntro.core.services.stores.ValuePath;

public abstract class StoreConnectedValue {
	
	private ValuePath valuePath;
	private ModelStore modelStore;
	
	
	ValuePath getValuePath() {
		return valuePath;
	}

	void setValuePath(ValuePath valuePath) {
		this.valuePath = valuePath;
	}

	ModelStore getModelStore() {
		return modelStore;
	}

	void setModelStore(ModelStore modelStore) {
		this.modelStore = modelStore;
	}
}
