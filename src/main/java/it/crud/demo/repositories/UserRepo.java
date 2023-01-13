package it.crud.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.User;

public interface UserRepo extends JpaRepository<User, String>{

	User findByUserId(String userId);
	
}
