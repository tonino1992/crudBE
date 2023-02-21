package it.crud.demo.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.crud.demo.domain.id.StudentCourseId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students_courses")
public class StudentCourse {
	
	@EmbeddedId
	private StudentCourseId id;
		
}
