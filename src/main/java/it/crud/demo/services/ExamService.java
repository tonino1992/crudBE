package it.crud.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.Course;
import it.crud.demo.domain.Exam;
import it.crud.demo.dto.ExamDto;
import it.crud.demo.dto.ExamJoinCourseDto;
import it.crud.demo.exceptions.ExamNotFoundException;
import it.crud.demo.repositories.CourseRepo;
import it.crud.demo.repositories.ExamRepo;

@Service
public class ExamService {

	private ExamRepo examRepo;
	private CourseService courseService;
	private CourseRepo courseRepo;

	@Autowired
	public ExamService(ExamRepo examRepo, CourseService courseService, CourseRepo courseRepo) {
		super();
		this.examRepo = examRepo;
		this.courseService = courseService;
		this.courseRepo = courseRepo;
	}

	public Exam getExamDaoById(int id) {
		return examRepo.findById(id).orElseThrow(() -> new ExamNotFoundException("Esame non trovato"));
	}

	public List<ExamJoinCourseDto> getAllExams() {
	    List<Exam> exams = examRepo.findAll();
	    return exams.stream().map(exam -> {
	        ExamJoinCourseDto examDto = new ExamJoinCourseDto();
	        examDto.setId(exam.getId());
	        examDto.setClassroom(exam.getClassroom());
	        examDto.setDay(exam.getDay());
	        examDto.setHour(exam.getHour());
	        examDto.setDone(exam.isDone());
	        examDto.setCourseId(exam.getCourse().getId());
	        examDto.setCourseSubject(exam.getCourse().getSubject());
	        examDto.setTeacherName(exam.getCourse().getTeacher().getName());
	        examDto.setTeacherSurname(exam.getCourse().getTeacher().getSurname());
	        return examDto;
	    }).collect(Collectors.toList());
	}
	
	public ExamJoinCourseDto getExamById(int id) {
		Exam exam = this.getExamDaoById(id);
		ExamJoinCourseDto examDto = new ExamJoinCourseDto();
		examDto.setId(exam.getId());
		examDto.setClassroom(exam.getClassroom());
		examDto.setDay(exam.getDay());
		examDto.setHour(exam.getHour());
		examDto.setDone(exam.isDone());
		examDto.setCourseId(exam.getCourse().getId());
		examDto.setCourseSubject(exam.getCourse().getSubject());
		examDto.setTeacherName(exam.getCourse().getTeacher().getName());
		examDto.setTeacherSurname(exam.getCourse().getTeacher().getSurname());

		return examDto;
	}

	public Exam updateExam(ExamDto examDto) {
		Exam exam = new Exam();
		exam.setId(examDto.getId());
		exam.setClassroom(examDto.getClassroom());
		exam.setCourse(courseService.getCourseDaoById(examDto.getCourseId()));
		exam.setDay(examDto.getDay());
		exam.setHour(examDto.getHour());
		exam.setDone(examDto.isDone());

		return examRepo.save(exam);
	}

	@Transactional
	public Exam addExam(ExamDto examDto) {
		Exam exam = new Exam();
		Course course = courseService.getCourseDaoById(examDto.getCourseId());
		exam.setClassroom(examDto.getClassroom());
		exam.setCourse(course);
		exam.setDay(examDto.getDay());
		exam.setDone(false);
		exam.setHour(examDto.getHour());
		course.setDone(true);
		this.courseRepo.save(course);
		return examRepo.save(exam);
	}

	public void deleteExam(int id) {
		Exam exam = this.getExamDaoById(id);
		examRepo.delete(exam);
	}
}
