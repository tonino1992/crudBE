package it.crud.demo.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exams")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DROIT_SEQ")
	@SequenceGenerator(name = "DROIT_SEQ", sequenceName = "DROIT_ACCEES_SEQ", allocationSize = 1, initialValue = 1)
	@Column(nullable = false, updatable = false)
	private int id;

	private Date day;
	private Time hour;
	private String classroom;
	private boolean isDone;

	@OneToOne
	@JoinColumn(name = "course_id")
	@JsonBackReference
	private Course course;
	
	@OneToMany(mappedBy = "id.exam")
	@JsonManagedReference
	private List<StudentExam> students;

	public Exam(int id, Date date, String classroom, Course course) {
		this.id = id;
		this.day = date;
		this.classroom = classroom;
		this.course = course;
	}

}


