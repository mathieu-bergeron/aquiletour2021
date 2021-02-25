package ca.ntro.jsweet.test;

import java.io.File;

import org.jsweet.transpiler.JSweetTranspiler;
import org.jsweet.transpiler.ModuleKind;
import org.jsweet.transpiler.SourceFile;
import org.junit.Test;

public class NtroJSon {

	@Test
	public void initializeNtro() throws Exception {
	   TestLogHandler logHandler = new TestLogHandler();
	   
	   // FIXME: crashes on java.lang.NoClassDefFoundError: com/sun/tools/javac/tree/JCTree

	   //JSweetTranspiler transpiler = new JSweetTranspiler(null);

	   //transpiler.setModuleKind(ModuleKind.commonjs);

	   // here, the transpiler uses node.js to evaluate the generated JavaScript
	   //transpiler.eval(logHandler, SourceFile.getSourceFiles(new File("src/test/java/ca/ntro/jsweet/test/TestMain.java")));
	}
}
