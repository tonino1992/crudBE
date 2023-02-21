package it.crud.demo.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.crud.demo.domain.Course;
import it.crud.demo.domain.Student;

@SuppressWarnings("serial")
@Embeddable
public class StudentCourseId implements Serializable {
	@ManyToOne
	@JsonBackReference
	private Student student;

	@ManyToOne
	@JsonBackReference
	private Course course;

	public StudentCourseId(Student student, Course course) {
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
