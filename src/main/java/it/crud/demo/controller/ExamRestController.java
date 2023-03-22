package it.crud.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.crud.demo.dto.ExamDto;
import it.crud.demo.dto.ExamJoinCourseDto;
import it.crud.demo.exceptions.CourseNotFoundException;
import it.crud.demo.exceptions.ExamNotFoundException;
import it.crud.demo.services.ExamService;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(value = "/exams")
public class ExamRestController {

	private ExamService examService;

	public ExamRestController(ExamService examService) {
		super();
		this.examService = examService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<ExamJoinCourseDto>> getAllExams() {
		try {
			List<ExamJoinCourseDto> exams = examService.getAllExams();
			return new ResponseEntity<>(exams, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ExamJoinCourseDto> getExamById(@PathVariable("id") int id) {
		try {
			ExamJoinCourseDto examDto = examService.getExamById(id);
			return new ResponseEntity<>(examDto, HttpStatus.OK);
		} catch (ExamNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/add")
	  public ResponseEntity<?> addExam(@RequestBody ExamDto examDto) {
        try {
            examService.addExam(examDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	@PutMapping(value = "/update")
	 public ResponseEntity<?> updateExam(@RequestBody ExamDto examDto) {
        try {
            examService.updateExam(examDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ExamNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteExam(@PathVariable("id") int id) {
		examService.deleteExam(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
