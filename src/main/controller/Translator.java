package controller;

import java.util.Set;

import model.Document;
import model.Element;
import model.Folder;

public class Translator {

	private Session session;

	public Translator(Session session) {
		this.session = session;
	}

	public String translate(String string) {
		int separator = string.indexOf(" ");
		String args = string.substring(separator + 1, string.length());
		if (string.startsWith("ls ")) {
			String[] folders = args.split("/");
			Folder folder = (Folder) navigate(folders);
			return drawLs(folder);
		} else if (string.startsWith("cd ")) {
			String[] folders = args.split("/");
			Folder folder = (Folder) navigate(folders);
			session.setFolder(folder);
			return drawLs(folder);
		} else if (string.startsWith("mkdir ")) {
			separator = args.indexOf(" ");
			String name = args.substring(0, separator);

			String path = args.substring(separator + 1);
			String[] folders = path.split("/");
			Folder parent = (Folder) navigate(folders);
			Folder newFolder = session.createFolder(name, parent);

			return newFolder.path() + newFolder.name();
		} else if (string.startsWith("rm ")) {
			String[] folders = args.split("/");
			Element element = navigate(folders);
			Folder parent = element.parent();
			session.remove(element);
			return parent.path() + parent.name();
		}
		return null;
	}

	private String drawLs(Folder folder) {
		String result = "";
		Set<Element> elements = session.ls(folder);
		for (Element elem : elements) {
			result += elem.name() + "\t";
		}
		return result;
	}

	private Element navigate(String[] elements) {
		Element element = session.folder();
		for (String elementName : elements) {
			if (element instanceof Folder) {
				Folder folder = (Folder) element;
				element = folder.contains(elementName) ? folder.getChild(elementName) : folder;
				if (element.equals(folder)) {
					return element;
				}
			} else if (element instanceof Document) {
				return element;
			}
		}
		return element;
	}

}
