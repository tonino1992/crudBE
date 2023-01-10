package it.crud.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, Integer>{
	
	Optional<Teacher> findTeacherById(int id);
	
}
