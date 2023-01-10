package it.crud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.StudentExam;

public interface StudentExamRepo extends JpaRepository<StudentExam, Integer>{

}
