package it.crud.demo.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.crud.demo.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

	@OneToMany(mappedBy = "userId")
	@JsonManagedReference
	private List<ResetPasswordToken> tokens;

}
