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

import it.crud.demo.domain.Student;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.exceptions.StudentNotFoundException;
import it.crud.demo.exceptions.UserNotFoundException;
import it.crud.demo.services.StudentService;

@RestController
@RequestMapping(value = "/students")
public class StudentRestController {

	StudentService studentService;

	public StudentRestController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}


	@GetMapping(value = "/all")
	public ResponseEntity<List<StudentDto>> getAllStudents() {
		try {
			List<StudentDto> listDto = studentService.getAllStudents();
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping(value = "/{id}")
	public ResponseEntity<StudentDto> getStudentById(@PathVariable int id) {
		try {
			StudentDto studentDto = studentService.findStudentById(id);
			return new ResponseEntity<>(studentDto, HttpStatus.OK);
		} catch (StudentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/add")
	public ResponseEntity<Student> addStudent(@RequestBody StudentDto studentDto) {
		try {
			Student student = studentService.addStudent(studentDto);
			return new ResponseEntity<>(student, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PutMapping(value = "/update")
	public ResponseEntity<Student> updateStudent(@RequestBody StudentDto studentDto) {
		try {
			Student student = studentService.updateStudent(studentDto);
			return new ResponseEntity<>(student, HttpStatus.OK);
		} catch (StudentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
		studentService.deleteStudent(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
