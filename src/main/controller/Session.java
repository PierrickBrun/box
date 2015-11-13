package controller;

import java.io.File;
import java.util.Set;

import model.Document;
import model.Element;
import model.Folder;
import model.User;

public class Session {

	private Controller controller;

	public Session(String name) {
		this.controller = Controller.getInstance();
		if(connect(name) == null){
			createUser(name);
		}
		folder = user.home();
	}

	private Folder folder;
	private User user;

	public User createUser(String name) {
		user = controller.createUser(name);
		return user;
	}

	public User user() {
		return user;
	}

	public User connect(String name) {
		user = controller.getUser(name);
		return user;
	}

	public Folder createFolder(String name, Folder folder) {
		folder = (folder == null) ? user.home() : folder;
		Folder newFolder = new Folder(name, folder, user);
		user.addElement(newFolder);
		return newFolder;
	}

	public Document createDocument(File name, Folder folder) {
		Document newDocument = new Document(name, folder, user);
		user.addElement(newDocument);
		return newDocument;
	}

	public Set<Element> ls() {
		return ls(folder);
	}

	public Folder folder() {
		return folder;
	}

	public void cd(Folder folder) {
		this.folder = folder;
	}

	public Set<Element> ls(Folder folder) {
		return folder.getChildren();
	}


}
