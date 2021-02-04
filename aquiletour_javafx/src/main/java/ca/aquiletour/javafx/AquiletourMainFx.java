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

package ca.aquiletour.javafx;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.root.RootView;
import ca.ntro.core.mvc.ViewLoaders;
import ca.ntro.core.system.trace.T;
import ca.ntro.javafx.ViewLoaderFx;

public class AquiletourMainFx extends AquiletourMain {

	@Override
	protected void registerViewLoaders() {
		T.call(this);

		ViewLoaders.registerViewLoader(RootView.class,
				"fr"
				, new ViewLoaderFx()
			     	.setFxmlUrl("/views/root/structure.xml")
			     	.setCssUrl("/views/root/style.css")
			     	.setTranslationsName("i18n.strings"));
		
	}
}
