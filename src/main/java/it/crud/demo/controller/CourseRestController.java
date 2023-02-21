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
import it.crud.demo.domain.Course;
import it.crud.demo.dto.CourseDto;
import it.crud.demo.dto.CourseJoinTeacherDto;
import it.crud.demo.exceptions.CourseNotFoundException;
import it.crud.demo.services.CourseService;

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


	@PostMapping(value = "/add")
	public ResponseEntity<?> addCourse(@RequestBody CourseDto courseDto) {
	    try {
	        Course course = courseService.addCourse(courseDto);
	        return new ResponseEntity<>(course, HttpStatus.CREATED);
	    } catch (CourseNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
	    }
	}
}
