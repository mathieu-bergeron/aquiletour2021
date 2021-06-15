package ca.ntro.services;

import ca.ntro.core.Path;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.StoredBoolean;
import ca.ntro.core.models.StoredDouble;
import ca.ntro.core.models.StoredInteger;
import ca.ntro.core.models.StoredList;
import ca.ntro.core.models.StoredLong;
import ca.ntro.core.models.StoredMap;
import ca.ntro.core.models.StoredProperty;
import ca.ntro.core.models.StoredString;
import ca.ntro.core.regex.RegEx;
import ca.ntro.core.system.trace.T;
import ca.ntro.messages.ntro_messages.NtroErrorMessage;
import ca.ntro.messages.ntro_messages.NtroGetModelMessage;
import ca.ntro.messages.ntro_messages.NtroInvokeValueMethodMessage;
import ca.ntro.messages.ntro_messages.NtroRegisterSocketMessage;
import ca.ntro.messages.ntro_messages.NtroSetModelMessage;
import ca.ntro.messages.ntro_messages.NtroUpdateSessionMessage;
import ca.ntro.models.NtroDate;
import ca.ntro.models.NtroDayOfWeek;
import ca.ntro.models.NtroTimeOfDay;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ValuePath;
import ca.ntro.users.NtroUser;
import ca.ntro.users.NtroSession;
import ca.ntro.users.NtroSessionData;

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

		Ntro.registerConfigService(provideConfigService());
	}

	protected abstract Introspector provideIntrospector();
	protected abstract Logger provideLogger();
	protected abstract AppCloser provideAppCloser();
	protected abstract RegEx provideRegEx();
	protected abstract CollectionsService provideCollectionsService();
	protected abstract ConfigService provideConfigService();

	protected void registerSerializableClasses() {
		Ntro.registerSerializableClass(NtroUser.class);
		Ntro.registerSerializableClass(NtroSession.class);
		Ntro.registerSerializableClass(NtroSessionData.class);
		Ntro.registerSerializableClass(NtroDate.class);
		Ntro.registerSerializableClass(NtroTimeOfDay.class);
		Ntro.registerSerializableClass(NtroDayOfWeek.class);

		Ntro.registerSerializableClass(NtroRegisterSocketMessage.class);
		Ntro.registerSerializableClass(NtroGetModelMessage.class);
		Ntro.registerSerializableClass(NtroSetModelMessage.class);
		Ntro.registerSerializableClass(NtroUpdateSessionMessage.class);
		Ntro.registerSerializableClass(NtroInvokeValueMethodMessage.class);
		Ntro.registerSerializableClass(NtroErrorMessage.class);

		Ntro.registerSerializableClass(Path.class);
		Ntro.registerSerializableClass(DocumentPath.class);
		Ntro.registerSerializableClass(ValuePath.class);

		Ntro.registerSerializableClass(StoredBoolean.class);
		Ntro.registerSerializableClass(StoredString.class);
		Ntro.registerSerializableClass(StoredDouble.class);
		Ntro.registerSerializableClass(StoredInteger.class);
		Ntro.registerSerializableClass(StoredLong.class);
		Ntro.registerSerializableClass(StoredProperty.class);
		Ntro.registerSerializableClass(StoredList.class);
		Ntro.registerSerializableClass(StoredMap.class);
	}
}
