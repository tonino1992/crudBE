package it.crud.demo.dto;


import it.crud.demo.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private String userId;
	private String email;
	private String password;
	private UserRole role;

}
