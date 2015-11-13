package controller;

import java.util.Set;

import model.Element;
import model.Folder;

public class Translator {

	private Session session;

	public Translator(String name) {
		session = new Session(name);
	}

	public String translate(String string) {
		if (string.startsWith("ls ")) {
			String path = string.substring(3);
			String[] folders = path.split("/");
			Folder folder = navigate(folders);
			return drawLs(folder);
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
			folder = (folder != null && Folder.contains(folder, folderName)) ? (Folder) folder.getChild(folderName) : null;
		}
		return folder;
	}

}
