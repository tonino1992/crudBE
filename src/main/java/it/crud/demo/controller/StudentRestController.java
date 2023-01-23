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

import it.crud.demo.domain.Student;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.services.StudentService;

@RestController
@RequestMapping(value = "/students")
public class StudentRestController {

	StudentService studentService;	
	
	public StudentRestController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	@CrossOrigin
	@GetMapping(value = "/all")
	public ResponseEntity<List<StudentDto>> getAllStudents(){
		List<StudentDto> listDto = studentService.getAllStudents();
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<StudentDto> getStudentById(@PathVariable int id) {
		StudentDto studentDto = studentService.findStudentById(id);
		return new ResponseEntity<>(studentDto, HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping(value = "/add")
	public ResponseEntity<Student> addStudent(@RequestBody StudentDto studentDto) {
		Student student = studentService.addStudent(studentDto);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}
	@CrossOrigin
	@PutMapping(value = "/update")
	public ResponseEntity<Student> updateStudent(@RequestBody StudentDto studentDto) {
		Student student = studentService.updateStudent(studentDto);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}
	@CrossOrigin
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteTeacher(@PathVariable("id") int id) {
		studentService.deleteStudent(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
