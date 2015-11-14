
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.Session;
import controller.Translator;
import model.Folder;

public class testTranslator {

	private Session session;
	private Translator translator;
	private Folder folder1;

	@Before
	public void init() {
		session = new Session("test");
		translator = new Translator(session);
		folder1 = session.createFolder("test1", null);
		session.createFolder("test2", folder1);
	}

	@Test
	public void testLs() {
		String test = translator.translate("ls test1");

		Assert.assertEquals("test2\t", test);
	}

	@Test
	public void testCd() {
		translator.translate("cd test1");

		Assert.assertEquals(folder1.name(), session.folder().name());
	}

	@Test
	public void testMkdir() {
		String folderPath = translator.translate("mkdir test3 test1/test2");

		Assert.assertEquals("home/test1/test2/test3", folderPath);
	}

	@Test
	public void testRm() {
		String folder = translator.translate("rm test1/test2");

		Assert.assertEquals("home/test1", folder);
		Assert.assertEquals("", translator.translate("ls test1/"));
	}
	
	@Test
	public void testTouch(){
		String file = translator.translate("touch test1/test2 E:/tech/putty.exe");
		
		Assert.assertEquals("home/test1/test2/putty.exe", file);
	}

}
