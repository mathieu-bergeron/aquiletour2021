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

package ca.ntro.core.system.stack;

import ca.ntro.core.system.source.SourceFileLocation;

public class StackFrame {

	private String className;
	private String methodName;
	private SourceFileLocation location;

	public StackFrame(String className, String methodName, SourceFileLocation location) {
		this.className = className;
		this.methodName = methodName;
		this.location = location;
	}

	public void printFrame(StringBuilder builder) {
		printSourceLocation(builder);
		builder.append(" >> ");
		if(className != null) {
			builder.append(className);
		}else {
			location.formatClassName(builder);
		}
		builder.append(".");
		builder.append(methodName);
	}
	
	public void printSourceLocation(StringBuilder builder) {
		builder.append(" (");
		builder.append(location);
		builder.append(")");
	}


}
