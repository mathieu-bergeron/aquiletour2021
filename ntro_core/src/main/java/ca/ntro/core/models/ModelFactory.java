package ca.ntro.core.models;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.ModelStore;
import ca.ntro.services.Ntro;
import ca.ntro.services.NtroCollections;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ValuePath;

public class ModelFactory {

	public static NtroModel createModel(Class<? extends NtroModel> modelClass, 
			                            ModelStore modelStore, 
			                            DocumentPath documentPath,
			                            String jsonString) {
		T.call(ModelFactory.class);
		
		NtroModel model = Ntro.jsonService().fromString(modelClass, jsonString);
		
		modelStore.registerModel(documentPath, model);

		initializeStoreConnections(model, modelStore, documentPath.toValuePath(), new HashSet<>());
		
		return model;
	}

	@SuppressWarnings("unchecked")
	private static void initializeStoreConnections(Object value, 
			                                       ModelStore modelStore, 
			                                       ValuePath valuePath, 
			                                       HashSet<Object> localHeap) {
		T.call(ModelFactory.class);
		
		/*
		if(value == null) { 
			System.out.println("initializeStoreConnections");
			System.err.println(valuePath.toString());
			return;
		}*/

		if(NtroCollections.setContainsExact(localHeap, value)) return;
		
		localHeap.add(value);
		
		NtroClass valueClass = Ntro.introspector().ntroClassFromObject(value);

		initializeThisValue(value, modelStore, valuePath, valueClass);
		
		if(valueClass.ifImplements(NtroModel.class) || valueClass.ifImplements(NtroModelValue.class)) {

			initializeAttributes(value, modelStore, valuePath, localHeap);

		}else if(value instanceof List) {

			initializeListValues((List<Object>) value, modelStore, valuePath, localHeap);

		}else if(value instanceof Map) {

			initializeMapValues((Map<String,Object>) value, modelStore, valuePath, localHeap);
		}
	}

	private static void initializeThisValue(Object value, 
			                                ModelStore modelStore, 
			                                ValuePath valuePath, 
			                                NtroClass valueClass) {
		T.call(ModelFactory.class);

		if(valueClass.ifExtends(StoredValue.class)) {

			StoredValue storeConnectedValue = (StoredValue) value;

			storeConnectedValue.setValuePath(valuePath);
			storeConnectedValue.setModelStore(modelStore);
		}
	}

	private static void initializeAttributes(Object value, 
			                                 ModelStore modelStore, 
			                                 ValuePath valuePath, 
			                                 HashSet<Object> localHeap) {
		T.call(ModelFactory.class);
		
		Map<String, Object> attributeMap = new HashMap<>();
		
		NtroClass javaClass = Ntro.introspector().ntroClassFromObject(value);
		
		List<NtroMethod> getters = javaClass.userDefinedGetters();
		
		for(NtroMethod getter : getters) {
			
			String attributeName = getter.getterFieldName();
			Object attributeValue = null;

			try {

				attributeValue = getter.invoke(value);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				Log.fatalError("Unable to invoke getter " + getter.name(), e);
			}
			
			if(attributeValue == null) {
				Log.fatalError("Attributes of a model must never be null (" + getter.name() + ")");
			}

			attributeMap.put(attributeName, attributeValue);
		}

		initializeMapValues(attributeMap, modelStore, valuePath, localHeap);
	}

	private static void initializeMapValues(Map<String, Object> map, 
			                                ModelStore modelStore, 
			                                ValuePath valuePath, 
			                                HashSet<Object> localHeap) {
		T.call(ModelFactory.class);

		List<String> keys = new ArrayList<>();
		keys.addAll(map.keySet());
		
		keys.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		for(String key : keys) {

			Object subValue = map.get(key);
			
			ValuePath subPath = valuePath.clone();
			subPath.addFieldName(key);
			
			initializeStoreConnections(subValue, modelStore, subPath, localHeap);
		}
	}

	private static void initializeListValues(List<Object> list, 
			                                 ModelStore modelStore, 
			                                 ValuePath valuePath, 
			                                 HashSet<Object> localHeap) {
		T.call(ModelFactory.class);

		for(int i = 0; i < list.size(); i++) {
			
			Object subValue = list.get(i);
			
			ValuePath subPath = valuePath.clone();
			subPath.addFieldName(String.valueOf(i));

			initializeStoreConnections(subValue, modelStore, subPath, localHeap);
		}
	}
}