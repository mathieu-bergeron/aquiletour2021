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

package ca.ntro.jdk.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ca.ntro.core.system.trace.T;
import ca.ntro.services.CollectionsService;

public class CollectionsServiceJdk extends CollectionsService {

	@Override
	public <I extends Object> List<I> synchronizedList(List<I> elements) {
		return Collections.synchronizedList(elements);
	}

	@Override
	public <K extends Object, V extends Object> Map<K, V> concurrentMap(Map<K, V> elements) {
		Map<K,V> concurrentHashMap = new ConcurrentHashMap<K,V>();
		concurrentHashMap.putAll(elements);
		return concurrentHashMap;
	}

	@Override
	public <V extends Object> Set<V> concurrentSet(Set<V> elements) {
		Set<V> concurrentSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
		concurrentSet.addAll(elements);
		return concurrentSet;
	}


	@Override
	public boolean containsKeyExact(Map<?, ?> map, Object key) {
		boolean containsKey = false;
		
		synchronized (map) {
			for(Object candidateKey : map.keySet()) {
				if(candidateKey == key) {
					containsKey = true;
					break;
				}
			}
		}
		
		return containsKey;
	}

	@Override
	public <V> V getExact(Map<?, V> map, Object key) {
		T.call(this);

		V value = null;

		synchronized(map) {
			for(Entry<?, V> entry : map.entrySet()) {
				if(entry.getKey() == key) {
					value = entry.getValue();
					break;
				}
			}
		}

		return value;
	}

	@Override
	public boolean listEquals(List<?> list1, List<?> list2) {
		return list1.equals(list2);
	}

	@Override
	public boolean mapEquals(Map<?, ?> map1, Map<?, ?> map2) {
		return map1.equals(map2);
	}

	@Override
	public boolean setContainsExact(Set<?> set, Object target) {
		boolean setContains = false;
		
		synchronized (set) {
			for(Object element : set) {
				if(element == target) {
					setContains = true;
					break;
				}
			}
		}
		
		return setContains;
	}

	@Override
	public boolean containsEquals(Set<?> set, Object target) {
		return set.contains(target);
	}

	@Override
	public int indexOfEquals(List<?> list, Object target) {
		return list.indexOf(target);
	}

	@Override
	public boolean listContainsEquals(List<?> value, Object target) {
		return value.contains(target);
	}

	@Override
	public int compareToString(String o1, String o2) {
		return o1.compareTo(o2);
	}
}
