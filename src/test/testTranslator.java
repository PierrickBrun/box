import org.junit.Before;
import org.junit.Test;

import controller.Session;
import controller.Translator;
import junit.framework.Assert;
import model.Folder;

public class testTranslator {
	
	private Session session;
	private Translator translator;
	private Folder folder1;
	private Folder folder2;

	@Before
	public void init(){
		translator = new Translator("test");
		session = new Session("test");
		folder1 = session.createFolder("test1", null);
		folder2 = session.createFolder("test2", null);
	}
	
	@Test
	public void testLs(){
		String test = translator.translate("ls /test1/");
		
		Assert.assertEquals("test2\t", test);
	}

}
