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

import ca.ntro.services.NtroCollections;


public class NtroCollectionsJSweet extends NtroCollections {

	@Override
	public <I> List<I> synchronizedListImpl(List<I> elements) {
		// XXX: no need for a synchronized list in single-threaded Javascript
		List<I> list = new ArrayList<>();
		for(I item : elements) {
			list.add(item);
		}
		return list;
	}

	@Override
	public <K, V> Map<K, V> concurrentMapImpl(Map<K, V> elements) {
		// XXX: no need for a concurrent map in single-threaded Javascript
		Map<K,V> map = new HashMap<>();
		for(Map.Entry<K,V> entry : elements.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	@Override
	protected <V> Set<V> concurrentSetImpl(Set<V> elements) {
		// XXX: no need for a concurrent set in single-threaded Javascript
		Set<V> set = new HashSet<>();
		for(V value : elements) {
			set.add(value);
		}
		return set;
	}

	@Override
	protected boolean containsEqualsImpl(Set<?> set, Object target) {
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
	protected boolean containsKeyExactImpl(Map<?, ?> map, Object key) {
		return map.containsKey(key);
	}

	@Override
	protected <V> V getExactKeyImpl(Map<?, V> map, Object key) {
		return map.get(key);
	}

	@Override
	protected boolean listEqualsImpl(List<?> list1, List<?> list2) {
		if(list1.size() != list2.size()) return false;
		
		boolean ifEquals = true;
		
		for(int i = 0; i < list1.size(); i++) {
			Object item1 = list1.get(i);
			Object item2 = list1.get(i);
			if(item1 instanceof List) {
				ifEquals = listEqualsImpl((List<?>)item1, (List<?>)item2);
			}else if(item1 instanceof Map) {
				ifEquals = mapEqualsImpl((Map<?,?>)item1, (Map<?,?>)item2);
			}else if(item1 == null){
				ifEquals = (item2 == null);
			}else {
				ifEquals = item1.equals(item2);
			}
		}
		
		return ifEquals;
	}

	@Override
	protected boolean mapEqualsImpl(Map<?, ?> map1, Map<?, ?> map2) {
		if(map1.size() != map2.size()) return false;

		boolean ifEquals = true;
		
		for(Map.Entry<?, ?> entry : map1.entrySet()) {
			Object item1 = entry.getValue();
			Object item2 = map1.get(entry.getKey());
			if(item1 instanceof List) {
				ifEquals = listEqualsImpl((List<?>)item1, (List<?>)item2);
			}else if(item1 instanceof Map) {
				ifEquals = mapEqualsImpl((Map<?,?>)item1, (Map<?,?>)item2);
			}else if(item1 == null){
				ifEquals = (item2 == null);
			}else {
				ifEquals = item1.equals(item2);
			}
		}
		
		return ifEquals;
	}

	@Override
	protected boolean setContainsExactImpl(Set<?> set, Object target) {
		return set.contains(target);
	}

}
