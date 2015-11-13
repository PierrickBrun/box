package controller;

import java.util.Set;

import model.Element;
import model.Folder;

public class Translator {

	private Session session;

	public Translator(Session session) {
		this.session = session;
	}

	public String translate(String string) {
		int separator = string.indexOf(" ");
		String args = string.substring(separator+1, string.length());
		if (string.startsWith("ls ")) {
			String[] folders = args.split("/");
			Folder folder = navigate(folders);
			return drawLs(folder);
		} else if (string.startsWith("cd ")) {
			String[] folders = args.split("/");
			Folder folder = navigate(folders);
			session.setFolder(folder);
			return drawLs(folder);
		} else if (string.startsWith("mkdir ")) {
			separator = args.indexOf(" ");
			String name = args.substring(0, separator);

			String path = args.substring(separator + 1);
			String[] folders = path.split("/");
			Folder parent = navigate(folders);
			Folder newFolder = session.createFolder(name, parent);

			return newFolder.path() + newFolder.name();
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

	private Folder navigate(String[] folders) {
		Folder folder = session.folder();
		for (String folderName : folders) {
			folder = (folder != null && Folder.contains(folder, folderName)) ? (Folder) folder.getChild(folderName)
					: null;
		}
		return folder;
	}

}
