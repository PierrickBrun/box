import org.junit.Assert;
import org.junit.Test;

import controller.Controller;
import model.User;

public class testController {

	@Test
	public void testControllerCreateUser() {
		Controller controller = Controller.getInstance();
		User user = controller.createUser("Dick");

		Assert.assertEquals(user, controller.getUser("Dick"));
	}

}
