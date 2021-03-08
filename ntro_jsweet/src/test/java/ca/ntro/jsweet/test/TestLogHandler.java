package ca.ntro.jsweet.test;

import org.jsweet.transpiler.JSweetProblem;
import org.jsweet.transpiler.JSweetTranspiler;
import org.jsweet.transpiler.SourceFile;
import org.jsweet.transpiler.SourcePosition;
import org.jsweet.transpiler.TranspilationHandler;

public class TestLogHandler implements TranspilationHandler {

	@Override
	public void report(JSweetProblem problem, SourcePosition sourcePosition, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCompleted(JSweetTranspiler transpiler, boolean fullPass, SourceFile[] files) {
		// TODO Auto-generated method stub
		
	}

}
