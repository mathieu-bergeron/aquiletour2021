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


public abstract class CollectionsService {

	public abstract <I extends Object> List<I> synchronizedList(List<I> elements);
	public abstract <K extends Object, V extends Object> Map<K,V> concurrentMap(Map<K,V> elements);
	public abstract <V extends Object> Set<V> concurrentSet(Set<V> elements);

	public abstract boolean containsEquals(Set<?> set, Object target);
	public abstract boolean setContainsExact(Set<?> set, Object target);

	public abstract boolean containsKeyExact(Map<?, ?> map, Object key);
	public abstract <V extends Object> V getExactKey(Map<?, V> map, Object key);

	public abstract boolean listEquals(List<?> list1, List<?> list2);
	public abstract boolean mapEquals(Map<?,?> map1, Map<?,?> map2);

	public abstract int indexOfEquals(List<?> list, Object target);

}
