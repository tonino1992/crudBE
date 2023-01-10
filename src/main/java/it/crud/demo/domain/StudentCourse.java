package it.crud.demo.domain;

import it.crud.demo.domain.id.StudentCourseId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "students_courses")
public class StudentCourse {
	
	@EmbeddedId
	private StudentCourseId id;
	
	


	public StudentCourse() {}




	public StudentCourse(StudentCourseId id) {
		this.id = id;
	}




	public StudentCourseId getId() {
		return id;
	}




	public void setId(StudentCourseId id) {
		this.id = id;
	}
	
	
		
}
