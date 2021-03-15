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

package ca.aquiletour.server;

import ca.aquiletour.server.backend.AquiletourBackendService;
import ca.ntro.core.system.trace.__T;
import ca.ntro.jdk.web.NtroWebserver;
import ca.ntro.services.ModelStore;

public class JavaMainServer {
	
	public static void main(String[] args) {
		__T.call(JavaMainServer.class, "main");
		
		ModelStore localStore = new LocalStoreServer();
		
		NtroWebserver.defaultInitializationTask(AquiletourBackendService.class, localStore)
		             .setOptions(args)
		             .addNextTask(new AquiletourMainServer())
		             .execute();
		             //.execute().addGraphWriter(new GraphTraceWriterJdk(new File("TMP")));
	}
}
