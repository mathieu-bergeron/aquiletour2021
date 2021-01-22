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

package ca.ntro.core.system.source;


public class SourceFileLocation {
	
	// FIXME: is this always / ??
	private static final String PATH_DELEMITOR = "/";
	
	private String fileName;
	private int line;

	public SourceFileLocation(String rawFilePath, int line) {
		this.fileName = extractFilename(rawFilePath);
		this.line = line;
	}
	
	private String extractFilename(String rawFilePath) {
		String[] pathSplits = rawFilePath.split(PATH_DELEMITOR);
		
		return pathSplits[pathSplits.length-1];
	}
	
	public String toString() {
		return fileName + ":" + line;
	}

	public void formatClassName(StringBuilder builder) {
		builder.append(fileName);
	}

}
