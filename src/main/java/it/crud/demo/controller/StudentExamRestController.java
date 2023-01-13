package it.crud.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.crud.demo.domain.StudentExam;
import it.crud.demo.dto.ExamJoinCourseDto;
import it.crud.demo.dto.StudentExamDto;
import it.crud.demo.services.StudentExamService;

@RestController
@RequestMapping(value = "/studentexams")
public class StudentExamRestController {
	
	private StudentExamService studentExamService;

	public StudentExamRestController(StudentExamService studentExamService) {
		this.studentExamService = studentExamService;
	}
		
	@PostMapping(value = "/booking")
	public ResponseEntity<StudentExam> studentExamBooking(@RequestBody StudentExamDto studentExamDto){		
		StudentExam studentExam = studentExamService.studentExamBooking(studentExamDto);
		return new ResponseEntity<>(studentExam, HttpStatus.CREATED);
	} 
	
	@PutMapping(value = "/updatevote")
	public ResponseEntity<StudentExam> studentExamUpdateVote(@RequestBody StudentExamDto studentExamDto){		
		StudentExam studentExam = studentExamService.updateStudentExam(studentExamDto);
		return new ResponseEntity<>(studentExam, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}/examstodo")
	public ResponseEntity<List<ExamJoinCourseDto>> getStudentExamsToDo(@PathVariable int id){
		List<ExamJoinCourseDto> exams = studentExamService.getExamsToDoByStudent(id);
		return new ResponseEntity<>(exams, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/examsdone")
	public ResponseEntity<List<ExamJoinCourseDto>> getStudentExamsDone(@PathVariable int id){
		List<ExamJoinCourseDto> exams = studentExamService.getExamsDoneByStudent(id);
		return new ResponseEntity<>(exams, HttpStatus.OK);
	}
}
