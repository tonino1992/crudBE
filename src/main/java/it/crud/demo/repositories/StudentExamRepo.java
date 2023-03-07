package it.crud.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.crud.demo.domain.StudentExam;
import it.crud.demo.domain.id.StudentExamId;

public interface StudentExamRepo extends JpaRepository<StudentExam, StudentExamId>{

	Optional<StudentExam> findById(StudentExamId id);
	
	@Query("SELECT se FROM StudentExam se WHERE se.id.exam.id = :examId")
    List<StudentExam> findAllByExamId(@Param("examId") int examId);
	
}
