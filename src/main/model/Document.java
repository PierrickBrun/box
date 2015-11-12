package model;

import java.io.File;

public class Document extends Element {

	private File file;
	private String type;

	private static String getFileName(File file) {
		String extendedName = file.getName();
		int indexEndName = extendedName.indexOf(".");
		return extendedName.substring(0, indexEndName);
	}

	private static String getFileType(File file) {
		String extendedName = file.getName();
		int indexStartType = extendedName.indexOf(".") + 1;
		int indexEndName = extendedName.length();
		return extendedName.substring(indexStartType, indexEndName);
	}

	public Document(File file, Folder parent, User admin) {
		super(getFileName(file), parent, admin);
		this.file = file;
		this.type = getFileType(file);
	}

	public File file() {
		return this.file;
	}

	public String type() {
		return this.type;
	}

}
