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

package ca.ntro.core.introspection;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.system.trace.T;

public abstract class ProcedureSignature {
	
	/*
	private static List<String> possibleModifiers = new ArrayList<>();
	static {
		possibleModifiers.add("public");
		possibleModifiers.add("protected");
		possibleModifiers.add("private");
		possibleModifiers.add("transient");
		possibleModifiers.add("volatile");
		possibleModifiers.add("static");
		possibleModifiers.add("final");
		possibleModifiers.add("strict");
		possibleModifiers.add("abstract");
		possibleModifiers.add("interface");
	}*/

	private String name;
	private List<String> argumentTypes = new ArrayList<>();
	private List<String> modifiers = new ArrayList<>();

	public ProcedureSignature(String name, List<String> argumentTypes, List<String> modifiers) {
		T.call(this);
		
		this.name = name;
		this.argumentTypes = argumentTypes;
		this.modifiers = modifiers;
	}
	
	@Override
	public String toString() {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();
		
		formatModifiers(builder);
		
		formatName(builder);

		formatArguments(builder);
		
		return builder.toString();
	}

	protected void formatArguments(StringBuilder builder) {
		T.call(this);

		builder.append("(");
		
		if(argumentTypes.size() > 0) {
			builder.append(argumentTypes.get(0).toString());
			
			for(int i = 1; i < argumentTypes.size(); i++) {
				builder.append(", ");
				builder.append(argumentTypes.get(i).toString());
			}
		}

		builder.append(")");
	}

	protected void formatName(StringBuilder builder) {
		T.call(this);

		builder.append(" ");
		builder.append(name);
	}

	protected void formatModifiers(StringBuilder builder) {
		T.call(this);

		for(String modifier : modifiers) {
			builder.append(modifier);
			builder.append(" ");
		}
	}

	public String name() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> argumentTypes() {
		return argumentTypes;
	}

	public void setArgumentTypes(List<String> argumentTypes) {
		this.argumentTypes = argumentTypes;
	}

	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ProcedureSignature) {
			
			ProcedureSignature other = (ProcedureSignature) obj;
			
			return other.toString().equals(this.toString());
			
		}else {
			
			return false;
		}
	}
	
	public List<String> modifiers() {
		return modifiers;
	}

	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}
	
	public boolean isPrivate() {
		return modifiers.contains("private");
	}

}
