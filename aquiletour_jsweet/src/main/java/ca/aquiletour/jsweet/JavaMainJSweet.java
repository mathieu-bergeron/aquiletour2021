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

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.Constants;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jsweet.services.BackendServiceJSweet;
import ca.ntro.jsweet.services.NtroJSweet;

public class JavaMainJSweet {

	public static void main(String[] args) {
		__T.call(JavaMainJSweet.class,"main");

		String[] options = new String[] {"--traceLevel","APP"};
		
		// FIXME: this has to be done before pretty much anything
		//        we need a better init sequence
		//        ideally using a TaskGraph to represent dependancies
		AquiletourMain.registerSerializableClasses();

		BackendServiceJSweet backendServiceJSweet = new BackendServiceJSweet(Constants.WS_PATH);
		
		NtroJSweet.defaultInitializationTask(backendServiceJSweet)
				  .setOptions(options)
				  .addNextTask(new AquiletourMainJSweet())
				  .execute();
	}
}
