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

public class testElement {

	private Session session;
	private Element element;

	@Before
	public void init() {
		session = new Session("Christine Boutin");
		element = session.createFolder("test", null);
	}

	@Test
	public void testIsAdmin() {
		Assert.assertTrue(element.isAdmin(session.user()));

		User user2 = session.createUser("Patrick Balkany");
		Assert.assertFalse(element.isAdmin(user2));
	}

	@Test
	public void testName() {
		Assert.assertEquals("test", element.name());
	}

	@Test
	public void testAddGuest() {
		User userTest = session.createUser("Mohamed Sifaoui");
		element.addGuest(userTest);

		Set<User> assertSet = new HashSet<User>();
		assertSet.add(userTest);
		Assert.assertEquals(assertSet, element.guests());
	}

	@Test
	public void testPath() {
		Folder folder2 = session.createFolder("test2", (Folder) element);

		File testFile = new File("E:/tech/putty.exe");
		Document doc = session.createDocument(testFile, folder2);

		Assert.assertEquals("home/", element.path());
		Assert.assertEquals("home/test/", folder2.path());
		Assert.assertEquals("home/test/test2/", doc.path());
	}

	@Test
	public void testParent() {
		Folder folder2 = new Folder("test564623", (Folder) element, session.user());
		Assert.assertEquals(element, folder2.parent());
	}

	@Test
	public void testFolderChildren() {
		new Folder("test564623", (Folder) element, session.user());
		Assert.assertEquals(1, ((Folder) element).children().size());
	}

	@Test
	public void testFolderChild() {
		Folder folder2 = new Folder("test564623", (Folder) element, session.user());
		Assert.assertEquals(folder2, ((Folder) element).getChild("test564623"));
	}

	@Test
	public void testRemove() {
		Folder folder2 = new Folder("test564623", (Folder) element, session.user());
		folder2.remove();
		Assert.assertEquals(0, ((Folder) element).children().size());
	}

	@Test
	public void testDocumentType() {
		File testFile = new File("E:/tech/putty.exe");
		Document doc = session.createDocument(testFile, (Folder) element);
		Assert.assertEquals("exe", doc.type());
	}

}
