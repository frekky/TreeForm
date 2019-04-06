package userInterface;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilterXML implements FilenameFilter {

	public FileFilterXML() {
		super();
	}

	public String getDescription() {
		return "TreeForm XML File.";
	}
	
	public boolean accept(File dir, String name) {
		return (name.indexOf(".xml") != -1);
	}
}

