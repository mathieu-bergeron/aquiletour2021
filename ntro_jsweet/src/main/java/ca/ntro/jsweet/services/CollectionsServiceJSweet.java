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

import ca.ntro.core.system.trace.T;
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
	public boolean containsElementEquals(Set<?> set, Object target) {
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
	public <V> V getByKeyExact(Map<?, V> map, Object key) {
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
	public boolean containsElementExact(Set<?> set, Object target) {
		return set.contains(target);
	}

	@Override
	public int indexOfEquals(List<?> list, Object target) {
		int index = -1;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(target)) {
				index = i;
				break;
			}
		}

		return index;
	}

	@Override
	public boolean listContainsEquals(List<?> value, Object target) {
		return indexOfEquals(value, target) != -1;
	}

	// JSweet: "".compareTo("") transpiles to .localCompare
	//         but it handles lower/upper case differently 
	//         than in Java!
	// JSweet:  "abC".localeCompare("abc");  ------>    > 0
	// Jdk:     "abC".compareTo("abc");      ------>    < 0
	@Override
	public int compareToString(String stringA, String stringB) {
		// -1: stringA smaller-then stringB
		// 0: equals
		// +1: stringA bigger-then stringB
		
		int result = 0;
		
		for(int i = 0; i < stringA.length()-1; i++) {

			if(i < stringB.length()) {
				String charA = stringA.substring(i,i+1);
				String charB = stringB.substring(i,i+1);
				
				int charResult = charA.compareTo(charB);
				
				if(charResult != 0
						&& charA.equalsIgnoreCase(charB)){
					
					result = -charResult;

				}else if(charResult != 0) {
					result = charResult;
					break;
				}
				
			}else {
				result = +1;
				break;
			}
		}
		
		return result;
	}

	@Override
	public boolean containsItemEquals(List<?> list, Object target) {
		T.call(this);
		
		boolean ifContains = false;
		
		for(Object candidate : list) {
			if(candidate.equals(target)) {
				ifContains = true;
				break;
			}
		}
		
		return ifContains;
	}

	@Override
	public boolean containsItemExact(List<?> list, Object target) {
		T.call(this);

		return list.contains(target);
	}

	@Override
	public boolean containsKeyEquals(Map<?, ?> map, Object key) {
		T.call(this);
		
		return map.containsKey(key);
	}

	@Override
	public <V> V getByKeyEquals(Map<?, V> map, Object key) {
		T.call(this);
		
		V value = null;
		
		for(Map.Entry<?, V> entry : map.entrySet()) {
			if(entry.getKey().equals(key)) {
				value = entry.getValue();
				break;
			}
		}

		return value;
	}

	@Override
	public <V> void removeByKeyEquals(Map<?, V> map, Object key) {
		T.call(this);
		
		Object foundKey = null;
		
		for(Map.Entry<?, V> entry : map.entrySet()) {
			if(entry.getKey().equals(key)) {
				foundKey = key;
				break;
			}
		}
		
		if(foundKey != null) {
			map.remove(foundKey);
		}
	}

	@Override
	public <V> void removeByKeyExact(Map<?, V> map, Object key) {
		T.call(this);

		map.remove(key);
	}

}
