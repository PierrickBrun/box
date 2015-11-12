package controller;

import java.util.HashMap;
import java.util.Map;

import model.User;

public class Controller {

	private static Controller INSTANCE = new Controller();

	static Controller getInstance() {
		return INSTANCE;
	}

	private Map<String,User> users = new HashMap<String,User>();
	
	User createUser(String name){
		User user = new User(name);
		users.put(name, user);
		return user;
	}

	User getUser(String name) {
		return users.get(name);
	}

}
