package it.crud.demo.dto;

import it.crud.demo.domain.enums.UserRole;

public class UserDto {
	
	private String userId;
	private String password;
	private UserRole role;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	

}
