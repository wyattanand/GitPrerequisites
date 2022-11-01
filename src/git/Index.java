package git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Index {
	File index; //table of contents, list on every signle item, and where it exists 
	HashMap <String, String> objects; 
	File head;
	public Index() throws IOException {
		initialize();
	}
	public void initialize() throws IOException {
		index = new File("./index"); 
		objects = new HashMap <String,String> ();
		head = new File("./HEAD");
		new File("./objects").mkdirs();
		
	}
	public void delete(String fileName) throws NoSuchAlgorithmException, IOException {
		objects.put(fileName, "*deleted*");
		writeFile();
	}
	public void addBlob(String fileName) throws IOException {
		Blob newBlob = new Blob (fileName);
		objects.put(fileName, newBlob.getSha());
		writeFile();
	}
	private void writeFile() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter("./index");
		for (String key: objects.keySet()) {
			if (objects.get(key).equals("*deleted*")) {
				pw.println(objects.get(key) + " " + key);
			}
			else {
				pw.println(key + " : " + objects.get(key));
			}
		}
		pw.close();
	}
	
	
	/*
	public void writeAllOfIndex () throws IOException {
		for(String str : objects.keySet()) {
			index.append(str + " : " ); 
			index.append(" : " + objects.get(str).getSha());
		}
	}
	*/
	/*
	public void removeBlob(String fileName) throws IOException {
		objects.remove(fileName);
		writeAllOfIndex();
		FileWriter fileToDelete = new FileWriter("./objects/"+ objects.get(fileName).getSha());
		fileToDelete.close(); 	}
	*/
}
