package ca.aquiletour.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TestUtils {
	
	public static void installTestData() throws IOException {
		
		File dataDir = new File("__data__");
		if(dataDir.exists()) {
			deleteDirectory(dataDir);
		}

		copyDirectory(new File("__test_data__"), new File("__data__"));
	}

	private static void deleteDirectory(File file) throws IOException {
		
		if(file.isDirectory()) {
			String subFilePaths[] = file.list();

			for (String subFilePath : subFilePaths) {
				deleteDirectory(new File(file, subFilePath));
			}
		}
		
		file.delete();
	}
	
	private static void copyDirectory(File src, File dest) throws IOException {
	    if(src.isDirectory()){
	        if(!dest.exists()){
	            dest.mkdir();
	        }

	        String subFilePaths[] = src.list();

	        for (String subFilePath : subFilePaths) {
	            File nextSrcFile = new File(src, subFilePath);
	            File nextDestFile = new File(dest, subFilePath);

	            copyDirectory(nextSrcFile,nextDestFile);
	        }

	    } else {
	    	Files.copy(src.toPath(), dest.toPath());
	    }
	}
}
