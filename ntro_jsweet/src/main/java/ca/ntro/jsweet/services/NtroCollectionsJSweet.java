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
import java.util.List;
import java.util.Map;

import ca.ntro.core.services.NtroCollections;


public class NtroCollectionsJSweet extends NtroCollections {

	@Override
	public <I> List<I> synchronizedListImpl(List<I> elements) {
		// XXX: no need for a synchronized list in single-threaded Javascript
		List<I> list = new ArrayList<>();
		list.addAll(elements);
		return list;
	}

	@Override
	public <K, V> Map<K, V> concurrentHashMapImpl(Map<K, V> elements) {
		// XXX: no need for a concurrent map in single-threaded Javascript
		Map<K,V> map = new HashMap<>();
		map.putAll(elements);
		return map;
	}

}
