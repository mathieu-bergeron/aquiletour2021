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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ca.ntro.core.services.NtroCollections;

public class NtroCollectionsJdk extends NtroCollections {

	@Override
	public <I extends Object> List<I> synchronizedListImpl(List<I> elements) {
		return Collections.synchronizedList(elements);
	}

	@Override
	public <K extends Object, V extends Object> Map<K, V> concurrentMapImpl(Map<K, V> elements) {
		Map<K,V> concurrentHashMap = new ConcurrentHashMap<K,V>();
		concurrentHashMap.putAll(elements);
		return concurrentHashMap;
	}

	@Override
	protected <V extends Object> Set<V> concurrentSetImpl(Set<V> elements) {
		Set<V> concurrentSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
		concurrentSet.addAll(elements);
		return concurrentSet;
	}
}
