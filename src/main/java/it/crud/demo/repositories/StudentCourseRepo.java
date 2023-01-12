package it.crud.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.StudentCourse;
import it.crud.demo.domain.id.StudentCourseId;

public interface StudentCourseRepo extends JpaRepository<StudentCourse, StudentCourseId>{

	
	Optional<StudentCourse> findById(StudentCourseId id);
}
