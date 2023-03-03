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
import it.crud.demo.domain.Course;
import it.crud.demo.dto.CourseDto;
import it.crud.demo.dto.CourseJoinTeacherDto;
import it.crud.demo.exceptions.CourseNotFoundException;
import it.crud.demo.exceptions.TeacherNotFoundException;
import it.crud.demo.services.CourseService;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(value = "/courses")
public class CourseRestController {

	private CourseService courseService;

	public CourseRestController(CourseService courseService) {
		this.courseService = courseService;
	}
	

	@GetMapping(value = "/all")
	public ResponseEntity<List<CourseJoinTeacherDto>> getAllCourses() {
		List<CourseJoinTeacherDto> listDto = this.courseService.getAllCourses();
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}
	

	@GetMapping(value = "/{id}")
	public ResponseEntity<CourseJoinTeacherDto> getCourseById(@PathVariable int id) {
	    try {
	        CourseJoinTeacherDto courseDto = courseService.findCourseById(id);
	        return new ResponseEntity<>(courseDto, HttpStatus.OK);
	    } catch (CourseNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	@PutMapping(value = "/update")
	public ResponseEntity<Course> updateCourse(@RequestBody CourseDto courseDto) {
	    try {
	        Course updatedCourse = courseService.updateCourse(courseDto);
	        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
	    } catch (CourseNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } catch (TeacherNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    } catch (IllegalArgumentException e) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@PostMapping(value = "/add")
	public ResponseEntity<?> addCourse(@RequestBody CourseDto courseDto) {
	    try {
	        Course course = courseService.addCourse(courseDto);
	        return new ResponseEntity<>(course, HttpStatus.CREATED);
	    } catch (TeacherNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
	    }
	}
	
	@GetMapping(value = "/exams/{courseId}")
	public ResponseEntity<?> getExamIdByCourseId(@PathVariable int courseId) {
	    try {
	        int examId = courseService.getExamIdByCourseId(courseId);
	        return new ResponseEntity<>(examId, HttpStatus.OK);
	    } catch (CourseNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
	    }
	}

}
