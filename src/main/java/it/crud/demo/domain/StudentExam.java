package it.crud.demo.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "students_exams")
public class StudentExam {

	@Id
	@JoinColumn(name = "student_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DROIT_SEQ")
	@SequenceGenerator(name = "DROIT_SEQ", sequenceName = "DROIT_ACCEES_SEQ", allocationSize = 1, initialValue = 1)
	private int id;

	LocalDate bookingDate;
	int vote;

	@ManyToOne
	@JsonBackReference
	Student student;

	@ManyToOne
	@JsonBackReference
	Exam exam;

	public StudentExam() {
	}

	public StudentExam(int id, LocalDate bookingDate, int vote, Student student, Exam exam) {
		super();
		this.id = id;
		this.bookingDate = bookingDate;
		this.vote = vote;
		this.student = student;
		this.exam = exam;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

}
