package it.crud.demo.domain;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DROIT_SEQ")
	@SequenceGenerator(name = "DROIT_SEQ", sequenceName = "DROIT_ACCEES_SEQ", allocationSize = 1, initialValue = 1)
	@Column(nullable = false, updatable = false)
	private int id;

	private String name;
	private String surname;
	private LocalDate dateOfBirth;
	
	@OneToMany(mappedBy = "id.student")
	@JsonManagedReference
	private List<StudentCourse> courses;
	
	@OneToMany(mappedBy = "id.student")
	@JsonManagedReference
	private List<StudentExam> exams;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	User userId;
	

	public Student() {
	}

	public Student(int id, String name, String surname, LocalDate dateOfBirth) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<StudentCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<StudentCourse> courses) {
		this.courses = courses;
	}

	public List<StudentExam> getExams() {
		return exams;
	}

	public void setExams(List<StudentExam> exams) {
		this.exams = exams;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}
	
	

}
