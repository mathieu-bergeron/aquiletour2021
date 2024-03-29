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

package ca.ntro.jsweet;

import ca.ntro.jsweet.services.OnLoadTask;
import def.dom.WebSocket;
import jsweet.lang.Interface;

// XXX: this could be anywhere
//      it is not a Globals (that only works for def)
//      i.e. defs here are not pushed into the root scope by JSweet
public class JSweetGlobals {

	// notifyTaskFinished() is called from Javascript, see tutoriel02/webapp/index.html
	public static OnLoadTask onLoadTask = null;

	public static void initialize() {
		onLoadTask = new OnLoadTask();
	}
	
	@Interface
	public static  class SockJS extends WebSocket {
	}

}
