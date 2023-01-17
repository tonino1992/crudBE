package it.crud.demo.dto;

import java.time.LocalDate;

public class StudentExamDto {

	LocalDate bookingDate;
	int vote;
	private int studentId;
	private int examId;
	
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getExamId() {
		return examId;
	}
	public void setExamId(int courseId) {
		this.examId = courseId;
	}
	
	

}
