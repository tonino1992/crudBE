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
import it.crud.demo.exceptions.ExamNotFoundException;
import it.crud.demo.exceptions.StudentExamAlreadyBookedException;
import it.crud.demo.exceptions.StudentExamNotFoundException;
import it.crud.demo.exceptions.StudentNotFoundException;
import it.crud.demo.services.StudentExamService;

@RestController
@RequestMapping(value = "/studentexams")
public class StudentExamRestController {
	
	private StudentExamService studentExamService;

	public StudentExamRestController(StudentExamService studentExamService) {
		this.studentExamService = studentExamService;
	}
	

	@PostMapping(value = "/booking")
	public ResponseEntity<StudentExam> studentExamBooking(@RequestBody StudentExamDto studentExamDto) {
	    try {
	        StudentExam studentExam = studentExamService.studentExamBooking(studentExamDto);
	        return new ResponseEntity<>(studentExam, HttpStatus.CREATED);
	    } catch (StudentNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } catch (ExamNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    } catch (StudentExamAlreadyBookedException e) {
	        return new ResponseEntity<>(HttpStatus.CONFLICT);
	    }catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PutMapping(value = "/updatevote")
	public ResponseEntity<StudentExam> studentExamUpdateVote(@RequestBody StudentExamDto studentExamDto) {
	    try {
	        StudentExam studentExam = studentExamService.updateStudentExam(studentExamDto);
	        return new ResponseEntity<>(studentExam, HttpStatus.OK);
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
	public ResponseEntity<List<ExamJoinCourseDto>> getStudentExamsToDo(@PathVariable int id){
	    try {
	        List<ExamJoinCourseDto> exams = studentExamService.getExamsToDoByStudent(id);
	        return new ResponseEntity<>(exams, HttpStatus.OK);
	    } catch (StudentNotFoundException e) {
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

}
