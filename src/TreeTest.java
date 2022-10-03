
import java.io.IOException;

import git.Tree;
import git.Blob;
import git.Index;
import java.util.*;
public class TreeTest {
	public static void main(String[] args) throws Exception {
	/*	ArrayList<String> list = new ArrayList<String>();
		list.add("blob: a94a8fe5ccb19ba61c4c0873d391e987982fbbd3");
		list.add("tree : ab4d8d2a5f480a137067da17100271cd176607a1");
		Tree tree = new Tree(list);
		*/
		Index i = new Index();
		i.initialize();
		Blob b = new Blob("main.txt");
		i.addBlob("main.txt");
		Commit c = new Commit("Wyatt", "Changes", null);
		c.writeFile();
		
		Blob b2 = new Blob("funnyrandomstuff.txt");
		i.addBlob("main.txt");
		Commit c2 = new Commit("Wyatt", "Changes Two", c);
		c2.writeFile();
		
		Blob b3 = new Blob("naalah.txt");
		i.addBlob("main.txt");
		Commit c3 = new Commit("Wyatt", "Changes Three", c2);
		c3.writeFile();
		
		Blob b4 = new Blob("animal.txt");
		i.addBlob("main.txt");
		Commit c4 = new Commit("Wyatt", "Changes Four", c3);
		c4.writeFile();
	}
}
