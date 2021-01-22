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

package ca.ntro.core.system.assertions;

public class ValueExpr extends AssertExpr {
	
	private Object javaValue;

	public ValueExpr(Object javaValue) {
		this.javaValue = javaValue;
	}

	public AssertExpr contains(Object o) {
		AssertExpr arg = null;

		if(o instanceof AssertExpr) {

			arg = (AssertExpr) o;

		}else {
			
			arg = value(o);
		}
		
		return new ContainsExpr(this, arg);
	}

	@Override
	public Object evaluate() {
		return javaValue;
	}

}
