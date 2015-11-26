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

	@Before
	public void init() {
		// pas de création de session ici
		// le controller étant un singleton il y a des conflits dans les tests
	}

	@Test
	public void testConnect() {
		Session session = new Session("testCreateUser");
		session.connect("testCreateUser");
		Assert.assertEquals("testCreateUser", session.user().name());
	}

	@Test
	public void testCreateUser() {
		Session session = new Session("testConnect");
		User userTest2 = session.createUser("test2");
		Assert.assertEquals("test2", userTest2.name());
	}

	@Test
	public void testCreateFolder() {
		Session session = new Session("testCreateFolder");
		Folder testFolder = session.createFolder("test", null);

		Assert.assertEquals("test", testFolder.name());
	}

	@Test
	public void testCreateDocument() {
		Session session = new Session("testCreateDocument");
		File testFile = new File("E:/tech/putty.exe");
		Document doc = session.createDocument(testFile, null);

		Assert.assertEquals("putty", doc.name());
		Assert.assertEquals("exe", doc.type());
		Assert.assertEquals(testFile, doc.file());
	}

	@Test
	public void testLs() {
		Session session = new Session("testLs");
		Folder folder = session.createFolder("test1", null);

		Set<Element> list = new HashSet<Element>();
		list.add(folder);

		Assert.assertEquals(list, session.ls());
	}

	@Test
	public void testCd() {
		Session session = new Session("testCd");
		Folder folder = session.createFolder("test1", null);

		Assert.assertEquals("home", session.folder().name());

		session.cd(folder);

		Assert.assertEquals(folder, session.folder());
	}

	@Test
	public void testFolder() {
		Session session = new Session("testFolder");
		Assert.assertEquals("home", session.folder().name());
	}

}
