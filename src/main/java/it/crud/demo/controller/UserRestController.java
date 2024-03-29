package it.crud.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.crud.demo.dto.ChangeUserIdDto;
import it.crud.demo.dto.PersonDto;
import it.crud.demo.dto.UserDto;
import it.crud.demo.exceptions.IllegalPasswordException;
import it.crud.demo.exceptions.UserIdAlreadyExsistException;
import it.crud.demo.exceptions.UserNotFoundException;
import it.crud.demo.services.JwtService;
import it.crud.demo.services.UserService;


@RestController
@RequestMapping(value = "/users")
public class UserRestController {

	private UserService userService;
	private JwtService jwtService;

	public UserRestController(UserService userService, JwtService jwtService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}

	@PutMapping(value = "/update")
	public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
		try {
			userService.updateUser(userDto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
		userService.addUser(userDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PermitAll
	@CrossOrigin
	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody UserDto userDto) {
		try {
			// Autenticazione dell'utente e raccolta dei suoi dati
			PersonDto userDateDto = userService.validateUser(userDto.getUserId(), userDto.getPassword());

			// Creazione dei claims da includere nel token JWT
			Map<String, Object> claims = new HashMap<>();
			claims.put("sub", userDto.getUserId());
			claims.put("id", userDateDto.getId());
			claims.put("name", userDateDto.getName());
			claims.put("surname", userDateDto.getSurname());
			claims.put("email", userDateDto.getEmail());
			claims.put("role", userDateDto.getRole());

			// Generazione del token JWT
			String token = jwtService.createToken(claims, userDto.getUserId());

			// Restituzione del token JWT nella risposta HTTP
			return new ResponseEntity<>(token, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (IllegalPasswordException e) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} catch (IllegalStateException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
		try {
			userService.deleteUser(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("permitAll()")
	@PostMapping(value = "/recupera-password")
	public ResponseEntity<?> recuperaPassword(@RequestBody String userId) {
		try {
			userService.recuperaPassword(userId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (MessagingException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/change-userid")
	public ResponseEntity<?> changeUserId(@RequestBody ChangeUserIdDto userIds) {
		try {
			userService.changeUserId(userIds);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (UserIdAlreadyExsistException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
