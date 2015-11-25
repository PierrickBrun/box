package controller;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import model.Document;
import model.Element;
import model.Folder;
import model.User;

public class Translator {

	private Session session;

	public Translator(Session session) {
		this.session = session;
	}

	public Set<Element> translate(String string) {
		String[] argsArray = string.split(" ");
		if (string.startsWith("ls")) {
			return ls(argsArray);
		} else if (string.startsWith("cd")) {
			return cd(argsArray);
		} else if (string.startsWith("mkdir")) {
			return mkdir(argsArray);
		} else if (string.startsWith("rm")) {
			return rm(argsArray);
		} else if (string.startsWith("touch")) {
			return touch(argsArray);
		} else if (string.startsWith("share")) {
			return share(argsArray);
		} else if (string.startsWith("listdocs")) {
			return listDocs(argsArray);
		}
		return null;
	}

	private Set<Element> listDocs(String[] argsArray) {
		Set<Element> elements = session.user().elements();
		Set<Element> docs = new HashSet<Element>();
		for (Element elem : elements) {
			if (elem instanceof Document) {
				docs.add((Document) elem);
			}
		}

		return docs;
	}

	private Set<Element> share(String[] argsArray) {
		Element element = session.folder();

		String userName = argsArray[1];
		if (argsArray.length > 2) {
			element = navigate(argsArray[1]);
		}

		Controller controller = Controller.getInstance();
		User user = controller.getUser(userName);

		element.addGuest(user);

		return encapsulate(element);
	}

	private Set<Element> touch(String[] argsArray) {
		Folder parent = session.folder();

		String localPath = argsArray[1];

		if (argsArray.length > 2) {
			parent = (navigate(argsArray[2]) instanceof Folder) ? (Folder) navigate(argsArray[2]) : session.folder();
		}

		File file = new File(localPath);

		Document newDocument = session.createDocument(file, parent);

		return encapsulate(newDocument);
	}

	private Set<Element> rm(String[] argsArray) {
		String[] folders = argsArray[1].split("/");
		Element element = navigate(folders);
		Folder parent = element.parent();
		session.remove(element);
		return encapsulate(parent);
	}

	private Set<Element> mkdir(String[] argsArray) {
		Folder parent = (Folder) session.folder();
		String name = argsArray[1];

		if (argsArray.length > 2) {
			parent = (navigate(argsArray[2]) instanceof Folder) ? (Folder) navigate(argsArray[2]) : session.folder();
		}
		Folder newFolder = session.createFolder(name, parent);

		return encapsulate(newFolder);

	}

	private Set<Element> encapsulate(Element element) {
		Set<Element> resultSet = new HashSet<Element>();
		resultSet.add(element);
		return resultSet;
	}

	private Set<Element> cd(String[] argsArray) {
		Folder folder = session.folder();
		if (argsArray.length > 1) {
			folder = (navigate(argsArray[1]) instanceof Folder) ? (Folder) navigate(argsArray[1]) : session.folder();
		}
		session.setFolder(folder);
		return encapsulate(folder);
	}

	private Element navigate(String path) {
		String[] elements = path.split("/");
		Element element = navigate(elements);
		return element;
	}

	private Set<Element> ls(String[] argsArray) {
		Folder folder = session.folder();
		if (argsArray.length > 1) {
			folder = (navigate(argsArray[1]) instanceof Folder) ? (Folder) navigate(argsArray[1]) : session.folder();
		}
		return session.ls(folder);
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
