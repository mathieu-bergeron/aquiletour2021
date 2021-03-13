package ca.ntro.services;

import ca.ntro.core.NtroUser;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.GetModelNtroMessage;
import ca.ntro.messages.ntro_messages.InvokeValueMethodNtroMessage;
import ca.ntro.messages.ntro_messages.RegisterSocketNtroMessage;
import ca.ntro.messages.ntro_messages.SetModelNtroMessage;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ValuePath;

public abstract class EarlyInitialization {

	public void performInitialization() {
		registerSerializableClasses();

		Introspector introspector = provideIntrospector();
		T.__registerIntrospector(introspector);
		Ntro.registerIntrospector(introspector);

		Logger logger = provideLogger();
		T.__registerLogger(logger);
		Ntro.registerLogger(logger);
		
		Ntro.registerAppCloser(provideAppCloser());
		Ntro.registerRegEx(provideRegEx());

		Ntro.registerCollectionsService(provideCollectionsService());
	}

	protected abstract Introspector provideIntrospector();
	protected abstract Logger provideLogger();
	protected abstract AppCloser provideAppCloser();
	protected abstract RegEx provideRegEx();
	protected abstract CollectionsService provideCollectionsService();

	private void registerSerializableClasses() {
		Ntro.registerSerializableClass(NtroUser.class);

		Ntro.registerSerializableClass(RegisterSocketNtroMessage.class);
		Ntro.registerSerializableClass(GetModelNtroMessage.class);
		Ntro.registerSerializableClass(SetModelNtroMessage.class);
		Ntro.registerSerializableClass(InvokeValueMethodNtroMessage.class);

		Ntro.registerSerializableClass(DocumentPath.class);
		Ntro.registerSerializableClass(ValuePath.class);
	}

}
