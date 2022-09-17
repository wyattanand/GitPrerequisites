package git;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Blob {
	String files = ""; 
	String sha1 = "";
	String value = "";
	List<String> shahash = new ArrayList <> ();
	
	public Blob (String filePath) throws IOException {
		value = filePath; 
		System.out.println ("shidded on em");
		System.out.println("naalah"); 
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(value.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
		Scanner scanOfFile = new Scanner(new File(filePath));//scanning filePath contents
		FileWriter fileWriterOfSha = new FileWriter("./objects/" + sha1);
		PrintWriter printWriterofFileWriter = new PrintWriter (fileWriterOfSha);
		while(scanOfFile.hasNextLine())
	     {
	        String str = scanOfFile.nextLine();
	        fileWriterOfSha.write(str);
	     }
		if(scanOfFile != null) {
			scanOfFile.close();  
		    }
		    if(fileWriterOfSha != null) {
		    	fileWriterOfSha.flush();
		    	fileWriterOfSha.close();
		     }
		
	}
	
	public String getSha() {
		return sha1; 
	}
	
	public static String getContent (String filepath) throws IOException {
		File file = new File (filepath);
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		StringBuilder sb = new StringBuilder(); 
		String line = br.readLine(); 
		while (line != null) { 
			sb.append(line).append("\n"); 
			line = br.readLine(); 
		} 
		String fileAsString = sb.toString();
		return fileAsString;
	} 

}
	
	
	

