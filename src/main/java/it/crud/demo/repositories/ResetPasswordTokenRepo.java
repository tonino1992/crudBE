package it.crud.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.ResetPasswordToken;
import it.crud.demo.domain.User;

public interface ResetPasswordTokenRepo extends JpaRepository<ResetPasswordToken, String>{

	Optional<ResetPasswordToken> findByToken(String token);

	void deleteByUserId(User userId);
	
	
}
