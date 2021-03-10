package ca.ntro.core.models;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import ca.ntro.core.Ntro;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.services.stores.DocumentPath;
import ca.ntro.core.services.stores.ValuePath;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

/**
 *
 * The properties of a Model must be:
 * - Java values: String, Double, List<String>, etc.
 * - User-defined classes that implements JsonSerializable
 *
 * @author mbergeron
 *
 */
public abstract class NtroModel implements JsonSerializable {

	private String modelId;
	private ModelStore modelStore;

	public void save() {
		T.call(this);

		modelStore.saveJsonString(documentPath(), Ntro.jsonService().toString(this));
	}

	public void updateOnceFrom(ModelStore store, String modelId, UpdateListener listener) {


	}

	public void synchronizeWith(ModelStore store, String modelId) {
		// XXX: only synchronizes Synchronized properties/map/list


	}


	private DocumentPath documentPath() {
		return new DocumentPath(Ntro.introspector().getSimpleNameForClass(getClass()), modelId);
	}

	// XXX: cannot be protected as it should
	//      not be used by subclasses
	void setModelId(String id) {
		T.call(this);

		this.modelId = id;
	}

	void setOrigin(ModelStore origin) {
		T.call(this);

		this.modelStore = origin;
	}

	public abstract void initializeStoredValues();

	public void connectStoredValues() {
		T.call(this);

		// FIXME: this inserts the wrong value in a StoredString
		//        (it inserts a StoredString instead of a String)
		for(Method getter : Ntro.introspector().userDefinedGetters(this)) {

			String fieldName = Ntro.introspector().fieldNameForGetter(getter);

			Object fieldValue = null;

			try {

				fieldValue = getter.invoke(this);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				Log.fatalError("[Model] Cannot invoke getter " + getter.getName(), e);

			}

			if(fieldValue instanceof StoreConnectable) {

				ValuePath valuePath = ValuePath.collection(Ntro.introspector().getSimpleNameForClass(this.getClass())).documentId(modelId).field(fieldName);

				((StoreConnectable) fieldValue).connectToStore(valuePath, modelStore);
			}
		}
	}

	public void onUpdate(String valueName, String methodName, List<Object> args) {

	}

}
