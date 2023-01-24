package it.crud.demo.dto;

import java.time.LocalDate;

import it.crud.demo.domain.enums.UserRole;

public class PersonDto {
	private int id;
    private String userId;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private UserRole role;
    
    public PersonDto() {};

    public PersonDto(int id, String userId, String name, String surname, LocalDate dateOfBirth) {
    	this.id = id;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

	public UserRole getRole() {
		return role;
	}

	protected void setRole(UserRole role) {
		this.role = role;
	}
    
    
}

