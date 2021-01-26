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

package ca.aquiletour.core.pages.rootpage;

import ca.aquiletour.core.messages.OpenDashboardMessage;
import ca.aquiletour.core.messages.OpenSettingsMessage;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.mvc.view.NtroView;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTask;
import ca.ntro.messages.MessageFactory;

public abstract class RootpageController extends NtroTask {
	
	private RootpageView rootpageView;
	
	public RootpageController(String lang) {
		T.call(this);
		
		addSubTask(loadView(lang),"ViewLoader");

		MessageFactory.addMessageReceptor(OpenSettingsMessage.class, new OpenSettingsReceptor(this));
		MessageFactory.addMessageReceptor(OpenDashboardMessage.class, new OpenDashboardReceptor(this));
	}

	protected abstract ViewLoader loadView(String lang);
	protected abstract NtroWindow getWindow();

	@Override
	protected void runTask() {
		T.call(this);
		
		ViewLoader viewLoader = getSubTask(ViewLoader.class,"ViewLoader");
		getWindow().installRootView(viewLoader);
		
		rootpageView = (RootpageView) viewLoader.getView();

		notifyTaskFinished();
	}

	@Override
	protected void onFailure(Exception e) {
		
	}

	public void openSettings() {
		T.call(this);
		
		// FIXME: this must finish before
		//        the WriteResponse task
		//rootpageView.installSubPage(page);
		
	}

	public void openDashboard() {
		T.call(this);
		
	}

}
