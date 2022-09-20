import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import git.Tree;

public class Commit {
	private Tree pTree; 
	private String author1;
	private String date;
	private String summary;
	private String nameOfFile; 
	public Commit(String author, String pTreeValue, String sumOfChanges, String pointerParent) {
		author1 = author;
		summary = sumOfChanges; 
		date = getDate (); 
		
		
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
	
	public void writeFile () {
		FileWriter fw = new FileWriter(new File("objects", str + ".txt"));
		for(String stringe: arr) {
		  fw.write(str + System.lineSeparator());
		}
		fw.close();
		
	}
}
