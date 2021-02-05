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

package ca.ntro.core.system.trace;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.services.Logger;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.stack.StackFrame;

public class T {
	
	//private static TraceFilter previousFilter = TraceLevel.APP;
	//private static TraceFilter traceFilter = TraceLevel.APP;

	private static TraceFilter previousFilter = TraceLevel.ALL;
	private static TraceFilter traceFilter = TraceLevel.ALL;
	
	private static Introspector introspector = null;
	private static StackAnalyzer stackAnalyzer = null;
	private static Logger logger = null;

	public static void __registerIntrospector(Introspector introspector) {
		T.introspector = introspector;
	}
	
	public static void __registerStackAnalyzer(StackAnalyzer stackAnalyzer) {
		__T.call(T.class, "registerStackAnalyzer");

		T.stackAnalyzer = stackAnalyzer;
	}
	
	public static void __registerLogger(Logger logger) {
		__T.call(T.class, "registerLogger");
		
		T.logger = logger;
	}

	public static void setTraceLevel(TraceFilter traceFilter) {
		T.previousFilter = T.traceFilter;
		T.traceFilter = traceFilter;
		
		__T.setTraceLevel(traceFilter);
	}

	public static void revertToPreviousLevel() {
		T.traceFilter = previousFilter;

		__T.setTraceLevel(previousFilter);
	}

	static Class<?> getCalledClass(Object called){
		Class<?> calledClass = called.getClass();
		
		// FIXME: replace by making sure that
		//        Introspector is initialized
		//        We also need to check if class is a anon class (has __parent attribute in JSweet)

		// XXX: Hacky, but Ntro.intropsector might not be registered yet
		if(T.class.getClass().getSimpleName().equals("Function")) {
			// in JSweet
			if(calledClass.getSimpleName().equals("Function")) {
				calledClass = (Class<?>) called;
			}

		// in Java
		}else if(called instanceof Class){
			calledClass = (Class<?>) called;
		}

		return calledClass;
	}

	public static void call(Object called) {
		int stackOffset = 1;

		traceCall(getCalledClass(called), stackOffset);
	}
	
	private static void traceCall(Class<?> calledClass, int stackOffset) {
		stackOffset++;
		
		if(traceFilter.shouldDisplayTrace(calledClass.getName(), "", "", 0)) {
			String className = calledClass.getSimpleName();
			
			try {

				StackFrame tracedFrame = stackAnalyzer.getTracedFrame(className, stackOffset);
				printTrace(tracedFrame);

			}catch(NullPointerException e) {
				System.out.println("#T.call (" + className + ".java)");
			}
		}
	}
	
	private static void printTrace(StackFrame tracedFrame) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("#T.call");

		tracedFrame.printFrame(builder);
		
		try {
			
			logger.text(builder.toString());
			
		}catch(NullPointerException e) {

			System.out.println(builder.toString());
		}
	}


	public static void here() {
		__T.call(T.class, "here");
		int stackOffset = 1;

		try {

			StackFrame tracedFrame = stackAnalyzer.getTracedFrame(null, stackOffset);
			
			StringBuilder builder = new StringBuilder();
			builder.append("#T.here");
			tracedFrame.printSourceLocation(builder);
			logger.text(builder.toString());

		}catch(NullPointerException e) {
			try {
				logger.text("#T.here");
			}catch(NullPointerException e2) {
				System.out.println("#T.here");
			}
		}
	}

	public static void values(Object... values) {
		__T.call(T.class, "values");
		int stackOffset = 1;

		StringBuilder prefix = new StringBuilder();
		prefix.append("#T.vals");

		try {
			StackFrame tracedFrame = stackAnalyzer.getTracedFrame(null, stackOffset);
			tracedFrame.printSourceLocation(prefix);
		}catch(NullPointerException e) {}

		prefix.append(" >> ");
		prefix.append(values.length);
		prefix.append(" values:");

		try {
			logger.text(prefix.toString());
		}catch(NullPointerException e) {
			System.out.println(prefix.toString());
		}

		for(int i = 0; i < values.length; i++) {
			try {
				logger.value(values[i]);
			}catch(NullPointerException e) {
				System.out.println(values[i]);
			}
		}
	}

}
