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

import it.crud.demo.domain.Teacher;
import it.crud.demo.dto.CourseDto;
import it.crud.demo.dto.ExamJoinCourseDto;
import it.crud.demo.dto.TeacherDto;
import it.crud.demo.services.TeacherService;

@RestController
@RequestMapping(value = "/teachers")
public class TeacherRestController {

	private TeacherService teacherService;

	public TeacherRestController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
	@CrossOrigin
	@GetMapping(value = "/all")
	public ResponseEntity<List<TeacherDto>> getAllTeachers() {
		List<TeacherDto> listDto = teacherService.getAllTeacher();
		return new ResponseEntity<>(listDto, HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public ResponseEntity<TeacherDto> getTeacherById(@PathVariable("id") int id) {
		TeacherDto teacherDto = teacherService.getTeacherById(id);
		return new ResponseEntity<>(teacherDto, HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping(value = "/{id}/courses")
	public ResponseEntity<List<CourseDto>> getTeacherCourses(@PathVariable("id") int id) {
		List<CourseDto> listCourseDto = teacherService.getTeacherCourses(id);
		return new ResponseEntity<>(listCourseDto, HttpStatus.OK);
	}
	@CrossOrigin
	@GetMapping(value = "/{id}/exams")
	public ResponseEntity<List<ExamJoinCourseDto>> getTeacherExams(@PathVariable("id") int id) {
		List<ExamJoinCourseDto> listExamDto = teacherService.getTeacherExams(id);
		return new ResponseEntity<>(listExamDto, HttpStatus.OK);
	}
	@CrossOrigin
	@PostMapping(value = "/add")
	public ResponseEntity<Teacher> addTeacher(@RequestBody TeacherDto teacherDto) {
		Teacher teacher = teacherService.addTeacher(teacherDto);
		return new ResponseEntity<>(teacher, HttpStatus.CREATED);
	}
	@CrossOrigin
	@PutMapping(value = "/update")
	public ResponseEntity<Teacher> updateTeacher(@RequestBody TeacherDto teacherDto){
		Teacher teacher = teacherService.updateTeacher(teacherDto);				
		return new ResponseEntity<>(teacher, HttpStatus.OK);
	}
	@CrossOrigin
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteTeacher(@PathVariable("id") int id) {
		teacherService.deleteTeacher(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
