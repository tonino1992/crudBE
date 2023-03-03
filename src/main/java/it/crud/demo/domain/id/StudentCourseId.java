package it.crud.demo.domain.id;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import it.crud.demo.domain.Course;
import it.crud.demo.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class StudentCourseId implements Serializable {
	@ManyToOne
	@JsonBackReference
	private Student student;

	@ManyToOne
	@JsonBackReference
	private Course course;

	
}
