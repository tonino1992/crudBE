package it.crud.demo.exceptions;

public class ExamNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 5246398098758199917L;

	public ExamNotFoundException(String message) {
		super(message);
	}
}
