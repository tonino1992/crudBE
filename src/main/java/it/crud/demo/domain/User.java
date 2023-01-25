package it.crud.demo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.crud.demo.domain.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(nullable = false)
	private String userId;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@OneToOne(mappedBy = "userId")
	@JsonManagedReference
	private Teacher teacher;

	@OneToOne(mappedBy = "userId")
	@JsonManagedReference
	private Student student;

	public User() {
	}

	public User(String userId, String password, UserRole role, Teacher teacher, Student student) {
		super();
		this.userId = userId;
		this.password = password;
		this.role = role;
		this.teacher = teacher;
		this.student = student;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
