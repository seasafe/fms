package com.fms.event.watch;

import java.io.File;
import java.util.EventObject;
//@Component
public class FileEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;

	public FileEvent(File file) {
		super(file);
	}

	public File getFile() {
		return (File) getSource();
	}
	
	
}