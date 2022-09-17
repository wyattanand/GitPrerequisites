package git;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndexTest {
	
	

	@Test
	void indexTest() throws IOException {
		Index ind = new Index();
		File index = new File("index");
		assertTrue(index.exists());
	}
	
	void objectsTest() throws IOException {
		Index ind = new Index();
		File objectsDir = new File("Objects");
		assertTrue(objectsDir.exists());
	}
	  void addTest() throws IOException {
		Index ind = new Index();
		Blob blobbie = new Blob("naalah.txt");
		File file = new File("naalah.txt");
		ind.addBlob(file.getName());
		String indexLine = Blob.getContent("index");
		assertTrue(indexLine.equals(file.getName() + " : " + blobbie.getSha()));
	}

}
