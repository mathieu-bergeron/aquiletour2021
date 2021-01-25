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

package ca.aquiletour.web.page.rootpage;

import ca.aquiletour.core.pages.rootpage.RootpageMain;
import ca.ntro.core.Ntro;
import ca.ntro.core.mvc.view.ViewLoader;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.web.NtroWindowWeb;

public abstract class RootpageMainWeb extends RootpageMain {

	public RootpageMainWeb(String lang) {
		super(lang);
	}

	protected ViewLoader loadView(String lang) {
		return Ntro.viewLoaderWeb()
		           .setHtmlUrl("/public/views/rootpage/structure.html")
		           .setCssUrl("/public/views/rootpage/style.css")
		           .setTranslationsUrl("/public/i18/"+lang+"/strings.json");
	}

	// FIXME: we specialize return type 
	//        is this supported in JSweet???
	@Override
	protected abstract NtroWindowWeb getWindow();
	
	public void writeHtml(StringBuilder out) {
		T.call(this);
		
		getWindow().writeHtml(out);
	}
}
