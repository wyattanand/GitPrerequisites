package git;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class Index {
	FileWriter index = new FileWriter(null); 
	HashMap objects = new HashMap (String ,Blob); 
	public Index() {
		FileWriter index = new FileWriter(null); 
		
	}
	public void addBlob(String fileName) {
		Blob newBlobWithFileName = new Blob (fileName);
		objects.put(fileName,newBlobWithFileName);
		index.append(fileName + newBlobWithFileName); 
	}
	public void removeBlob(String fileName) {
		objects.remove(fileName);
		
	}
	
}
