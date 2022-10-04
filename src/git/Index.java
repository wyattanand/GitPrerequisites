package git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Index {
	FileWriter index; //table of contents, list on every signle item, and where it exists 
	HashMap <String, Blob> objects; 
	File head;
	public Index() throws IOException {
		initialize();
	}
	public void initialize() throws IOException {
		index = new FileWriter("./index.txt"); 
		objects = new HashMap <String,Blob> ();
		head = new File("./HEAD");
		File theDir = new File("/path/objects");
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
	}
	public void delete(String fileName) throws NoSuchAlgorithmException, IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("./index"));
		writer.append("*deleted*" + fileName);
		writer.close();
	}
	public void addBlob(String fileName) throws IOException {
		Blob newBlobWithFileName = new Blob (fileName);
//		objects.put(fileName,newBlobWithFileName);
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
