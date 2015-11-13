import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.Session;
import model.Document;
import model.Element;
import model.Folder;
import model.User;

public class testSession {

	private Session session;
	private User user;

	@Before
	public void init() {
		session = new Session("test");
		user = session.createUser("test");
	}

	@Test
	public void testCreateUser() {
		Assert.assertEquals("test", user.name());
		Assert.assertEquals("test", session.user().name());
	}
	
	@Test
	public void testConnect() {
		user = session.connect("test");
		Assert.assertEquals("test", user.name());
		Assert.assertEquals("test", session.user().name());
	}
	
	@Test
	public void testCreateFolder(){
		Folder testFolder = session.createFolder("test",null);
		
		Assert.assertEquals("test", testFolder.name());
	}
	
	@Test
	public void testCreateDocument(){
		File testFile = new File("E:/tech/putty.exe");
		Document doc = session.createDocument(testFile,null);
		
		Assert.assertEquals("putty", doc.name());
		Assert.assertEquals("exe", doc.type());
		Assert.assertEquals(testFile, doc.file());
	}
	
	@Test
	public void testLs() {
		Folder folder = session.createFolder("test1",null);
		
		Set<Element> list = new HashSet<Element>();
		list.add(folder);
		
		Assert.assertEquals(list, session.ls());
	}
	
	@Test
	public void testCd() {
		Folder folder = session.createFolder("test1",null);
		
		Assert.assertEquals("home", session.folder().name());
		
		session.cd(folder);
		
		Assert.assertEquals(folder, session.folder());
	}

}
