package com.reload.exception;

import java.io.File;

public class FileIsNotDirectoryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileIsNotDirectoryException(File file){
		super("file:"+file.getAbsolutePath()+"is not directory!");
	}
	
}
