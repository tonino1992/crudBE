package it.crud.demo.exceptions;

public class StudentExamAlreadyBookedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public StudentExamAlreadyBookedException(String message) {
        super(message);
    }

}
