package ca.ntro.assertions;

import java.util.List;

import ca.ntro.core.introspection.NtroClass;

public class Factory {

	public static SimpleValueExpression that(Object value) {
		return new SimpleAssertExpression().that(value);
	}

	public static NtroClassExpression thatClass(NtroClass classSignature) {
		return new SimpleAssertExpression().thatClass(classSignature);
	}

	public static ObjectExpression thatObject(Object object) {
		return new SimpleAssertExpression().thatObject(object);
	}

	public static ListExpression thatList(List<?> list) {
		return new SimpleAssertExpression().thatList(list);
	}

}
