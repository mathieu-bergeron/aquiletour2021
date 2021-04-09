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

package def.es6;

import ca.ntro.jsweet.dom.HtmlElementJSweet;
import def.dom.Blob;
import def.js.Object;
import def.js.Promise;
import jsweet.lang.Interface;

public class Globals {

	@Interface
	public static abstract class FetchResponse {
		public boolean ok;

		public native Promise<Blob> blob();
		public native Promise<String> text();
		public native Promise<Object> json();
	}

	public native static Promise<FetchResponse> fetch(String path);
	public native static Promise<FetchResponse> fetch(String path, Object request);

	public native static String decodeURI(String string);
	public native static String encodeURI(String string);

	public native static void installSourceMap(String rawSourceMap);

	public static JsCookies Cookies;

	@Interface
	public static abstract class JsCookies {
		public abstract String get(String name);
		public abstract String set(String name, String value, def.js.Object options);
	}

	public native static void _ntro_initialize_view(String viewName, HtmlElementJSweet viewRootHtmlElement);

}
