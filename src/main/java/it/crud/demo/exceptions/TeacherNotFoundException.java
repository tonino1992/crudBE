package it.crud.demo.exceptions;

public class TeacherNotFoundException extends RuntimeException{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7981143696691585882L;

	public TeacherNotFoundException(String message) {
		super(message);
	}

}