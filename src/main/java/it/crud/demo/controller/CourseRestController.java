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

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import it.crud.demo.domain.Course;
import it.crud.demo.dto.CourseDto;
import it.crud.demo.services.CourseService;

@RestController
@OpenAPIDefinition
@RequestMapping(value = "/courses")
public class CourseRestController {

	private CourseService courseService;

	public CourseRestController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<CourseDto>> getAllCourses() {
		List<CourseDto> listDto = this.courseService.getAllCourses();
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CourseDto> getCourseById(@PathVariable int id) {
		CourseDto courseDto = courseService.findCourseById(id);
		return new ResponseEntity<>(courseDto, HttpStatus.OK);
	}


	@PostMapping(value = "/add")
	public ResponseEntity<Course> addCourse(@RequestBody CourseDto courseDto) {
		Course course = courseService.addCourse(courseDto);
	return new ResponseEntity<>(course, HttpStatus.CREATED);
	}

}
