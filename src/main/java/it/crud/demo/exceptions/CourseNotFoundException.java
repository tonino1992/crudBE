package it.crud.demo.exceptions;

public class CourseNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4300972183448663324L;

	public CourseNotFoundException(String message) {
		super(message);
	}
}
