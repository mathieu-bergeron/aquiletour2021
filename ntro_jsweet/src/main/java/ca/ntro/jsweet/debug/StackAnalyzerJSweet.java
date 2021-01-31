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

package ca.ntro.jsweet.debug;

import ca.ntro.core.system.source.SourceFileLocation;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.stack.StackFrame;
import def.js.Error;
import static def.dom.Globals.window;

public class StackAnalyzerJSweet extends StackAnalyzer {

	@Override
	public StackFrame getTracedFrameImpl(String className, int finalStackOffset) {

		StackFrame result = null;

		// XXX: def.js.Error
		Error error = new Error();

		String[] stackLines = error.stack.split("\n");

		if(stackLines[0].equals("Error")) {

			result = parseChromiumStackLines(className, finalStackOffset, stackLines);

		}else {

			result = parseFirefoxStackLines(className, finalStackOffset, stackLines);

		}

		return result;
	}

	private StackFrame parseFirefoxStackLines(String className, int finalStackOffset, String[] stackLines) {
		String stackLine = stackLines[finalStackOffset];

		String filePrefix = window.location.origin;
		String fileName = stackLine.split("@")[1].replace(filePrefix, "").split(":")[0];

		String methodName = stackLine.split("@")[0];

		String[] colonSplits = stackLine.split(":");

		String rawLineNumber = colonSplits[colonSplits.length-2];
		String rawColumnNumber = colonSplits[colonSplits.length-1];

		int line = Integer.valueOf(rawLineNumber);
		int column = Integer.valueOf(rawColumnNumber);

		SourceFileLocation location = SourceMapAnalyzer.getOriginalLocation(fileName, line, column);

		// FIXME
		return new StackFrame(className, methodName, location);
	}

	private StackFrame parseChromiumStackLines(String className, int finalStackOffset, String[] stackLines) {

		// XXX: first line is "Error", not a call
		String stackLine = stackLines[finalStackOffset+1];

		// XXX: strip starting ' '
		// TODO: refactor in a JSweet String utils
		while(stackLine.startsWith(" ")) {
			stackLine = stackLine.substring(1);
		}

		String methodName = stackLine.split("at ")[1];

		if(methodName.startsWith("new")) {

			methodName = methodName.split("new ")[1];

		}else {

			// XXX: JSweet split is NOT regexp
			methodName = methodName.split(".")[1];
			methodName = methodName.split(" ")[0];

		}

		String[] colonSplits = stackLine.split(":");
		String rawLineNumber = colonSplits[colonSplits.length-2];
		String rawColumnNumber = colonSplits[colonSplits.length-1].replace(")","");

		int lineNumber = Integer.valueOf(rawLineNumber);
		int columnNumber = Integer.valueOf(rawColumnNumber);

		SourceFileLocation location = SourceMapAnalyzer.getOriginalLocation("something", lineNumber, columnNumber);

		// FIXME
		return new StackFrame(className, methodName, location);
	}

	@Override
	protected int getInitialStackOffset() {
		return 1;
	}


}
