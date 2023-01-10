package it.crud.demo.exceptions;

public class StudentNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3394907546738259561L;

	public StudentNotFoundException(String message) {
		super(message);
	}

}
