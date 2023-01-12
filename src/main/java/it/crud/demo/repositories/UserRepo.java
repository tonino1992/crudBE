package it.crud.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.User;

public interface UserRepo extends JpaRepository<User, String>{

	Optional<User> findByUserId(String userId);
	
}
