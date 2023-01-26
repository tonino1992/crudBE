package it.crud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.ResetPasswordToken;

public interface ResetPasswordTokenRepo extends JpaRepository<ResetPasswordToken, String>{

}
