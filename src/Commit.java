import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import git.Tree;

public class Commit {
	private String pTree; 
	private String author1;
	private String date;
	private String summary;
	private String nameOfFile; 
	private String sha1;
	private String pointer;
	public Commit(String author, String pTreeValue, String sumOfChanges, String pointerParent) throws IOException {
		author1 = author;
		summary = sumOfChanges; 
		date = getDate (); 
		sha1 = generateSha1(summary,date,author1,pointer);
		pTree = pTreeValue;
		if(pointer != null) {
			pointer = pointerParent; 
		}
		else {
			pointer = null;
		}

		writeFile(); 
		
	}
	
	public String generateSha1 (String summary, String date, String author, String pointer) {
		String value = summary + date + author + pointer;
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(value.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}

		return sha1;
	}
	
	public String getDate () {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
	
	public void writeFile () throws IOException {
		FileWriter fw = new FileWriter(new File("objects", sha1));
		fw.write(pTree); // 1 writing p Tree value 
		fw.write("idk"); // 2 writing location of previous commit
		fw.write("idk2"); // 3 writing location of next commit
		fw.write(author1); //  4 writing author
		fw.write(date); // 5 writing date
		fw.write(summary); // 6 writing location of previous commit
		fw.close();
		
	}
}
