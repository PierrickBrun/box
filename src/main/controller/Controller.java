package controller;

import java.util.HashMap;
import java.util.Map;

import model.User;

public class Controller {

	private static final Controller INSTANCE = new Controller();

	public static Controller getInstance() {
		return INSTANCE;
	}

	private Controller() {
	}

	private Map<String, User> users = new HashMap<String, User>();

	public User createUser(String name) {
		User user = new User(name);
		users.put(name, user);
		return user;
	}

	public User getUser(String name) {
		return users.get(name);
	}

}
