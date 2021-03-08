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

package ca.aquiletour.jsweet;

import java.util.HashMap;
import java.util.Map;

import ca.aquiletour.jsweet.test.LinkedListNode;
import ca.ntro.core.Ntro;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jsweet.NtroJSweet;
import ca.ntro.jsweet.introspection.IntrospectorJSweet;
import ca.ntro.jsweet.services.JsonParserJSweet;

public class JavaMainJSweet {

	public static void main(String[] args) {
		__T.call(JavaMainJSweet.class,"main");

		String[] options = new String[] {"--traceLevel","APP"};
		

		NtroJSweet.defaultInitializationTask()
				  .setOptions(options)
				  .addNextTask(new AquiletourMainJSweet())
				  .execute();
	}
}
