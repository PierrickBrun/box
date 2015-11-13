package model;

import java.util.HashSet;
import java.util.Set;

public abstract class Element {

	protected String name;
	protected Folder parent;
	private Set<User> guests = new HashSet<User>();
	private User admin;

	public Element(String name, Folder parent, User admin) {
		this.name = name;
		this.parent = parent;
		this.admin = admin;
		if (parent != null) {
			parent.add(this);
		}
	}

	public boolean isAdmin(User user) {
		return user == admin;
	}

	public String name() {
		return this.name;
	}

	public String toString() {
		return this.name;
	}

	public Set<User> guests() {
		return guests;
	}

	public void addGuest(User user) {
		this.guests.add(user);
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

}
