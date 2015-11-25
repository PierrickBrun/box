
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
	private Folder folder1;

	@Before
	public void init() {
		session = new Session("userTest");
		translator = new Translator(session);
		folder1 = session.createFolder("test1", null);
		Folder folder2 = session.createFolder("test2", folder1);
	}

	@Test
	public void testLs() {
		Set<Element> test = translator.translate("ls test1");

		Assert.assertEquals(1, test.size());
	}

	@Test
	public void testCd() {
		translator.translate("cd test1");

		Assert.assertEquals(folder1.name(), session.folder().name());
	}

	@Test
	public void testMkdir() {
		Set<Element> elements = translator.translate("mkdir test3 test1/test2");
		Element newElement = null;
		for (Element element : elements) {
			newElement = element;
		}
		Assert.assertEquals("home/test1/test2/test3", newElement.path() + newElement.name());
	}

	@Test
	public void testRm() {
		Set<Element> elements = translator.translate("rm test1/test2");

		Element newElement = null;
		for (Element element : elements) {
			newElement = element;
		}
		Assert.assertEquals("home/test1", newElement.path() + newElement.name());
		Assert.assertEquals(0, translator.translate("ls test1/").size());
	}

	@Test
	public void testTouch() {
		Set<Element> elements = translator.translate("touch E:/tech/putty.exe test1/test2");

		Element newElement = null;
		for (Element element : elements) {
			newElement = element;
		}
		Assert.assertEquals("home/test1/test2/putty.exe",
				newElement.path() + newElement.name() + "." + ((Document) newElement).type());
	}

	@Test
	public void testShare() {
		Session session2 = new Session("userTest2");

		Set<Element> elements = translator.translate("share userTest2 test1/test2");
		Element newElement = null;
		for (Element element : elements) {
			newElement = element;
		}

		Assert.assertEquals(1, newElement.guests().size());

		User newGuest = null;
		for (User guest : newElement.guests()) {
			newGuest = guest;
		}

		Assert.assertEquals(session2.user(), newGuest);

	}

}
