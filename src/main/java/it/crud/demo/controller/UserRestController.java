package it.crud.demo.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.crud.demo.domain.User;
import it.crud.demo.dto.PersonDto;
import it.crud.demo.dto.UserDto;
import it.crud.demo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

	private UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	@CrossOrigin
	@PutMapping(value = "/update")
	public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
		User user = userService.addOrUpdateUser(userDto);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping(value = "/add")
	public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
		User user = userService.addOrUpdateUser(userDto);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping(value = "/login")
	public ResponseEntity<PersonDto> login(@RequestBody UserDto userDto) {
		PersonDto userDateDto = userService.validateUser(userDto.getUserId(), userDto.getPassword());
	    return new ResponseEntity<>(userDateDto, HttpStatus.OK);
	}
	@CrossOrigin
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping(value = "/recuperapassword")
	public ResponseEntity<?> recuperaPassword(@RequestBody String email) throws UnsupportedEncodingException{
		userService.recuperaPassword(email);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
