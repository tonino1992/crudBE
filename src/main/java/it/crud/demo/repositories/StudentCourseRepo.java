package it.crud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.StudentCourse;

public interface StudentCourseRepo extends JpaRepository<StudentCourse, Integer>{

}
