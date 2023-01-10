package it.crud.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.crud.demo.domain.Exam;
import it.crud.demo.dto.ExamDto;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.services.ExamService;
import it.crud.demo.services.StudentExamService;

@RestController
@RequestMapping(value = "/exams")
public class ExamRestController {

	private ExamService examService;
	private StudentExamService studentExamService;

	public ExamRestController(ExamService examService) {
		super();
		this.examService = examService;
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<ExamDto>> getAllExams() {
		List<ExamDto> listDto = examService.getAllExams();
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ExamDto> getExamById(@PathVariable("id") int id) {
		ExamDto examDto = examService.getExamById(id);
		return new ResponseEntity<>(examDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/students")
	public ResponseEntity<List<StudentDto>> getStudentsByExam(@PathVariable("id") int id) {
		List<StudentDto> students = studentExamService.getStudentsByExam(id);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<Exam> addTeacher(@RequestBody ExamDto examDto) {
		Exam exam = examService.addExam(examDto);
		return new ResponseEntity<>(exam, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<Exam> updateExam(@RequestBody ExamDto examDto){
		Exam exam = examService.updateExam(examDto);				
		return new ResponseEntity<>(exam, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteExam(@PathVariable("id") int id) {
		examService.deleteExam(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
