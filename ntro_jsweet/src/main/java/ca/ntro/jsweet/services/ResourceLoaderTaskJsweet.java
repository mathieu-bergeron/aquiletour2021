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

package ca.ntro.jsweet.services;

import java.util.function.Consumer;

import ca.ntro.services.ResourceLoaderTask;
import def.es6.Globals;
import def.es6.Globals.FetchResponse;

public class ResourceLoaderTaskJsweet extends ResourceLoaderTask {
	
	private String resourceAsString;

	public ResourceLoaderTaskJsweet(String resourcePath) {
		super(resourcePath);
	}

	@Override
	public String getResourceAsString() {
		return resourceAsString;
	}

	@Override
	protected void runTaskAsync() {
		Globals.fetch(getResourcePath()).then(new Consumer<FetchResponse>() {
			@Override
			public void accept(FetchResponse response) {
				response.text().then(new Consumer<String>() {
					@Override
					public void accept(String text) {
						resourceAsString = text;
						notifyTaskFinished();
					}
				});
			}
		});
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
