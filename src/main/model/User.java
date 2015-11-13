package model;

import java.util.HashSet;
import java.util.Set;

public class User {

	private Set<Element> elements = new HashSet<Element>();
	private String name;

	public User(String name) {
		this.name = name;
		this.add(new Folder("home", null, this));
	}

	public Folder home() {
		for (Element element : elements) {
			if ("home".equals(element.name())) {
				return (Folder) element;
			}
		}
		return null;
	}

	public String name() {
		return name;
	}

	public Set<Element> elements() {
		return elements;
	}

	public void add(Element element) {
		elements.add(element);
	}
	
	public void remove(Element element){
		element.remove();
		elements.remove(element);
	}

}
