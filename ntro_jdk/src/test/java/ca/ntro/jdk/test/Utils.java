package ca.ntro.jdk.test;

import java.io.File;

public class Utils {

	public static void deleteDir(File aDir) {
		String[] fileNames = aDir.list();
		
		for(String fileName : fileNames) {

			File file = new File(aDir, fileName);
			
			if(file.isDirectory()) {
				deleteDir(file);
			}else {
				file.delete();
			}
		}
		
		aDir.delete();
	}

}
