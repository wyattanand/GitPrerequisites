package git;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Index {
	FileWriter index; //table of contents, list on every signle item, and where it exists 
	HashMap objects; 
	public Index() throws IOException {
		index = new FileWriter("index"); 
		objects = new HashMap <String,Blob> ();
		
		
	}
	public void initialize() {
		// creates empty object folder 
	}
	public void addBlob(String fileName) throws IOException {
		Blob newBlobWithFileName = new Blob (fileName);
		objects.put(fileName,newBlobWithFileName);
		index.append(fileName + " : " + newBlobWithFileName); 
	}
	
	 
	public void removeBlob(String fileName) throws IOException {
		objects.remove(fileName);
		FileWriter fileToDelete = new FileWriter("./objects/" + objects.get(fileName).getSha());
		fileToDelete.close(); 
	}
	
}
