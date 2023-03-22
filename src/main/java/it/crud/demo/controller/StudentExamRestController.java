package it.crud.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.crud.demo.dto.ExamJoinCourseDto;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.dto.StudentExamDto;
import it.crud.demo.exceptions.ExamNotFoundException;
import it.crud.demo.exceptions.StudentExamAlreadyBookedException;
import it.crud.demo.exceptions.StudentExamNotFoundException;
import it.crud.demo.exceptions.StudentNotFoundException;
import it.crud.demo.services.StudentExamService;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(value = "/studentexams")
public class StudentExamRestController {

	private StudentExamService studentExamService;

	public StudentExamRestController(StudentExamService studentExamService) {
		this.studentExamService = studentExamService;
	}

	@PostMapping(value = "/booking")
	public ResponseEntity<?> studentExamBooking(@RequestBody StudentExamDto studentExamDto) {
		try {
			studentExamService.studentExamBooking(studentExamDto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (StudentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (ExamNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (StudentExamAlreadyBookedException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updatevote")
	public ResponseEntity<?> studentExamUpdateVote(@RequestBody StudentExamDto studentExamDto) {
		try {
			studentExamService.updateStudentExam(studentExamDto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (StudentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (ExamNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (StudentExamNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}/examstodo")
	public ResponseEntity<List<ExamJoinCourseDto>> getStudentExamsToDo(@PathVariable int id) {
		try {
			List<ExamJoinCourseDto> exams = studentExamService.getExamsToDoByStudent(id);
			return new ResponseEntity<>(exams, HttpStatus.OK);
		} catch (StudentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}/students")
	public ResponseEntity<List<StudentDto>> getStudentsByExam(@PathVariable("id") int id) {
		try {
			List<StudentDto> listDto = studentExamService.getStudentsByExam(id);
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (ExamNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}/examsdone")
	public ResponseEntity<List<ExamJoinCourseDto>> getStudentExamsDone(@PathVariable int id) {
		try {
			List<ExamJoinCourseDto> exams = studentExamService.getExamsDoneByStudent(id);
			return new ResponseEntity<>(exams, HttpStatus.OK);
		} catch (StudentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}/all")
	public ResponseEntity<List<StudentExamDto>> findAllByExamId(@PathVariable int id) {
		try {
			List<StudentExamDto> studentExams = studentExamService.getStudentExamsByExam(id);
			return new ResponseEntity<>(studentExams, HttpStatus.OK);
		} catch (ExamNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}/doneexams")
	public ResponseEntity<List<StudentExamDto>> getDoneExamsByStudent(@PathVariable int id) {
		try {
			List<StudentExamDto> studentExams = studentExamService.getDoneExamsByStudent(id);
			return new ResponseEntity<>(studentExams, HttpStatus.OK);
		} catch (StudentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
