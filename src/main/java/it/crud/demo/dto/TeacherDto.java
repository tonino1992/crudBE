package it.crud.demo.dto;

import it.crud.demo.domain.enums.UserRole;

public class TeacherDto extends PersonDto{

	public TeacherDto(){
		super();
	};
	
	public TeacherDto(int id, String userId, String name, String surname, int age) {
		super(id, userId, name, surname, age);
	}
	

	@Override
	public UserRole getRole() {
		return UserRole.TEACHER;
	}
	

}
