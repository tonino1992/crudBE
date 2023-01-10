package it.crud.demo.domain;

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
@Table(name = "students_courses")
public class StudentCourse {
	
	@Id
	@JoinColumn(name = "student_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DROIT_SEQ")
	@SequenceGenerator(name = "DROIT_SEQ", sequenceName = "DROIT_ACCEES_SEQ", allocationSize = 1, initialValue = 1)
	private int id;
	
	
	@ManyToOne	
	@JsonBackReference
	private Student student;
	
	@ManyToOne
	@JsonBackReference
	private Course course;

	public StudentCourse() {}
	
	public StudentCourse(Student student, Course course) {
		
		this.student = student;
		this.course = course;
	}


	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}	
		
}
