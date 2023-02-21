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

import it.crud.demo.domain.Teacher;
import it.crud.demo.dto.CourseDto;
import it.crud.demo.dto.ExamJoinCourseDto;
import it.crud.demo.dto.TeacherDto;
import it.crud.demo.exceptions.TeacherNotFoundException;
import it.crud.demo.services.TeacherService;

@RestController
@RequestMapping(value = "/teachers")
public class TeacherRestController {

	private TeacherService teacherService;

	public TeacherRestController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}


	@GetMapping(value = "/all")
	public ResponseEntity<List<TeacherDto>> getAllTeachers() {
		try {
			List<TeacherDto> listDto = teacherService.getAllTeachers();
			return new ResponseEntity<>(listDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping(value = "/{id}")
	public ResponseEntity<TeacherDto> getTeacherById(@PathVariable("id") int id) {
		try {
			TeacherDto teacherDto = teacherService.getTeacherById(id);
			return new ResponseEntity<>(teacherDto, HttpStatus.OK);
		} catch (TeacherNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}/courses")
	public ResponseEntity<List<CourseDto>> getTeacherCourses(@PathVariable("id") int id) {
		try {
			List<CourseDto> listCourseDto = teacherService.getTeacherCourses(id);
			return new ResponseEntity<>(listCourseDto, HttpStatus.OK);
		} catch (TeacherNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}/exams")
	public ResponseEntity<List<ExamJoinCourseDto>> getTeacherExams(@PathVariable("id") int id) {
		try {
			List<ExamJoinCourseDto> listExamDto = teacherService.getTeacherExams(id);
			return new ResponseEntity<>(listExamDto, HttpStatus.OK);
		} catch (TeacherNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping(value = "/add")
	public ResponseEntity<Teacher> addTeacher(@RequestBody TeacherDto teacherDto) {
		try {
			Teacher teacher = teacherService.addTeacher(teacherDto);
			return new ResponseEntity<>(teacher, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PutMapping(value = "/update")
	public ResponseEntity<Teacher> updateTeacher(@RequestBody TeacherDto teacherDto) {
		try {
			Teacher teacher = teacherService.updateTeacher(teacherDto);
			return new ResponseEntity<>(teacher, HttpStatus.OK);
		} catch (TeacherNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteTeacher(@PathVariable("id") int id) {
		teacherService.deleteTeacher(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
