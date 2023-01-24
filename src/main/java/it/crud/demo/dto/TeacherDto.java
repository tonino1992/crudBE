package it.crud.demo.dto;

import java.time.LocalDate;

import it.crud.demo.domain.enums.UserRole;

public class TeacherDto extends PersonDto{
	
	private String password;

	public TeacherDto(){
		super();
	};		

	public TeacherDto(int id, String userId, String name, String surname, LocalDate dateOfBirth) {
		super(id, userId, name, surname, dateOfBirth);
	}

	@Override
	public UserRole getRole() {
		return UserRole.TEACHER;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
}
