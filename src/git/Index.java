package git;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Index {
	FileWriter index; //table of contents, list on every signle item, and where it exists 
	HashMap <String, Blob> objects; 
	public Index() throws IOException {
		index = new FileWriter("index"); 
		objects = new HashMap <String,Blob> ();
		File theDir = new File("/path/objects");
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
	}
	public void initialize() {
		// creates empty object folder 
	}
	public void addBlob(String fileName) throws IOException {
		Blob newBlobWithFileName = new Blob (fileName);
		objects.put(fileName,newBlobWithFileName);
		index.append(fileName + " : " + newBlobWithFileName); 
	}
	
	public void writeAllOfIndex () throws IOException {
		for(String str : objects.keySet()) {
			index.append(str + " : " ); 
			index.append(" : " + objects.get(str).getSha());
		}
	}
	
	 
	public void removeBlob(String fileName) throws IOException {
		objects.remove(fileName);
		writeAllOfIndex();
		FileWriter fileToDelete = new FileWriter("./objects/"+ objects.get(fileName).getSha());
		fileToDelete.close(); 	}
	
}
