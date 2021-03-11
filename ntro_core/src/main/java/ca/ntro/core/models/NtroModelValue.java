package ca.ntro.core.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ca.ntro.core.Ntro;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.services.stores.ValuePath;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;


/**
 * Every appointment-defined value inside a model
 * must be a subclass of ModelValue
 * 
 * @author mbergeron
 *
 */
public abstract class NtroModelValue extends StoreConnectedValue implements JsonSerializable {


	private void connectSubValues(ValuePath valuePath, ModelStore modelStore) {
		T.call(this);
		
		for(Method getter : Ntro.introspector().userDefinedGetters(this)) {
			
			String fieldName = Ntro.introspector().fieldNameForGetter(getter);
			
			Object fieldValue = null;
			
			try {

				fieldValue = getter.invoke(this);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				
				Log.fatalError("[ModelValue] Cannot invoke getter " + getter.getName(), e);
				
			}
			
			if(fieldValue instanceof StoreConnectedValue) {
				
				valuePath.addFieldName(fieldName);
				
				//((StoreConnectable) fieldValue).connectToStore(valuePath, modelStore);
			}
		}
	}
}
