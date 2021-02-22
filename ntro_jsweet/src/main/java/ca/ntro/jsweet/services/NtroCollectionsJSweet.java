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

import ca.ntro.core.services.NtroCollections;


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

}
