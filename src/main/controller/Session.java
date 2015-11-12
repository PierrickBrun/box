package controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
	}

	private Folder folder = null;
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
		Folder newFolder = new Folder(name, folder, user);
		user.addElement(newFolder);
		return newFolder;
	}

	public Document createDocument(File name, Folder folder) {
		Document newDocument = new Document(name, folder, user);
		user.addElement(newDocument);
		return newDocument;
	}

	public Map<String,Element> ls() {
		Map<String,Element> list = new HashMap<String,Element>();
		for(Element element : user.elements()){
			Element elementFromFolder = (folder == element.parent()) ? element : null;
			list.put(elementFromFolder.name(),elementFromFolder);
		}
		return list;
	}

	public Folder folder() {
		return folder;
	}

	public void cd(Folder folder) {
		this.folder = folder;
	}


}
