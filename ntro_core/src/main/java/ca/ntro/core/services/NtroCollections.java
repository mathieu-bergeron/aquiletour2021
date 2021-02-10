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

package ca.ntro.core.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
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

}
