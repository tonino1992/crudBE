package it.crud.demo.exceptions;

public class StudentExamNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StudentExamNotFoundException(String message) {
		super(message);
	}
}
