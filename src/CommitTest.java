import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import git.Blob;
import git.Index;

class CommitTest {

	@Test
	void test() throws Exception {
		Index i = new Index();
		i.initialize();
		Blob b = new Blob("main.txt");
		i.addBlob("main.txt");
		Commit c = new Commit("Wyatt", "Changes", null);
		c.writeFile();
		
		Blob b2 = new Blob("funnyrandomstuff.txt");
		Index i2 = new Index();
		i2.addBlob("funnyrandomstuff.txt");
		Commit c2 = new Commit("Wyatt", "Changes Two", c);
		c2.writeFile();
		
		Index i3 = new Index();
		Blob b3 = new Blob("naalah.txt");
		i3.addBlob("naalah.txt");
		Commit c3 = new Commit("Wyatt", "Changes Three", c2);
		c3.writeFile();
		
		Blob b4 = new Blob("animal.txt");
		Index i4 = new Index();
		i4.addBlob("animal.txt");
		Commit c4 = new Commit("Wyatt", "Changes Four", c3);
		c4.writeFile();
	}

}
