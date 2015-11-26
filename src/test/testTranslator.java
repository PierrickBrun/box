
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.Session;
import controller.Translator;
import model.Document;
import model.Element;
import model.Folder;
import model.User;

public class testTranslator {

	private Session session;
	private Translator translator;

	@Before
	public void init() {
		session = new Session("Frederic Mitterand");
		translator = new Translator(session);
	}

	@Test
	public void testLs() {
		session.createUser("testLs");
		session.connect("testLs");

		Folder folder1 = session.createFolder("test1", null);
		session.createFolder("test2", folder1);
		Set<Element> test = translator.translate("ls test1");

		Assert.assertEquals(1, test.size());
	}

	@Test
	public void testCd() {
		session.createUser("testCd");
		session.connect("testCd");

		Folder folder1 = session.createFolder("test1", null);
		session.createFolder("test2", folder1);
		translator.translate("cd test1");

		Assert.assertEquals(folder1.name(), session.folder().name());
	}

	@Test
	public void testMkdir() {
		session.createUser("testMkdir");
		session.connect("testMkdir");

		Folder folder1 = session.createFolder("test1", null);
		session.createFolder("test2", folder1);
		translator.translate("mkdir test3 test1/test2");
		Set<Element> elements = translator.translate("ls test1/test2");
		Element newElement = null;
		for (Element element : elements) {
			newElement = element;
		}
		Assert.assertEquals("home/test1/test2/test3", newElement.path() + newElement.name());
	}

	@Test
	public void testRm() {
		session.createUser("testRm");
		session.connect("testRm");

		Folder folder1 = session.createFolder("test1", null);
		session.createFolder("test2", folder1);
		translator.translate("rm test1/test2");

		Assert.assertEquals(0, translator.translate("ls test1/").size());
	}

	@Test
	public void testTouch() {
		session.createUser("testTouch");
		session.connect("testTouch");

		Folder folder1 = session.createFolder("test1", null);
		session.createFolder("test2", folder1);
		translator.translate("touch E:/tech/putty.exe test1/test2");
		Set<Element> elements = translator.translate("ls test1/test2");
		Element newElement = null;
		for (Element element : elements) {
			newElement = element;
		}

		Assert.assertEquals("home/test1/test2/putty.exe",
				newElement.path() + newElement.name() + "." + ((Document) newElement).type());
	}

	@Test
	public void testRmDocument() {
		session.createUser("testRmDoc");
		session.connect("testRmDoc");

		Folder folder1 = session.createFolder("test1", null);
		session.createFolder("test2", folder1);
		translator.translate("touch E:/tech/putty.exe test1");

		translator.translate("rm test1/putty");

		Assert.assertEquals(1, session.ls(folder1).size());
	}

	@Test
	public void testShare() {
		session.createUser("testShare");
		session.connect("testShare");

		Folder folder1 = session.createFolder("test1", null);
		Folder folder2 = session.createFolder("test2", folder1);

		Session session2 = new Session("userTest2");

		translator.translate("share userTest2 test1/test2");

		Assert.assertEquals(1, folder2.guests().size());

		User newGuest = null;
		for (User guest : folder2.guests()) {
			newGuest = guest;
		}

		Assert.assertEquals(session2.user(), newGuest);

	}

	@Test
	public void testListDocs() {
		session.createUser("testListDocs");
		session.connect("testListDocs");

		translator.translate("touch E:/tech/putty.exe test1");
		translator.translate("touch E:/tech/putty.exe test1/test2");
		Set<Element> elements = translator.translate("listdocs");

		System.out.println(elements);

		Assert.assertEquals(2, elements.size());
	}

}
