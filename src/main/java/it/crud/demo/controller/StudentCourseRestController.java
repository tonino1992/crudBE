package it.crud.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import it.crud.demo.exceptions.CourseNotFoundException;
import it.crud.demo.exceptions.StudentCourseAlreadyExistsException;
import it.crud.demo.exceptions.StudentNotFoundException;
import it.crud.demo.services.StudentCourseService;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(value = "/studentcourses")
public class StudentCourseRestController {

	StudentCourseService studentCourseService;

	public StudentCourseRestController(StudentCourseService studentCourseService) {
		this.studentCourseService = studentCourseService;
	}


	@PostMapping(value = "/iscription")
	public ResponseEntity<StudentCourse> enrollStudentInCourse(@RequestBody StudentCourseDto studentCourseDto) {
		try {
			StudentCourse studentCourse = studentCourseService.enrollStudentInCourse(studentCourseDto);
			return new ResponseEntity<>(studentCourse, HttpStatus.CREATED);
		} catch (StudentCourseAlreadyExistsException e) {
			// error 409
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (StudentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (CourseNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping(value = "/{id}/students")
	public ResponseEntity<List<StudentDto>> getStudentsByCourse(@PathVariable int id) {
		try {
			List<StudentDto> students = studentCourseService.getStudentsByCourse(id);
			return new ResponseEntity<>(students, HttpStatus.OK);
		} catch (CourseNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (Exception e ) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping(value = "/{id}/courses")
	public ResponseEntity<List<CourseJoinTeacherDto>> getStudentCourses(@PathVariable int id) {
		try {
			List<CourseJoinTeacherDto> courses = studentCourseService.getCoursesByStudent(id);
			return new ResponseEntity<>(courses, HttpStatus.OK);
		} catch (StudentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e ) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
