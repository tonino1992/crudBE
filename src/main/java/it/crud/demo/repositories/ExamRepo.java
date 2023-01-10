package it.crud.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.crud.demo.domain.Exam;

public interface ExamRepo extends JpaRepository<Exam, Integer>{

}
