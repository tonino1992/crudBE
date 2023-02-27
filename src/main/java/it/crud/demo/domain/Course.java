package it.crud.demo.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DROIT_SEQ")
	@SequenceGenerator(name = "DROIT_SEQ", sequenceName = "DROIT_ACCEES_SEQ", allocationSize = 1, initialValue = 1)
	@Column(nullable = false, updatable = false)
	private int id;

	private String subject;
	private double hourAmount;
	private boolean isDone;

	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	@JsonBackReference
	private Teacher teacher;

	@OneToOne(mappedBy = "course")
	@JsonManagedReference
	private Exam exam;
	
	@OneToMany(mappedBy = "id.course")
	@JsonManagedReference
	private List<StudentCourse> students;

	public Course(int id, String subject, double hourAmount) {
		super();
		this.id = id;
		this.subject = subject;
		this.hourAmount = hourAmount;
	}

	
	
}
