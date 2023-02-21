package it.crud.demo.dto;

import it.crud.demo.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class StudentDto extends PersonDto{
	
	private String password;
	private String email;
	
	
	@Override
	public UserRole getRole() {
		return UserRole.STUDENT;
	}
	
}
