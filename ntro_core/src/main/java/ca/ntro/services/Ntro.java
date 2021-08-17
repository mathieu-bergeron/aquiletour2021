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
import ca.ntro.core.Constants;
import ca.ntro.core.introspection.Factory;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.trace.__T;
import ca.ntro.users.NtroUser;
import ca.ntro.users.NtroSession;
import ca.ntro.web.mvc.ViewLoaderWeb;

public class Ntro {

	/* <Factory> */

	private static Factory factory = new Factory();

	public static Factory factory() {
		__T.call(Ntro.class, "factory");

		return factory;
	}

	/* </Factory> */
	
	
	
	
	
	/* <Context> */
	
	public static NtroContext<?,?> context() {
		__T.call(Ntro.class, "context");

		NtroContext<?, ?> context = new NtroContext<>();

		context.registerUser(Ntro.currentUser());
		context.registerLang(Constants.LANG);
		context.registerSessionData(Ntro.currentSession().getSessionData());
		
		return context;
	}

	/* </Context> */
	
	
	
	
	
	/* <Router> */
	
	private static RouterService router;

	static void registerRouterService(RouterService router) {
		__T.call(Ntro.class, "registerRouterService");

		Ntro.router = router;
	}
	
	public static RouterService router() {
		__T.call(Ntro.class, "router");

		if(router == null) {
			Log.fatalError("ConfigService not registered");
		}

		return router;
	}

	/* </Router> */
	
	
	
	
	
	/* <Config> */

	private static ConfigService config;

	static void registerConfigService(ConfigService config) {
		__T.call(Ntro.class, "registerConfigService");

		Ntro.config = config;
	}

	public static ConfigService config() {
		__T.call(Ntro.class, "config");

		if(config == null) {
			Log.fatalError("ConfigService not registered");
		}

		return config;
	}

	/* </Config> */
	
	
	
	
	
	/* <Calendar> */

	private static CalendarService calendarService;

	static void registerCalendarService(CalendarService calendarService) {
		__T.call(Ntro.class, "registerCalendarService");

		Ntro.calendarService = calendarService;
	}

	public static CalendarService calendar() {
		__T.call(Ntro.class, "calendar");

		if(calendarService == null) {
			Log.fatalError("CalendarService not registered");
		}

		return calendarService;
	}

	/* </Calendar> */
	
	
	
	
	
	/* <RegEx> */

	private static RegEx regEx;

	static void registerRegEx(RegEx regEx) {
		__T.call(Ntro.class, "registerRegEx");

		Ntro.regEx = regEx;
	}

	public static RegEx regEx() {
		__T.call(Ntro.class, "regEx");

		if(regEx == null) {
			System.err.println("#FATAL | RegEx not registered");
		}

		return regEx;
	}

	/* </RegEx> */
	
	
	
	
	
	/* <Introspector> */

	private static Introspector introspector;

	static void registerIntrospector(Introspector introspector) {
		__T.call(Ntro.class, "registerIntrospector");

		Ntro.introspector = introspector;
	}

	public static Introspector introspector() {
		__T.call(Ntro.class, "introspector");

		if(introspector == null) {
			System.err.println("#FATAL | Introspector not registered");
		}

		return introspector;
	}

	/* </Introspector> */
	
	
	
	
	
	/* <Serializable classes> */

	private static Map<String, Class<?>> serializableClasses = new HashMap<>();
	
	public static void registerSerializableClass(Class<? extends JsonSerializable> _class) {
		__T.call(Ntro.class, "registerSerializableClass");

		// FIXME: we'd like to user Ntro.introspector().getSimpleNameForClass ...
		//        but it is NOT registered yet
		serializableClasses.put(_class.getSimpleName(), _class);
	}

	public static Class<?> serializableClass(String simpleName) {
		__T.call(Ntro.class, "serializableClass");

		return serializableClasses.get(simpleName);
	}

	/* </Serializable classes> */
	
	
	
	
	
	/* <Logger> */

	private static Logger logger;

	static void registerLogger(Logger logger) {
		__T.call(Ntro.class, "registerLogger");

		Ntro.logger = logger;
	}

	public static Logger logger() {
		__T.call(Ntro.class, "logger");

		if(logger == null) {
			System.err.println("#FATAL | Logger not registered");
		}

		return logger;
	}

	/* </Logger> */
	
	
	
	
	
	/* <AppCloser> */
	
	private static AppCloser appCloser;

	static void registerAppCloser(AppCloser appCloser) {
		__T.call(Ntro.class, "registerAppCloser");

		Ntro.appCloser = appCloser;
	}

	public static AppCloser appCloser() {
		__T.call(Ntro.class, "appCloser");

		if(appCloser == null) {
			System.err.println("#FATAL | AppCloser not registered");
		}

		return appCloser;
	}

	/* </AppCloser> */
	
	
	
	
	
	/* <ResourceLoader> */
	
	private static ResourceLoader resourceLoader;

	static void registerResourceLoader(ResourceLoader resourceLoader) {
		__T.call(Ntro.class, "registerResourceLoader");
		Ntro.resourceLoader = resourceLoader;
	}

	public static ResourceLoader resourceLoader() {
		__T.call(Ntro.class, "resourceLoader");

		if(resourceLoader == null) {
			System.err.println("#FATAL | ResourceLoader not registered");
		}

		return resourceLoader;
	}

	/* </ResourceLoader> */
	
	
	
	
	
	/* <ViewLoaderWeb> */

	private static Class<? extends ViewLoaderWeb> viewLoaderWebClass;

	static void registerViewLoaderWebClass(Class<? extends ViewLoaderWeb> viewLoaderWebClass) {
		__T.call(Ntro.class, "registerViewLoaderWebClass");

		Ntro.viewLoaderWebClass = viewLoaderWebClass;
	}

	public static ViewLoaderWeb viewLoaderWeb() {
		__T.call(Ntro.class, "viewLoaderWeb");

		return Ntro.factory().newInstance(viewLoaderWebClass);
	}

	/* </ViewLoaderWeb> */
	
	
	
	
	
	/* <ThreadService> */
	
	private static ThreadService threadService;

	static void registerThreadService(ThreadService threadService) {
		__T.call(Ntro.class, "registerThreadService");

		Ntro.threadService = threadService;
	}

	public static ThreadService threadService() {
		__T.call(Ntro.class, "threadService");

		return threadService;
	}

	/* </ThreadService> */
	
	
	
	
	
	/* <MessageService> */

	private static Class<? extends MessageService> messageServiceClass;
	private static Map<String, MessageService> messageServices = new HashMap<>();

	static void registerMessageServiceClass(Class<? extends MessageService> messageServiceClass) {
		__T.call(Ntro.class, "registerMessageServiceClass");

		Ntro.messageServiceClass = messageServiceClass;
	}

	public static MessageService messages() {
		__T.call(Ntro.class, "messageService");

		MessageService service = messageServices.get(threadService().currentThread().threadId());

		if(service == null) {
			service = Ntro.factory().newInstance(messageServiceClass);
			messageServices.put(threadService().currentThread().threadId(), service);
		}

		return service;
	}

	/* </MessageService> */
	
	
	
	
	
	/* <ModelStore> */

	private static ModelStore modelStore;

	static void registerModelStore(ModelStore modelStore) {
		__T.call(Ntro.class, "registerModelStore");

		Ntro.modelStore = modelStore;
	}

	public static ModelStore modelStore() {
		__T.call(Ntro.class, "modelStore");

		return modelStore;
	}

	/* </ModelStore> */
	
	
	
	
	
	/* <BackendService> */

	private static Class<? extends BackendService> backendServiceClass;
	private static Map<String, BackendService> backendServices = new HashMap<>();

	static void registerBackendServiceClass(Class<? extends BackendService> backendServiceClass) {
		__T.call(Ntro.class, "registerBackendServiceClass");

		Ntro.backendServiceClass = backendServiceClass;
	}

	public static BackendService backendService() {
		__T.call(Ntro.class, "backendService");

		BackendService service = backendServices.get(threadService().currentThread().threadId());

		if(service == null) {
			service = Ntro.factory().newInstance(backendServiceClass);
			backendServices.put(threadService().currentThread().threadId(), service);
		}

		return service;
	}

	/* </BackendService> */
	
	
	
	
	
	/* <AssertService> */

	private static AssertService assertService;

	static void registerAssertService(AssertService assertService) {
		__T.call(Ntro.class, "registerAssertService");

		Ntro.assertService = assertService;
	}
	
	public static AssertService assertService() {
		__T.call(Ntro.class, "assertService");

		return Ntro.assertService;
	}

	/* </AssertService> */
	
	
	
	
	
	/* <Verify> */
	
	public static void verify(AssertExpression assertExpression) {
		__T.call(Ntro.class, "verify");

		String failMessage = assertExpression.failMessage();
		if(failMessage != null) {
			assertService().fail(failMessage);
		}
	}

	/* </Verify> */
	
	
	
	
	
	/* <JsonService> */

	private static JsonService jsonService;

	static void registerJsonService(JsonService jsonService) {
		__T.call(Ntro.class, "registerJsonService");

		Ntro.jsonService = jsonService;
	}
	
	public static JsonService jsonService() {
		__T.call(Ntro.class, "jsonService");

		return Ntro.jsonService;
	}

	/* </JsonService> */
	
	
	
	
	
	/* <UserService> */

	public static NtroUser currentUser() {
		__T.call(Ntro.class, "userService");
		
		return currentSession().getUser();
	}

	/* </UserService> */
	
	
	
	
	
	/* <SessionService> */

	private static Class<? extends SessionService> sessionServiceClass;
	private static Map<String, SessionService> sessionServices = new HashMap<>();

	static void registerSessionServiceClass(Class<? extends SessionService> sessionServiceClass) {
		__T.call(Ntro.class, "registerSessionServiceClass");

		Ntro.sessionServiceClass = sessionServiceClass;
	}

	public static NtroSession currentSession() {
		__T.call(Ntro.class, "currentSession");

		return sessionService().session();
	}

	public static SessionService sessionService() {
		__T.call(Ntro.class, "sessionService");

		SessionService service = sessionServices.get(threadService().currentThread().threadId());

		if(service == null) {
			service = Ntro.factory().newInstance(sessionServiceClass);
			sessionServices.put(threadService().currentThread().threadId(), service);
		}

		return service;
	}

	/* </SessionService> */
	
	
	
	
	
	/* <CollectionsService> */

	private static CollectionsService collectionsService;

	public static void registerCollectionsService(CollectionsService collectionsService) {
		__T.call(Ntro.class, "registerCollectionsService");

		Ntro.collectionsService = collectionsService;
	}

	public static CollectionsService collections() {
		__T.call(Ntro.class, "collections");

		return Ntro.collectionsService;
	}

	/* </CollectionsService> */
	
	
	
	
	
	/* <ValueFormatter> */

	private static ValueFormatter valueFormatter;

	static void registerValueFormatter(ValueFormatter valueFormatter) {
		__T.call(Ntro.class, "registerValueFormatter");

		Ntro.valueFormatter = valueFormatter;
	}

	public static ValueFormatter valueFormatter() {
		__T.call(Ntro.class, "valueFormatter");

		return Ntro.valueFormatter;
	}

	/* </ValueFormatter> */
	
	
	
	
	
	/* <reset> */

	public static void reset() {
		messages().reset();
	}

	/* </reset> */
	
	
	
	
	
	/* <System> */

	private static SystemService systemService;

	static void registerSystemService(SystemService systemService) {
		__T.call(Ntro.class, "registerSystemService");

		Ntro.systemService = systemService;
	}

	public static String lineSeparator() {
		__T.call(Ntro.class, "lineSeparator");

		return Ntro.systemService.lineSeparator();
	}

	public static boolean isJSweet() {
		__T.call(Ntro.class, "isJSweet");

		return Ntro.systemService.isJSweet();
	}

	public static boolean isJdk() {
		__T.call(Ntro.class, "isJdk");

		return Ntro.systemService.isJdk();
	}

	/* </System> */
	
	
	
	
	
	

}
