import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	private String pPointer;
	private Commit nextPointer;
	private Tree tree;
	private Commit pCommit;
	private ArrayList<String> array = new ArrayList<String>();
	public Commit(String author, String sumOfChanges, Commit parent) throws Exception {
		File indexFile = new File("./index");
		Scanner scanny = new Scanner(indexFile);
		String nextLine;
		String sha;
		String deletedName;
		pCommit = parent;
		connectParent();
		File HEAD = new File("./HEAD");
		if (HEAD.exists()) {
			Scanner head = new Scanner(HEAD);
			pPointer = head.nextLine();
		}
		if (pPointer != null) {
			ArrayList<String> fileContent = new ArrayList<String>();
		}
		ArrayList<String> list = new ArrayList<String>();
		while (scanny.hasNextLine()) {
			nextLine = scanny.nextLine();
			if (nextLine.contains("*deleted*")) {
				deletedName = nextLine.substring(nextLine.indexOf(" ") + 1);
				deleteFile(deletedName, getPrevTree());
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).contains(deletedName)) {
						list.remove(i);
					}
				}
			} else {
				list.add("blob :" + nextLine.substring(nextLine.indexOf(':')+1) + " " + nextLine.substring(0, nextLine.indexOf(':')-1));
			}
		}
		for (int i = 0; i < array.size(); i++) {
			list.add(array.get(i));
		}
		if (pCommit != null) {
			list.add("tree : " + getPrevTree().getSha1());
		}
		tree = new Tree(list);
		scanny.close();
		File oldIndexFile = new File("./index");
		oldIndexFile.delete();
		File newIndexFile = new File("./index");
		author1 = author;
		summary = sumOfChanges; 
		date = getDate (); 
		String parentSha = "";
		if (pCommit != null) {
			parentSha = pCommit.getSha1();
		}
		sha1 = generateSha1(summary,date,author1, parentSha);
		writeFile(); 
		FileWriter headWriter = new FileWriter("./HEAD");
		headWriter.write(sha1);
		headWriter.close();
	}
	public boolean deleteFile(String fileName, Tree t) throws FileNotFoundException {
		array.add(0, t.getSha1());
		File tFile = new File("./objects/" + t.getSha1());
		Scanner scanny = new Scanner(tFile);
		while (scanny.hasNextLine()) {
			String line = scanny.nextLine();
			System.out.println(line);
			if (line.contains("blob") && !line.contains(fileName)) {
				array.add(line);
			}
			if (line.contains(fileName)) {
				if (getPrevTree() != null) {
					array.remove(0);
					array.add(0, getPrevTree().getSha1());
				}
				getInfo(getTree().getSha1());
				array.remove(line);
				return true;
			} 
			if (line.contains("tree")){
				deleteFile(fileName, pCommit.getPrevTree());
			}
		}
		scanny.close();
		return false;
	}
	public Tree getTree() {
		return tree;
	}
	public void getInfo(String str) {
		Scanner scanny = new Scanner(str);
		while (scanny.hasNextLine()) {
			array.add(scanny.nextLine());
		}
		scanny.close();
	}
	public Tree getPrevTree() {
		if (pCommit != null) {
			return pCommit.getTree();
		} else {
			return null;
		}
	}
	
	public String getSha1() {
		return sha1;
	}
	public void setChildCommit(Commit c) {
		nextPointer = c;
	}
	private void connectParent() {
		if (pCommit != null) {
			pCommit.setChildCommit(this);
		}
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
		fw.write("objects/" + tree.getSha1()); // 1 writing p Tree value 
		fw.write("\n");
		if (pCommit != null) {
			fw.write("objects/" + pCommit.getSha1()); // 2 writing location of previous commit
			fw.write("\n");
		}
		fw.write(author1); //  4 writing author
		fw.write("\n");
		fw.write(date); // 5 writing date
		fw.write("\n");
		fw.write(summary); // 6 writing location of previous commit
		fw.close();
	}
	public String toSha1(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset(); 
		digest.update(input.getBytes("utf8"));
		sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		return input;
	}
}

