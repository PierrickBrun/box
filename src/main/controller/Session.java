package controller;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import model.Document;
import model.Element;
import model.Folder;
import model.User;
import view.Menu;

public class Session implements Observer {

	private Controller controller;
	private Menu menu;

	public Session(String name, Menu menu) {
		this.controller = Controller.getInstance();
		controller.add(this);
		this.menu = menu;
		if (connect(name) == null) {
			user = createUser(name);
			folder = user.home();
		}
		this.menu = menu;
	}

	public Session(String name) {
		this.controller = Controller.getInstance();
		controller.add(this);
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
		if (controller.getUser(name) != null) {
			user = controller.getUser(name);
			folder = user.home();
			controller.connect(this);
		}
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
		return folder.children();
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public void remove(Element element) {
		user.remove(element);
	}

	@Override
	public void update(Observable observable, Object object) {
		if (object instanceof Element) {
			Element element = (Element) object;
			menu.println("You've been granted guest on " + element.name() + " by " + element.admin().name());
		}
	}

}
