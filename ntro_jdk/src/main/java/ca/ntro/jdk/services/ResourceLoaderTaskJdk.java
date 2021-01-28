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

import java.io.InputStream;
import java.util.Scanner;

import ca.ntro.core.services.ResourceLoaderTask;
import ca.ntro.core.system.assertions.MustNot;
import ca.ntro.core.system.trace.T;

public class ResourceLoaderTaskJdk extends ResourceLoaderTask {
	
	private String resourceAsString;

	public ResourceLoaderTaskJdk(String resourcePath) {
		super(resourcePath);
	}

	@Override
	public String getResourceAsString() {
		return resourceAsString;
	}

	@Override
	protected void runTaskAsync() {
		T.call(this);
		
		InputStream resourceStream = ResourceLoaderTask.class.getResourceAsStream(getResourcePath());
		
		MustNot.beNull(resourceStream);
		
		Scanner scanner = new Scanner(resourceStream);
		
		StringBuilder builder = new StringBuilder();

		while(scanner.hasNextLine()) {
			builder.append(scanner.nextLine());
			builder.append(System.lineSeparator());
		}
		
		resourceAsString = builder.toString();
		
		scanner.close();
		
		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
