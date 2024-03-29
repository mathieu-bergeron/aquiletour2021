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

package ca.ntro.jdk;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

public class FileLoaderDev extends FileLoader {

	// XXX: in dev, load from ./src/main/resources
	private final String DEV_PATH_PREFIX = "./src/main/resources";
	//private final String DEV_PATH_PREFIX = "";
	
	// TODO: in prod:
	//   * load via getResourceAsStream so we can load from a .jar
	//   * check if file is in memory cache

	@Override
	public InputStream loadFile(String filePath) {
		
		String resourcePath = Paths.get(DEV_PATH_PREFIX, filePath).toString();
		
		FileInputStream result = null;
		
		try {
			result = new FileInputStream(new File(resourcePath));
		} catch (FileNotFoundException e) {

			System.err.println("[WARNING] loadFile: file not found" + resourcePath);

		}
		
		return result;
	}

}
