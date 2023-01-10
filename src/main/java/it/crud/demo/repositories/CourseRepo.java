package it.crud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.Course;

public interface CourseRepo extends JpaRepository<Course, Integer>{

}
