import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.Session;
import model.Document;
import model.Folder;
import model.User;

public class testFolder {

	private Session session;
	private Folder folder;

	@Before
	public void init() {
		session = new Session("test");
		folder = session.createFolder("test", null);
	}

	@Test
	public void testAddGuest() {
		User userTest = session.createUser("test");
		folder.addGuest(userTest);

		Set<User> assertSet = new HashSet<User>();
		assertSet.add(userTest);
		Assert.assertEquals(assertSet, folder.guests());
	}
	
	@Test
	public void testGetPath(){
		Folder folder2 = session.createFolder("test2",folder);
		
		File testFile = new File("E:/tech/putty.exe");
		Document doc = session.createDocument(testFile,folder2);
		
		Assert.assertEquals("", folder.path());
		Assert.assertEquals("test/", folder2.path());
		Assert.assertEquals("test/test2/", doc.path());
	}

}
