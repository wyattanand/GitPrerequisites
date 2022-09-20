import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import git.Tree;

class JUnitTree {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	ArrayList<String> list = new ArrayList<String>();
	list.add("blob: a94a8fe5ccb19ba61c4c0873d391e987982fbbd3");
	list.add("tree : ab4d8d2a5f480a137067da17100271cd176607a1");
	Tree tree = new Tree(list);
	}
	
	@Test
	void writeFile() {
		File file = new File ("Objects/ab4d8d2a5f480a137067da17100271cd176607a1");
		assertTrue(file.exists()); 
	}
	
	@Test
	void fileContents() throws IOException {
		File file = new File ("Objects/ab4d8d2a5f480a137067da17100271cd176607a1");
		String content = FileUtil.getContent("Objects/ab4d8d2a5f480a137067da17100271cd176607a1");
		String sha = FileUtil.sha1(content);
		assertTrue(sha.equals("ab4d8d2a5f480a137067da17100271cd176607a1"));
	}
	
	@Test
	void testIfValidSha() {
		
	}

}
