package git;

import java.io.File;
import java.util.HashMap;

public class Index {
	File emptyFile = new File(null); 
	HashMap objects = new HashMap (String,String); 
	public Index() {
		
		
	}
	public void addBlob(String fileName) {
		Blob newBlobWithFileName = new Blob (fileName);
		objects.add()
	}
	
}
