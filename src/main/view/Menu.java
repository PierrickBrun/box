package view;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import controller.Session;
import controller.Translator;
import model.Element;

public class Menu {

	private String input = "";
	private Scanner scanner;
	private Session session = null;
	private Translator translator;

	public Menu(InputStream inputStream) {
		scanner = new Scanner(inputStream);
	}

	public Menu() {
	}

	public void main() {
		while (!input.equals("quit")) {
			if (input.equals("")) {
				init();
			} else {
				Set<Element> elements = translator.translate(input);
				System.out.println("\n" + input + " : " + draw(elements));
			}
			System.out.println("________________________________________________________");
			System.out.println(
					"ls path | cd path | mkdir name [path] | rmdir path | touch local_path [box_path] | share user [path] | listdocs");
			System.out.print(session.folder().path() + session.folder().name() + ":");
			try {
				input = scanner.nextLine();
			} catch (NoSuchElementException e) {
				System.out.println("\n\nfin de la démo");
				return;
			}
		}
	}

	private String draw(Set<Element> elements) {
		String result = "";
		if (elements != null) {
			for (Element elem : elements) {
				result += elem.name() + "\t";
			}
		}
		return result;
	}

	private void init() {
		System.out.println("Hi, who are you ?");
		input = scanner.nextLine();
		session = new Session(input, this);
		translator = new Translator(session);
		System.out.println("Write one of the following to execute tasks :");
	}

	public void println(String string) {
		System.out.println(string);
	}

}
