package it.crud.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.StudentExam;
import it.crud.demo.domain.id.StudentExamId;

public interface StudentExamRepo extends JpaRepository<StudentExam, StudentExamId>{

	Optional<StudentExam> findById(StudentExamId id);
	
}
