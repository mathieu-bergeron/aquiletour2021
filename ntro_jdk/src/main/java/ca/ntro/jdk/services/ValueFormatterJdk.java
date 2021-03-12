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

package ca.ntro.jdk.services;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Formatter;
import java.util.List;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.services.ValueFormatter;

public class ValueFormatterJdk extends ValueFormatter {

	@Override
	public void formatImpl(StringBuilder builder, boolean isHtml, Object... values) {
		T.call(this);

		if(values.length > 0) {

			Object firstValue = values[0];

			formatValue(builder, isHtml, firstValue);

			for(int i = 1; i < values.length; i++) {

				Object value = values[i];

				builder.append(", ");

				formatValue(builder, isHtml, value);
			}
		}
	}

	private void formatValue(StringBuilder builder, boolean isHtml, Object value) {
		T.call(this);

		if(isHtml) {
			builder.append("<code>");
		}

		if(value == null) {

			builder.append("null");

		}else if(value instanceof String) {

			builder.append("\"");
			builder.append(value);
			builder.append("\"");

		}else if(value instanceof Double || value.getClass().equals(double.class)) {

			builder.append(String.format("%.2f", value));

		}else if(value instanceof Float || value.getClass().equals(float.class)) {

			builder.append(String.format("%.2f", value));

		}else if(value instanceof Boolean || value.getClass().equals(boolean.class)) {

			builder.append(value);

		}else if(value instanceof Integer || value.getClass().equals(int.class)) {

			builder.append(value);

		}else if(value instanceof Long || value.getClass().equals(long.class)) {

			builder.append(value);

		}else if(value.getClass().isArray()) {

			builder.append("[");

			if(Array.getLength(value) > 0) {
				formatValue(builder, isHtml, Array.get(value, 0));
			}

			for(int i = 1; i < Array.getLength(value); i++) {
				builder.append(",");
				formatValue(builder, isHtml, Array.get(value, i));
			}

			builder.append("]");


		}else if(overridesToString(value)) {

			builder.append(value.toString());

		}else {

			builder.append(Ntro.introspector().getSimpleNameForClass(value.getClass()));
			builder.append("@");
			builder.append(intToHex(System.identityHashCode(value)));
		}

		if(isHtml) {
			builder.append("</code>");
		}
	}

	private boolean overridesToString(Object value) {
		T.call(this);

		boolean result = false;

		List<Method> userDefinedMethods = Ntro.introspector().userDefinedMethodsFromObject(value);

		for(Method userDefinedMethod : userDefinedMethods) {

			if(userDefinedMethod.getName().equals("toString")) {
				result = true;
				break;
			}
		}

		return result;
	}

	private String intToHex(int input) {
		T.call(this);

		String result;

		BigInteger bigInt = BigInteger.valueOf(input);
		byte[] bytes = bigInt.toByteArray();

	    Formatter formatter = new Formatter();

	    for (byte b : bytes) {
	        formatter.format("%02x", b);
	    }

	    result = formatter.toString();
	    formatter.close();

		return result;
	}








}
