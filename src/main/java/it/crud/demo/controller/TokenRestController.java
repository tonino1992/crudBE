package it.crud.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.crud.demo.dto.UserDto;
import it.crud.demo.exceptions.TokenExpiredException;
import it.crud.demo.exceptions.TokenNotFoundException;
import it.crud.demo.services.TokenService;

@RestController
@RequestMapping(value = "/token")
public class TokenRestController {

	TokenService tokenService;

	public TokenRestController(TokenService tokenService) {
		super();
		this.tokenService = tokenService;
	}


    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyTokenValidity(@RequestBody String token) {
        try {
            String userId = tokenService.verifyTokenValidity(token);
            return new ResponseEntity<>(userId, HttpStatus.OK);
        } catch (TokenNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TokenExpiredException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
	

	@PutMapping("/change-password")
	 public ResponseEntity<?> changePassword(@RequestBody UserDto userDto) {
        tokenService.changePassword(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
