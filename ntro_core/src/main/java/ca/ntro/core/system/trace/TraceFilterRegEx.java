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

package ca.ntro.core.system.trace;

import ca.ntro.core.regex.Pattern;
import ca.ntro.services.Ntro;

public class TraceFilterRegEx extends TraceFilter {
	
	private Pattern exclude;
	private Pattern include;
	
	public TraceFilterRegEx(String exclude, String include) {
		__T.call(this, "<init>");
		
		this.exclude = Ntro.regEx().compilePattern(exclude);
		this.include = Ntro.regEx().compilePattern(include);
	}

	@Override
	public boolean shouldDisplayTrace(String cannonicalClassName, String methodName, String fileName, int lineNumber) {
		boolean shouldExcludeClassName = exclude.matcher(cannonicalClassName).matches();
		boolean shouldIncludeClassName = include.matcher(cannonicalClassName).matches();
		
		return !shouldExcludeClassName && shouldIncludeClassName;
	}

}
