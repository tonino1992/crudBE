package it.crud.demo.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ResetPasswordToken {

	@Id
	private String token;
	private LocalDate expireDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User userId;

	public ResetPasswordToken() {
	}

	public ResetPasswordToken(String token, User userId) {
		this.token = token;
		this.userId = userId;
		this.expireDate = LocalDate.now().plusDays(7);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public User getUser() {
		return userId;
	}

	public void setUser(User userId) {
		this.userId = userId;
	}
}
