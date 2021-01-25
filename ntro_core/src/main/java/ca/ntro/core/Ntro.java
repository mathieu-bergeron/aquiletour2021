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

package ca.ntro.core;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.mvc.view.ViewLoaderJWeb;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.services.AppCloser;
import ca.ntro.core.services.Logger;
import ca.ntro.core.services.ResourceLoader;
import ca.ntro.core.system.trace.__T;

public class Ntro {
	
	private static Introspector introspector;
	private static Logger logger;
	private static AppCloser appCloser;
	private static RegEx regEx;
	private static ResourceLoader resourceLoader;

	// FIXME: zzz is to "hide" the public method in auto-completion lists
	//        can we make this package-private?
	public static void zzz_registerResourceLoader(ResourceLoader resourceLoader) {
		Ntro.resourceLoader = resourceLoader;
	}

	public static void __registerIntrospector(Introspector introspector) {
		//System.out.println("#T.call (Ntro.java) >> Ntro.__registerIntrospector");

		Ntro.introspector = introspector;
	}

	public static void __registerLogger(Logger logger) {
		__T.call(Ntro.class, "registerLogger");

		Ntro.logger = logger;
	}

	public static void __registerAppCloser(AppCloser appCloser) {
		__T.call(Ntro.class, "registerAppCloser");

		Ntro.appCloser = appCloser;
	}

	public static void __registerRegEx(RegEx regEx) {
		__T.call(Ntro.class, "registerRegEx");

		Ntro.regEx = regEx;
	}

	public static Introspector introspector() {
		System.out.println("#T.call (Ntro.java) >> Ntro.introspector");

		if(introspector == null) {
			System.err.println("#FATAL | Introspector not registered");
		}

		return introspector;
	}

	public static RegEx regEx() {
		__T.call(Ntro.class, "regEx");

		if(regEx == null) {
			System.err.println("#FATAL | RegEx not registered");
		}

		return regEx;
	}

	public static Logger logger() {
		__T.call(Ntro.class, "logger");

		if(logger == null) {
			System.err.println("#FATAL | Logger not registered");
		}

		return logger;
	}
	
	public static AppCloser appCloser() {
		__T.call(Ntro.class, "appCloser");

		if(appCloser == null) {
			System.err.println("#FATAL | AppCloser not registered");
		}

		return appCloser;
	}

	public static ResourceLoader resourceLoader() {
		__T.call(Ntro.class, "resourceLoader");

		if(resourceLoader == null) {
			System.err.println("#FATAL | ResourceLoader not registered");
		}

		return resourceLoader;
	}

	public static ViewLoaderJWeb viewLoaderWeb() {
		return new ViewLoaderJWeb();
	}

}
