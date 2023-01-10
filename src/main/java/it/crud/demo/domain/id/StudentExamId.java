package it.crud.demo.domain.id;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.crud.demo.domain.Exam;
import it.crud.demo.domain.Student;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@SuppressWarnings("serial")
@Embeddable
public class StudentExamId implements Serializable{

	@ManyToOne	
	@JsonBackReference
	private Student student;
	
	@ManyToOne
	@JsonBackReference
	private Exam exam;

	public StudentExamId(Student student, Exam exam) {
		this.student = student;
		this.exam = exam;
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
