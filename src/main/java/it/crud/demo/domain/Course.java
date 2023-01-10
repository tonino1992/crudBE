package it.crud.demo.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
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

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public double getHourAmount() {
		return hourAmount;
	}

	public void setHourAmount(double hourAmount) {
		this.hourAmount = hourAmount;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public List<StudentCourse> getStudents() {
		return students;
	}

	public void setStudents(List<StudentCourse> students) {
		this.students = students;
	}
	
}
