package controller;

import java.util.Map;

import model.Element;
import model.Folder;

public class Translator {

	private Session session;
	
	public Translator(String name){
		session = new Session(name);
	}

	public String translate(String string) {
		if(string.startsWith("ls ")){
			String path = string.substring(3);
			String[] folders = path.split("/");
			
		}
		return null;
	}
	
	private Folder navigate(String[] folders){
		Element element = null;
		for(String folderName : folders){
			Map<String, Element> mapList = session.ls();
			element = session.ls().containsKey(folderName) ? session.ls().get(folderName) : null;
		}
		return (Folder) element;
	}

}
