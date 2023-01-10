package it.crud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.Student;

public interface StudentRepo extends JpaRepository<Student, Integer>{

}
