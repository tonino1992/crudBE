package it.crud.demo.exceptions;

public class StudentCourseAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StudentCourseAlreadyExistsException(String message) {
        super(message);
    }

}
