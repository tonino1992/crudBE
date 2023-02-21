package it.crud.demo.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordToken {

	@Id
	private String token;
	private LocalDate expireDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User userId;

	public ResetPasswordToken(String token, User userId) {
		this.token = token;
		this.userId = userId;
		this.expireDate = LocalDate.now().plusDays(7);
	}

}
