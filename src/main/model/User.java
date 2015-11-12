package model;

import java.util.HashSet;
import java.util.Set;

public class User {

	private Set<Element> elements = new HashSet<Element>();
	private String name;

	public User(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public Set<Element> elements() {
		return elements;
	}

	public void addElement(Element element) {
		elements.add(element);
	}

}
