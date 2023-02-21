package it.crud.demo.dto;

import java.time.LocalDate;

import it.crud.demo.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
	private int id;
    private String userId;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private UserRole role;

   
}

