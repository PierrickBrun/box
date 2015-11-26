import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Folder;
import model.User;

public class testUser {

	private User user;

	@Before
	public void init() {
		user = new User("Nadine Morano");
	}

	@Test
	public void testHome() {
		Assert.assertEquals("home", user.home().name());
		Assert.assertEquals(null, user.home().parent());
	}

	@Test
	public void testName() {
		Assert.assertEquals("Nadine Morano", user.name());
	}

	@Test
	public void testAddElement() {
		user = new User("Robert Ménard");
		user.add(new Folder("folder", user.home(), user));
		Assert.assertEquals(2, user.elements().size());
	}

	@Test
	public void testRemoveElement() {
		user = new User("Eric Zemmour");
		Folder folder = new Folder("folder", user.home(), user);
		user.add(folder);
		user.remove(folder);
		Assert.assertEquals(1, user.elements().size());
	}

}
