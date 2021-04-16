package ca.ntro.services;

import ca.ntro.core.Path;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.models.StoredDouble;
import ca.ntro.core.models.StoredInteger;
import ca.ntro.core.models.StoredList;
import ca.ntro.core.models.StoredMap;
import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.GetModelNtroMessage;
import ca.ntro.messages.ntro_messages.InvokeValueMethodNtroMessage;
import ca.ntro.messages.ntro_messages.RegisterSocketNtroMessage;
import ca.ntro.messages.ntro_messages.SetModelNtroMessage;
import ca.ntro.messages.ntro_messages.SetUserNtroMessage;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ValuePath;
import ca.ntro.users.NtroUser;
import ca.ntro.users.Session;
import ca.ntro.web.interactivity.DomProcessor;
import ca.ntro.web.interactivity.runtime.InteractivityRuntime;

public abstract class EarlyInitialization {

	public void performInitialization() {
		registerSerializableClasses();

		Introspector introspector = provideIntrospector();
		T.__registerIntrospector(introspector);
		Ntro.registerIntrospector(introspector);

		DomProcessor domProcessor = provideDomProcessor();
		Ntro.registerDomProcessor(domProcessor);

		InteractivityRuntime interactivityRuntime = provideInteractivityRuntime();
		Ntro.registerInteractivityRuntime(interactivityRuntime);

		Logger logger = provideLogger();
		T.__registerLogger(logger);
		Ntro.registerLogger(logger);

		Ntro.registerAppCloser(provideAppCloser());
		Ntro.registerRegEx(provideRegEx());

		Ntro.registerCollectionsService(provideCollectionsService());
	}

	protected abstract Introspector provideIntrospector();
	protected abstract DomProcessor provideDomProcessor();
	protected abstract InteractivityRuntime provideInteractivityRuntime();
	protected abstract Logger provideLogger();
	protected abstract AppCloser provideAppCloser();
	protected abstract RegEx provideRegEx();
	protected abstract CollectionsService provideCollectionsService();

	private void registerSerializableClasses() {
		Ntro.registerSerializableClass(NtroUser.class);
		Ntro.registerSerializableClass(Session.class);

		Ntro.registerSerializableClass(RegisterSocketNtroMessage.class);
		Ntro.registerSerializableClass(GetModelNtroMessage.class);
		Ntro.registerSerializableClass(SetModelNtroMessage.class);
		Ntro.registerSerializableClass(SetUserNtroMessage.class);
		Ntro.registerSerializableClass(InvokeValueMethodNtroMessage.class);

		Ntro.registerSerializableClass(Path.class);
		Ntro.registerSerializableClass(DocumentPath.class);
		Ntro.registerSerializableClass(ValuePath.class);

		Ntro.registerSerializableClass(StoredBoolean.class);
		Ntro.registerSerializableClass(StoredString.class);
		Ntro.registerSerializableClass(StoredDouble.class);
		Ntro.registerSerializableClass(StoredInteger.class);
		Ntro.registerSerializableClass(StoredProperty.class);
		Ntro.registerSerializableClass(StoredList.class);
		Ntro.registerSerializableClass(StoredMap.class);
	}
}
