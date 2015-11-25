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
		int separator = string.indexOf(" ");
		String args = string.substring(separator + 1, string.length());
		if (string.startsWith("ls ")) {
			return ls(args);
		} else if (string.startsWith("cd ")) {
			return cd(args);
		} else if (string.startsWith("mkdir ")) {
			return mkdir(args);
		} else if (string.startsWith("rm ")) {
			return rm(args);
		} else if (string.startsWith("touch ")) {
			return touch(args);
		} else if (string.startsWith("share ")) {
			return share(args);
		}
		return null;
	}

	private Set<Element> share(String args) {
		Element element = session.folder();
		String[] argArray = args.split(" ");

		String userName = argArray[0];
		if(argArray.length > 1){			
			String elementPath = argArray[1];
			String[] elements = elementPath.split("/");
			element = navigate(elements);
		}

		Controller controller = Controller.getInstance();
		User user = controller.getUser(userName);

		element.addGuest(user);

		return encapsulate(element);
	}

	private Set<Element> touch(String args) {
		Folder parent = session.folder();
		String[] argArray = args.split(" ");

		String localPath = argArray[0];
		
		if(argArray.length > 1){			
			String boxPath = argArray[1];	
			String[] boxFolders = boxPath.split("/");
			parent = (Folder) navigate(boxFolders);
		}

		File file = new File(localPath);

		Document newDocument = session.createDocument(file, parent);

		return encapsulate(newDocument);
	}

	private Set<Element> rm(String args) {
		String[] folders = args.split("/");
		Element element = navigate(folders);
		Folder parent = element.parent();
		session.remove(element);
		return encapsulate(parent);
	}

	private Set<Element> mkdir(String args) {
		Folder parent = (Folder) session.folder();
		String[] argArray = args.split(" ");
		String name = argArray[0];

		if (argArray.length > 1) {
			String[] folders = argArray[1].split("/");
			parent = (Folder) navigate(folders);
		}
		Folder newFolder = session.createFolder(name, parent);

		return encapsulate(newFolder);

	}

	private Set<Element> encapsulate(Element element) {
		Set<Element> resultSet = new HashSet<Element>();
		resultSet.add(element);
		return resultSet;
	}

	private Set<Element> cd(String args) {
		String[] folders = args.split("/");
		Folder folder = (Folder) navigate(folders);
		session.setFolder(folder);
		return session.ls(folder);
	}

	private Set<Element> ls(String args) {
		String[] folders = args.split("/");
		Folder folder = (Folder) navigate(folders);
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
