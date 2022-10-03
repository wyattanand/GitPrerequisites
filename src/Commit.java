import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import git.Tree;

public class Commit {
	private String author1;
	private String date;
	private String summary;
	private String nameOfFile; 
	private String sha1;
	private String pointer;
	private String nextPointer;
	private Tree tree;
	private Commit pCommit;
	public Commit(String author, String sumOfChanges, Commit parent) throws Exception {
		Scanner scanny = new Scanner("./index");
		String fileName;
		String sha1;
		pCommit = parent;
		ArrayList<String> list = new ArrayList<String>();
		while (scanny.hasNext()) {
			fileName = scanny.next();
			sha1 = (scanny.next() + scanny.next()).substring(1);
			list.add("blob : " + sha1 + " " + fileName);
		}
		list.add("tree : " + pCommit.tree.sha1);
		tree = new Tree(list);
		File oldIndexFile = new File("./index");
		oldIndexFile.delete();
		File newIndexFile = new File("./index");
		author1 = author;
		summary = sumOfChanges; 
		date = getDate (); 
		sha1 = generateSha1(summary,date,author1,pCommit.sha1);
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
	
	public boolean delete(String fileName) {
		
		return false;
	}
	
	public void writeFile () throws IOException {
		FileWriter fw = new FileWriter(new File("objects", sha1));
		fw.write(tree.sha1); // 1 writing p Tree value 
		fw.write(pointer); // 2 writing location of previous commit
		fw.write(nextPointer); // 3 writing location of next commit
		fw.write(author1); //  4 writing author
		fw.write(date); // 5 writing date
		fw.write(summary); // 6 writing location of previous commit
		fw.close();
	}
}

