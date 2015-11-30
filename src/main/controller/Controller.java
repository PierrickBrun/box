package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.Element;
import model.User;

public class Controller extends Observable {

	private static final Controller INSTANCE = new Controller();

	public static Controller getInstance() {
		return INSTANCE;
	}

	private Controller() {
	}

	private Map<User, Session> users = new HashMap<User, Session>();

	public User createUser(String name) {
		User user = new User(name);
		users.put(user, null);
		return user;
	}

	public User getUser(String name) {
		for (User user : users.keySet()) {
			if (user != null && user.name().equals(name)) {
				return user;
			}
		}
		return null;
	}

	public void add(Session session) {
		users.put(session.user(), session);
	}

	public void notify(User user, Element element) {
		if (user != null && users.get(user) != null) {
			addObserver(users.get(user));
		}
		notifyObservers(element);
		deleteObservers();
	}

	public void connect(Session session) {
		users.put(session.user(), session);
	}

}
