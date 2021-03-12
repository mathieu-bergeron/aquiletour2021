// Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
//
// This file is part of tutoriels4f5
//
// tutoriels4f5 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// tutoriels4f5 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

package ca.ntro.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.T;

@SuppressWarnings("rawtypes")
public abstract class NtroCollections {

	private static NtroCollections instance;

	public static void initialize(NtroCollections instance) {
		T.call(NtroCollections.class);

		NtroCollections.instance = instance;
	}

	public abstract <I extends Object> List<I> synchronizedListImpl(List<I> elements);

	public static <I extends Object> List<I> synchronizedList(List<I> elements) {
		T.call(NtroCollections.class);

		List<I> synchronizedList = null;

		try {

			synchronizedList = instance.synchronizedListImpl(elements);

		}catch(NullPointerException e) {

			Log.fatalError(Ntro.introspector().getSimpleNameForClass(NtroCollections.class) + " must be initialized");
		}

		return synchronizedList;
	}

	public abstract <K extends Object, V extends Object> Map<K,V> concurrentMapImpl(Map<K,V> elements);

	public static <K extends Object, V extends Object> Map<K,V> concurrentMap(Map<K,V> elements) {
		T.call(NtroCollections.class);

		Map<K,V> concurrentHashMap = null;

		try {
			
			concurrentHashMap = instance.concurrentMapImpl(elements);
			
		}catch(NullPointerException e) {
			// Introspector might not be registered here
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}

		return concurrentHashMap;
	}

	public static <V extends Object> Set<V> concurrentSet(Set<V> elements) {

		Set<V> concurrentSet = null;
		
		try {
			
			concurrentSet = instance.concurrentSetImpl(elements);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}

		return concurrentSet;
	}

	protected abstract <V extends Object> Set<V> concurrentSetImpl(Set<V> elements);

	protected abstract boolean containsEqualsImpl(Set<?> set, Object target);

	public static boolean containsEquals(Set<?> set, Object target) {
		boolean ifSetContains = false;
		
		try {
			
			ifSetContains = instance.containsEqualsImpl(set, target);

		}catch(NullPointerException e) {
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}
		
		return ifSetContains;
	}

	protected abstract boolean setContainsExactImpl(Set<?> set, Object target);

	public static boolean setContainsExact(Set<?> set, Object target) {
		boolean ifSetContains = false;
		
		try {
			
			ifSetContains = instance.setContainsExactImpl(set, target);

		}catch(NullPointerException e) {
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}
		
		return ifSetContains;
	}
	
	protected abstract boolean containsKeyExactImpl(Map<?, ?> map, Object key);

	public static boolean containsKeyExact(Map<?, ?> map, Object key) {
		boolean containsKey = false;
		
		try {
			
			containsKey = instance.containsKeyExactImpl(map, key);

		}catch(NullPointerException e) {
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}
		
		return containsKey;
	}

	protected abstract <V extends Object> V getExactKeyImpl(Map<?, V> map, Object key);

	public static <V extends Object> V getExactKey(Map<?, V> map, Object key) {
		V value = null;
		
		try {
			
			value = instance.getExactKeyImpl(map, key);

		}catch(NullPointerException e) {
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}
		
		return value;
	}

	protected abstract boolean listEqualsImpl(List<?> list1, List<?> list2);

	public static boolean listEquals(List<?> list1, List<?> list2) {
		boolean listEquals = false;
		
		try {
			
			listEquals = instance.listEqualsImpl(list1, list2);

		}catch(NullPointerException e) {
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}
		
		return listEquals;
	}

	protected abstract boolean mapEqualsImpl(Map<?,?> map1, Map<?,?> map2);

	public static boolean mapEquals(Map<?,?> map1, Map<?,?> map2) {
		boolean mapEquals = false;
		
		try {
			
			mapEquals = instance.mapEqualsImpl(map1, map2);

		}catch(NullPointerException e) {
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}
		
		return mapEquals;
	}

}
