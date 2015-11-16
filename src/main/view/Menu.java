package view;

import java.io.InputStream;
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

	public void main() {
		while (!input.equals("quit")) {
			if (input.equals("")) {
				init();
			} else {
				Set<Element> elements = translator.translate(input);
				System.out.println(draw(elements));
			}
			System.out.println("ls [path] | cd [path] | mkdir | rmdir | touch");
			input = scanner.nextLine();
		}
	}

	private String draw(Set<Element> elements) {
		String result = "";
		for (Element elem : elements) {
			result += elem.name() + "\t";
		}
		return result;
	}

	private void init() {
		System.out.println("Hi, who are you ?");
		input = scanner.nextLine();
		session = new Session(input);
		translator = new Translator(session);
		System.out.println("Write one of the following to execute tasks :");
	}

}
