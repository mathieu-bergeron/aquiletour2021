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

package ca.ntro.core.system.log;

import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.stack.StackFrame;
import ca.ntro.services.Ntro;
import ca.ntro.services.__Ntro;

public class Log {
	
	public static void info(String... messages) {
		if(!Ntro.config().isProd()) {
			for(String message : messages) {
				System.out.println(timestamp() + message);
			}
		}
	}
	

	private static String timestamp() {
		return "[" + Ntro.calendar().now().format("yyyy-MM-dd HH:mm:ss") + "] ";
	}

	public static void warning(String... messages) {
		if(!Ntro.config().isProd()) {
			for(String message : messages) {
				System.err.println(timestamp() + message);
			}
		}
	}

	public static void error(String... messages) {
		for(String message : messages) {
			System.err.println(timestamp() + message);
		}
	}

	public static void fatalErrorAtDepth(String message, int currentDepth, Exception... causedBy) {
		
		StackAnalyzer stackAnalyzer = __Ntro.stackAnalyzer();
		
		if(stackAnalyzer != null) {

			StackFrame tracedFrame = stackAnalyzer.getTracedFrame(null, currentDepth);
			
			StringBuilder builder = new StringBuilder();
			
			builder.append("#FATAL | " );
			
			builder.append(message);

			builder.append(" (" );

			tracedFrame.printSourceLocation(builder);

			builder.append(")" );
			
			if(causedBy != null && causedBy.length > 0) {

				builder.append("\ncaused by\n");
				System.out.println(builder.toString());

				causedBy[0].printStackTrace();
				
			}else {

				System.out.println(builder.toString());
				
			}

			Ntro.appCloser().close();
		}

	}
	
	public static void fatalError(String message, Exception... causedBy) {
		fatalErrorAtDepth(message, 1, causedBy);
	}

}
