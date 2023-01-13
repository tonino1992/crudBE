package it.crud.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.crud.demo.domain.StudentCourse;
import it.crud.demo.dto.CourseJoinTeacherDto;
import it.crud.demo.dto.StudentCourseDto;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.services.StudentCourseService;

@RestController
@RequestMapping(value = "/studentcourses")
public class StudentCourseRestController {

	StudentCourseService studentCourseService;

	public StudentCourseRestController(StudentCourseService studentCourseService) {
		this.studentCourseService = studentCourseService;
	}

	@PostMapping(value = "/iscription")
	public ResponseEntity<StudentCourse> studentCourseIscrioption(@RequestBody StudentCourseDto studentCourseDto) {
		StudentCourse studentCourse = studentCourseService.studentCourseIscription(studentCourseDto);
		return new ResponseEntity<>(studentCourse, HttpStatus.CREATED);
	}
	

	@GetMapping(value = "/{id}/students")
	public ResponseEntity<List<StudentDto>> getStudentsByCourse(@PathVariable int id) {
		List<StudentDto> students = studentCourseService.getStudentsbyCourse(id);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/courses")
	public ResponseEntity<List<CourseJoinTeacherDto>> getStudentCourses(@PathVariable int id){
		List<CourseJoinTeacherDto> courses = studentCourseService.getCoursesByStudent(id);
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

}
