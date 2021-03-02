package ca.ntro.assertions;

import java.util.List;

import ca.ntro.core.introspection.ClassSignature;

public class Factory {

	public static SimpleValueExpression that(Object value) {
		return new SimpleAssertExpression().that(value);
	}

	public static ClassSignatureExpression thatClassSignature(ClassSignature classSignature) {
		return new SimpleAssertExpression().thatClassSignature(classSignature);
	}

	public static ObjectExpression thatObject(Object object) {
		return new SimpleAssertExpression().thatObject(object);
	}

	public static ListExpression thatList(List<?> list) {
		return new SimpleAssertExpression().thatList(list);
	}

}
