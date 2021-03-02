package ca.ntro.jsweet.test;

import ca.ntro.jsweet.NtroJSweet;

public class JavaMainTest {
	
	public static void main(String[] args) {
		
		String[] options = new String[] {"--traceLevel","APP"};
		
		NtroJSweet.defaultInitializationTask()
				  .setOptions(options)
				  .addNextTask(new NtroMainTest())
				  .execute();
	}
}
