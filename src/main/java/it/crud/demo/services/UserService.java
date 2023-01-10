package it.crud.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.User;
import it.crud.demo.dto.UserDto;
import it.crud.demo.exceptions.UserNotFoundException;
import it.crud.demo.repositories.UserRepo;

@Service
public class UserService {

	private UserRepo userRepo;

	
	@Autowired
	public UserService(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}

	public User findUserDaoById(String userId) {
		return userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
	}
	
	public User addOrUpdateUser(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setPassword(userDto.getPassword());
		user.setRole(userDto.getRole());
		return userRepo.save(user);
	}
	
	public void deleteUser(String userId) {
		userRepo.deleteById(userId);
	}
}
