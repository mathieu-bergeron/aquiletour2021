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

import java.util.HashMap;
import java.util.Map;

import ca.ntro.assertions.AssertExpression;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.trace.__T;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class Ntro {

	private static Map<String, Class<?>> serializableClasses = new HashMap<>();
	
	public static void registerSerializableClass(Class<? extends JsonSerializable> _class) {
		// FIXME: we'd like to user Ntro.introspector().getSimpleNameForClass ...
		//        but it is NOT registered yet
		serializableClasses.put(_class.getSimpleName(), _class);
	}

	public static Class<?> serializableClass(String simpleName) {
		return serializableClasses.get(simpleName);
	}

	private static Introspector introspector;
	private static Logger logger;
	private static AppCloser appCloser;
	private static RegEx regEx;
	private static ResourceLoader resourceLoader;
	private static Class<? extends ViewLoaderWeb> viewLoaderWebClass;

	private static Class<? extends MessageService> messageServiceClass;
	private static Map<String, MessageService> messageServices = new HashMap<>();
	private static ThreadService threadService;
	private static BackendService backendService;
	
	private static AssertService assertService;
	private static JsonService jsonService;
	private static UserService userService;

	// FIXME: zzz is to "hide" the public method in auto-completion lists
	//        can we make this package-private?
	public static void zzz_registerResourceLoader(ResourceLoader resourceLoader) {
		Ntro.resourceLoader = resourceLoader;
	}

	public static void __registerIntrospector(Introspector introspector) {
		//System.out.println("#T.call (Ntro.java) >> Ntro.__registerIntrospector");

		Ntro.introspector = introspector;
	}

	public static void __registerViewLoaderWeb(Class<? extends ViewLoaderWeb> viewLoaderWeb) {
		//System.out.println("#T.call (Ntro.java) >> Ntro.__registerIntrospector");

		Ntro.viewLoaderWebClass = viewLoaderWeb;
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
		__T.call(Ntro.class, "introspector");

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

	public static ViewLoaderWeb viewLoaderWeb() {
		return Factory.newInstance(viewLoaderWebClass);
	}

	public static void zzz_registerThreadService(ThreadService threadService) {
		Ntro.threadService = threadService;
	}

	public static ThreadService threadService() {
		return threadService;
	}

	public static void zzz_registerMessageServiceClass(Class<? extends MessageService> messageServiceClass) {
		Ntro.messageServiceClass = messageServiceClass;
	}

	public static MessageService messageService() {
		MessageService service = messageServices.get(threadService().currentThread().getThreadId());

		if(service == null) {
			service = Factory.newInstance(messageServiceClass);
			messageServices.put(threadService().currentThread().getThreadId(), service);
		}

		return service;
	}

	public static void zzz_registerBackendService(BackendService backendService) {
		Ntro.backendService = backendService;
	}

	public static BackendService backendService() {
		return backendService;
	}
	
	public static void verify(AssertExpression assertExpression) {
		String failMessage = assertExpression.failMessage();
		if(failMessage != null) {
			assertService().fail(failMessage);
		}
	}

	public static void zzz_registerAssertService(AssertService assertService) {
		Ntro.assertService = assertService;
	}
	
	public static AssertService assertService() {
		return Ntro.assertService;
	}

	public static void zzz_registerJsonService(JsonService jsonService) {
		Ntro.jsonService = jsonService;
	}
	
	public static JsonService jsonService() {
		return Ntro.jsonService;
	}

	static void registerUserService(UserService userService) {
		Ntro.userService = userService;
	}

	public static UserService userService() {
		return Ntro.userService;
	}
	
	
}
