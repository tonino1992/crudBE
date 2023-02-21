package it.crud.demo.domain;

import java.time.LocalDate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.crud.demo.domain.id.StudentExamId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students_exams")
public class StudentExam {

	@EmbeddedId
	private StudentExamId id;

	LocalDate bookingDate;
	int vote;


	
}
