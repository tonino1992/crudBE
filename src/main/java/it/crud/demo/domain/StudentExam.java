package it.crud.demo.domain;

import java.time.LocalDate;

import it.crud.demo.domain.id.StudentExamId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "students_exams")
public class StudentExam {

	@EmbeddedId
	private StudentExamId id;

	LocalDate bookingDate;
	int vote;


	public StudentExam() {
	}


	public StudentExam(StudentExamId id, LocalDate bookingDate, int vote) {
		super();
		this.id = id;
		this.bookingDate = bookingDate;
		this.vote = vote;
	}


	public StudentExamId getId() {
		return id;
	}


	public void setId(StudentExamId id) {
		this.id = id;
	}


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

	
}
