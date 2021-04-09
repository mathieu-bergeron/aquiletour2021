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
import def.js.Function;

public class SourceMapAnalyzer {

	private static Function analyzerFunction;

	public static void initialize(Function sourceMapAnalyzer) {
		SourceMapAnalyzer.analyzerFunction = sourceMapAnalyzer;
	}

	public static SourceFileLocation getOriginalLocation(String fileName, int line, int column) {
		if(SourceMapAnalyzer.analyzerFunction == null) return new SourceFileLocation("unknown", 0);

		//System.out.println("line, column " + line + " " + column);

		def.js.Object lineColumn = new def.js.Object();
		lineColumn.$set("fileName", fileName);
		lineColumn.$set("line", line);
		lineColumn.$set("column", column);

		def.js.Object result = SourceMapAnalyzer.analyzerFunction.$apply(lineColumn);

		/*
		String fileName = result.$get("source").toString();
		int resultLine = Integer.valueOf(result.$get("line").toString());
		int resultColumn = Integer.valueOf(result.$get("column").toString());
		*/

		String resultingFileName = result.$get("source");
		int resultLine = result.$get("line");

		return new SourceFileLocation(resultingFileName, resultLine);
	}


}
