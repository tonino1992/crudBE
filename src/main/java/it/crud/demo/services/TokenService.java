package it.crud.demo.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import it.crud.demo.domain.ResetPasswordToken;
import it.crud.demo.domain.User;
import it.crud.demo.dto.UserDto;
import it.crud.demo.exceptions.TokenExpiredException;
import it.crud.demo.exceptions.TokenNotFoundException;
import it.crud.demo.repositories.ResetPasswordTokenRepo;
import jakarta.transaction.Transactional;

@Service
public class TokenService {
	
	private ResetPasswordTokenRepo tokenRepo;
	private UserService userService;

	public TokenService(ResetPasswordTokenRepo tokenRepo, UserService userService) {
		super();
		this.tokenRepo = tokenRepo;
		this.userService = userService;
	}
	
	private ResetPasswordToken findDaoToken(String token) {
		return tokenRepo.findByToken(token).orElseThrow(() -> new TokenNotFoundException("Token non presente"));
	}
	
	public String verifyTokenValidity(String token) {
		ResetPasswordToken gettedToken = this.findDaoToken(token);
		if (gettedToken.getExpireDate().isBefore(LocalDate.now())) {
			throw new TokenExpiredException("Token scaduto");
		}
		return gettedToken.getUser().getUserId();
	}
	
	@Transactional
	public void changePassword(UserDto userDto) {
		User user = userService.findUserDaoById(userDto.getUserId());		
		userDto.setEmail(user.getEmail());
		userDto.setRole(user.getRole());		
		User userUpdated = userService.addOrUpdateUser(userDto);	
		
		tokenRepo.deleteByUserId(userUpdated);			
	}
}
