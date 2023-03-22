package it.crud.demo.dto;

import it.crud.demo.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class StudentDto extends PersonDto{

	@Override
	public UserRole getRole() {
		return UserRole.STUDENT;
	}
	
}
