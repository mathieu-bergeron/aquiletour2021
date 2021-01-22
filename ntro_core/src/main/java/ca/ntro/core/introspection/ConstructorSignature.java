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

public class ConstructorSignature extends ProcedureSignature {
	private static final long serialVersionUID = -5864624951077473662L;

	public ConstructorSignature(String name, List<String> argumentTypes, List<String> modifiers) {
		super(name, argumentTypes, modifiers);
		T.call(this);
	}

	public static ConstructorSignature fromString(String signatureString) {
		T.call(ConstructorSignature.class);
		
		List<String> modifiers = new ArrayList<>();
		
		String[] splitName = signatureString.split("\\(");
		String modifiersReturnTypeName = splitName[0];
		
		String[] modifiersReturnTypeNameSplit = modifiersReturnTypeName.split(" ");
		
		for(int i = 0; i < modifiersReturnTypeNameSplit.length - 1; i++) {
			modifiers.add(modifiersReturnTypeNameSplit[i]);
		}

		String name = modifiersReturnTypeNameSplit[modifiersReturnTypeNameSplit.length - 1].replace(" ", "");

		String args = splitName[1];
		args = args.replace(")", "");
		
		String[] argTypes = args.split(", ");
		
		List<String> argumentTypes = new ArrayList<>();
		for(String argType : argTypes) {
			argumentTypes.add(argType.replace(" ", ""));
		}

		return new ConstructorSignature(name, argumentTypes, modifiers);
	}
}
