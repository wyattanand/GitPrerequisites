import java.io.BufferedWriter;
import java.io.File;
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
	private String pointer;
	private Commit nextPointer;
	private Tree tree;
	private Commit pCommit;
	private ArrayList<String> array = new ArrayList<String>();
	public Commit(String author, String sumOfChanges, Commit parent) throws Exception {
		File indexFile = new File("./index.txt");
		Scanner scanny = new Scanner(indexFile);
		String fileName;
		String sha;
		String deletedName;
		pCommit = parent;
		connectParent();
		ArrayList<String> list = new ArrayList<String>();
		while (scanny.hasNextLine()) {
			fileName = scanny.next();
			System.out.println(fileName);
			if (fileName.charAt(0) == '*') {
				deletedName = scanny.next();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).contains(deletedName)) {
						list.remove(i);
					}
				}
			} else {
				String nxt1 = scanny.next();
				System.out.println(nxt1);
				String nxt2 = scanny.next();
				System.out.println(nxt2);
				sha = (nxt1 + nxt2).substring(1);
				list.add("blob : " + sha + " " + fileName);
			}
		}
		if (pCommit != null) {
			list.add("tree : " + pCommit.getTree().getSha1());
		}
		tree = new Tree(list);
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
	public boolean deleteFile(String fileName, Tree t) {
		array.add(0, t.getSha1());
		Scanner scanny = new Scanner("/objects/" + t.getSha1());
		while (scanny.hasNextLine()) {
			String line = scanny.nextLine();
			if (scanny.nextLine().contains("blob") && !scanny.nextLine().contains(fileName)) {
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
				deleteFile(fileName, getPrevTree());
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
	
	public void delete(String fileName) throws NoSuchAlgorithmException, IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("./index"));
		writer.append("*deleted*" + fileName);
		writer.close();
	}
	
	public void writeFile () throws IOException {
		FileWriter fw = new FileWriter(new File("objects", sha1));
		fw.write(tree.getSha1()); // 1 writing p Tree value 
		fw.write("\n");
		if (pCommit != null) {
			fw.write(pCommit.getSha1()); // 2 writing location of previous commit
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

