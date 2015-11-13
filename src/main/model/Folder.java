package model;

import java.util.HashSet;
import java.util.Set;

public class Folder extends Element {
	
	public static boolean contains(Folder folder,String elementName){
		if (folder.getChild(elementName) != null){
			return true;
		}
		return false;
	}
	
	private Set<Element> children = new HashSet<Element>();

	public Folder(String name, Folder folder, User admin) {
		super(name, folder, admin);
	}

	public Set<Element> getChildren() {
		return children;
	}

	public void add(Element element) {
		children.add(element);
	}

	public void remove(Element element) {
		children.remove(element);
	}
	
	public Element getChild(String name){
		for(Element element : getChildren()){
			if(name.equals(element.name())){
				return element;
			}
		}
		return null;
	}

}
