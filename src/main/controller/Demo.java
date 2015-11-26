package controller;

import java.io.ByteArrayInputStream;

import view.Menu;

public class Demo {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Session session = new Session("Christophe");
		
		System.out.println("");
		
		String demo = "Pierrick";
		demo += "\nls";
		demo += "\nmkdir dossier1";
		demo += "\nmkdir dossier2";
		demo += "\nls";
		demo += "\nmkdir dossier11 dossier1";
		demo += "\nmkdir dossier111 dossier1/dossier11";
		demo += "\ncd dossier1/dossier11";
		demo += "\nls";
		demo += "\ntouch fichier1.docx";
		demo += "\ntouch fichier2.pages";
		demo += "\nshare Christophe fichier2.pages";
		demo += "\ncd ../..";
		demo += "\nlistdocs";
		
		ByteArrayInputStream in = new ByteArrayInputStream(demo.getBytes());
		System.setIn(in);

		Menu menu = new Menu(System.in);
		menu.main();
	}

}
