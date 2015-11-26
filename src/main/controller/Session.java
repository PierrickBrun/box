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
		if (connect(name) == null) {
			user = createUser(name);
			folder = user.home();
		}
	}

	private Folder folder;
	private User user;

	public User createUser(String name) {
		User user = controller.createUser(name);
		return user;
	}

	public User user() {
		return user;
	}

	public User connect(String name) {
		user = controller.getUser(name) != null ? controller.getUser(name) : user;
		folder = (user != null) ? user.home() : folder;
		return user;
	}

	public Folder createFolder(String name, Folder folder) {
		folder = (folder == null) ? user.home() : folder;
		Folder newFolder = new Folder(name, folder, user);
		user.add(newFolder);
		return newFolder;
	}

	public Document createDocument(File file, Folder folder) {
		Document newDocument = new Document(file, folder, user);
		user.add(newDocument);
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

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public void remove(Element element) {
		user.remove(element);
	}

}
