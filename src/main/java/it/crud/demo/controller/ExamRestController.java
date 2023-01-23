package it.crud.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import it.crud.demo.dto.ExamJoinCourseDto;
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
	@CrossOrigin
	@GetMapping(value = "/all")
	public ResponseEntity<List<ExamJoinCourseDto>> getAllExams() {
		List<ExamJoinCourseDto> listDto = examService.getAllExams();
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<ExamJoinCourseDto> getExamById(@PathVariable("id") int id) {
		ExamJoinCourseDto examDto = examService.getExamById(id);
		return new ResponseEntity<>(examDto, HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping(value = "/{id}/students")
	public ResponseEntity<List<StudentDto>> getStudentsByExam(@PathVariable("id") int id) {
		List<StudentDto> students = studentExamService.getStudentsByExam(id);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping(value = "/add")
	public ResponseEntity<Exam> addTeacher(@RequestBody ExamDto examDto) {
		Exam exam = examService.addExam(examDto);
		return new ResponseEntity<>(exam, HttpStatus.CREATED);
	}
	@CrossOrigin
	@PutMapping(value = "/update")
	public ResponseEntity<Exam> updateExam(@RequestBody ExamDto examDto){
		Exam exam = examService.updateExam(examDto);				
		return new ResponseEntity<>(exam, HttpStatus.OK);
	}
	@CrossOrigin
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteExam(@PathVariable("id") int id) {
		examService.deleteExam(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
