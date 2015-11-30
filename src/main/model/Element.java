package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import controller.Controller;

import java.util.Set;

public abstract class Element {

	protected String name;
	protected Folder parent;
	private Map<User, Boolean> users = new HashMap<User, Boolean>();

	public Element(String name, Folder parent, User admin) {
		this.name = name;
		this.users.put(admin, true);
		if (parent != null) {
			this.parent = parent;
			parent.add(this);
		}
	}

	public User admin() {
		for (Entry<User, Boolean> entry : users.entrySet()) {
			if (entry.getValue() == true) {
				return entry.getKey();
			}
		}
		return null;
	}

	public boolean isAdmin(User user) {
		return user == admin();
	}

	public String toString() {
		return this.admin().name() + this.name();
	}

	public String name() {
		return this.name;
	}

	public Set<User> guests() {
		Set<User> guests = new HashSet<User>();
		for (Entry<User, Boolean> entry : users.entrySet()) {
			if (entry.getValue() == false) {
				guests.add(entry.getKey());
			}
		}
		return guests;
	}

	public void addGuest(User user) {
		users.put(user, false);
		Controller controller = Controller.getInstance();
		controller.notify(user, this);
		if (this instanceof Folder) {
			addGuestToChildren(user);
		}
	}

	private void addGuestToChildren(User user) {
		Folder thisFolder = (Folder) this;
		for (Element child : thisFolder.children()) {
			child.addGuest(user);
		}
	}

	public String path() {
		String path = "";
		Folder recursiveParent = parent();
		while (recursiveParent != null) {
			path = recursiveParent.name() + "/" + path;
			recursiveParent = recursiveParent.parent();
		}
		return path;
	}

	public Folder parent() {
		return this.parent;
	}

	public void remove() {
		parent.remove(this);
	}

}
