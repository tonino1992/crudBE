package it.crud.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.Exam;
import it.crud.demo.dto.ExamDto;
import it.crud.demo.exceptions.ExamNotFoundException;
import it.crud.demo.repositories.ExamRepo;

@Service
public class ExamService {

	private ExamRepo examRepo;
	private CourseService courseService;

	@Autowired
	public ExamService(ExamRepo examRepo, CourseService courseService) {
		super();
		this.examRepo = examRepo;
		this.courseService = courseService;
	}

	public Exam getExamDaoById(int id) {
		return examRepo.findById(id).orElseThrow(() -> new ExamNotFoundException("Esame non trovato"));
	}

	public List<ExamDto> getAllExams() {
		List<ExamDto> listDto = new ArrayList<ExamDto>();
		List<Exam> exams = examRepo.findAll();

		for (Exam exam : exams) {
			ExamDto examDto = new ExamDto();
			examDto.setId(exam.getId());
			examDto.setClassroom(exam.getClassroom());
			examDto.setCourseId(exam.getCourse().getId());
			examDto.setDay(exam.getDay());
			examDto.setHour(exam.getHour());

			listDto.add(examDto);
		}

		return listDto;
	}

	public ExamDto getExamById(int id) {
		Exam exam = this.getExamDaoById(id);
		ExamDto examDto = new ExamDto();
		examDto.setId(exam.getId());
		examDto.setClassroom(exam.getClassroom());
		examDto.setCourseId(exam.getCourse().getId());
		examDto.setDay(exam.getDay());
		examDto.setHour(exam.getHour());

		return examDto;
	}

	public Exam updateExam(ExamDto examDto) {
		Exam exam = new Exam();
		exam.setId(examDto.getId());
		exam.setClassroom(examDto.getClassroom());
		exam.setCourse(courseService.getCourseDaoById(examDto.getCourseId()));
		exam.setDay(examDto.getDay());
		exam.setHour(examDto.getHour());

		return examRepo.save(exam);
	}

	public Exam addExam(ExamDto examDto) {
		Exam exam = new Exam();
		exam.setClassroom(examDto.getClassroom());
		exam.setCourse(courseService.getCourseDaoById(examDto.getCourseId()));
		exam.setDay(examDto.getDay());
		exam.setHour(examDto.getHour());

		return examRepo.save(exam);
	}

	public void deleteExam(int id) {
		Exam exam = this.getExamDaoById(id);
		examRepo.delete(exam);
	}
}
