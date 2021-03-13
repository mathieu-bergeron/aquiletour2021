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

package ca.ntro.jsweet.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ntro.services.CollectionsService;


public class CollectionsServiceJSweet extends CollectionsService {

	@Override
	public <I> List<I> synchronizedList(List<I> elements) {
		// XXX: no need for a synchronized list in single-threaded Javascript
		List<I> list = new ArrayList<>();
		for(I item : elements) {
			list.add(item);
		}
		return list;
	}

	@Override
	public <K, V> Map<K, V> concurrentMap(Map<K, V> elements) {
		// XXX: no need for a concurrent map in single-threaded Javascript
		Map<K,V> map = new HashMap<>();
		for(Map.Entry<K,V> entry : elements.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	@Override
	public <V> Set<V> concurrentSet(Set<V> elements) {
		// XXX: no need for a concurrent set in single-threaded Javascript
		Set<V> set = new HashSet<>();
		for(V value : elements) {
			set.add(value);
		}
		return set;
	}

	@Override
	public boolean containsEquals(Set<?> set, Object target) {
		boolean ifSetContains = false;

		for(Object candidate : set) {
			if(candidate.equals(target)) {
				ifSetContains = true;
				break;
			}
		}

		return ifSetContains;
	}

	@Override
	public boolean containsKeyExact(Map<?, ?> map, Object key) {
		return map.containsKey(key);
	}

	@Override
	public <V> V getExactKey(Map<?, V> map, Object key) {
		return map.get(key);
	}

	@Override
	public boolean listEquals(List<?> list1, List<?> list2) {
		if(list1.size() != list2.size()) return false;
		
		boolean ifEquals = true;
		
		for(int i = 0; i < list1.size(); i++) {
			Object item1 = list1.get(i);
			Object item2 = list1.get(i);
			if(item1 instanceof List) {
				ifEquals = listEquals((List<?>)item1, (List<?>)item2);
			}else if(item1 instanceof Map) {
				ifEquals = mapEquals((Map<?,?>)item1, (Map<?,?>)item2);
			}else if(item1 == null){
				ifEquals = (item2 == null);
			}else {
				ifEquals = item1.equals(item2);
			}
		}
		
		return ifEquals;
	}

	@Override
	public boolean mapEquals(Map<?, ?> map1, Map<?, ?> map2) {
		if(map1.size() != map2.size()) return false;

		boolean ifEquals = true;
		
		for(Map.Entry<?, ?> entry : map1.entrySet()) {
			Object item1 = entry.getValue();
			Object item2 = map1.get(entry.getKey());
			if(item1 instanceof List) {
				ifEquals = listEquals((List<?>)item1, (List<?>)item2);
			}else if(item1 instanceof Map) {
				ifEquals = mapEquals((Map<?,?>)item1, (Map<?,?>)item2);
			}else if(item1 == null){
				ifEquals = (item2 == null);
			}else {
				ifEquals = item1.equals(item2);
			}
		}
		
		return ifEquals;
	}

	@Override
	public boolean setContainsExact(Set<?> set, Object target) {
		return set.contains(target);
	}

}
