package it.crud.demo.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.Course;
import it.crud.demo.domain.Exam;
import it.crud.demo.domain.Student;
import it.crud.demo.domain.StudentCourse;
import it.crud.demo.domain.StudentExam;
import it.crud.demo.domain.Teacher;
import it.crud.demo.domain.id.StudentExamId;
import it.crud.demo.dto.ExamJoinCourseDto;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.dto.StudentExamDto;
import it.crud.demo.exceptions.StudentExamAlreadyBookedException;
import it.crud.demo.repositories.StudentExamRepo;

@Service
public class StudentExamService {

	private StudentExamRepo studentExamRepo;
	private StudentService studentService;
	private ExamService examService;

	@Autowired
	public StudentExamService(StudentExamRepo studentExamRepo, StudentService studentService, ExamService examService) {
		this.studentExamRepo = studentExamRepo;
		this.studentService = studentService;
		this.examService = examService;
	}

	public StudentExam studentExamBooking(StudentExamDto studentExamDto) throws StudentExamAlreadyBookedException {
		Student student = studentService.getStudentDaoById(studentExamDto.getStudentId());
		Exam exam = examService.getExamDaoById(studentExamDto.getExamId());
		StudentExamId id = new StudentExamId(student, exam);
		if (studentExamRepo.findById(id).isPresent()) {
			throw new StudentExamAlreadyBookedException("Hai già prenotato questo esame");
		}
		StudentExam studentExam = new StudentExam();
		studentExam.setId(id);
		studentExam.setBookingDate(LocalDate.now());
		studentExam.setVote(0);
		return studentExamRepo.save(studentExam);
	}

	public List<ExamJoinCourseDto> getExamsToDoByStudent(int id) {
		Student student = studentService.getStudentDaoById(id);
		return getExamsByStudent(student.getExams(), e -> !e.getId().getExam().isDone());
	}

	public List<ExamJoinCourseDto> getExamsDoneByStudent(int id) {
		Student student = studentService.getStudentDaoById(id);
		return getExamsByStudent(student.getExams(), e -> e.getId().getExam().isDone());
	}

	private List<ExamJoinCourseDto> getExamsByStudent(List<StudentExam> exams, Predicate<StudentExam> filter) {
		return exams.stream().filter(filter).map(studentExam -> {
			Exam exam = studentExam.getId().getExam();
			Course examCourse = exam.getCourse();
			Teacher examTeacher = examCourse.getTeacher();
			ExamJoinCourseDto examDto = new ExamJoinCourseDto();
			examDto.setId(exam.getId());
			examDto.setClassroom(exam.getClassroom());
			examDto.setDay(exam.getDay());
			examDto.setHour(exam.getHour());
			examDto.setCourseSubject(examCourse.getSubject());
			if (filter.test(studentExam)) {
				examDto.setVote(studentExam.getVote());
			}
			examDto.setTeacherName(examTeacher.getName());
			examDto.setTeacherSurname(examTeacher.getSurname());
			return examDto;
		}).collect(Collectors.toList());
	}

	public StudentExam updateStudentExam(StudentExamDto studentExamDto) {
		StudentExam studentExam = studentExamRepo
				.findById(new StudentExamId(studentService.getStudentDaoById(studentExamDto.getStudentId()),
						examService.getExamDaoById(studentExamDto.getExamId())))
				.orElseThrow(() -> new IllegalArgumentException("Lo studente non ha prenotato questo esame"));
		studentExam.setVote(studentExamDto.getVote());
		return studentExamRepo.save(studentExam);
	}

	public List<StudentDto> getStudentsByExam(int id) {
		List<StudentDto> students = new ArrayList<>();
		Exam exam = this.examService.getExamDaoById(id);

		for (StudentCourse studentCourse : exam.getCourse().getStudents()) {
			StudentDto studentDto = new StudentDto();
			Student student = studentCourse.getId().getStudent();
			studentDto.setId(student.getId());
			studentDto.setName(student.getName());
			studentDto.setSurname(student.getSurname());
			studentDto.setDateOfBirth(student.getDateOfBirth());
			studentDto.setUserId(student.getUserId().getUserId());
			students.add(studentDto);
		}
		return students;
	}

	public List<StudentExamDto> getStudentExamsByExam(int id) {
	    List<StudentExam> studentExamList = this.studentExamRepo.findAllByExamId(id);
	    return studentExamList.stream().map(se -> {
	        StudentExamDto studentExamDto = new StudentExamDto();
	        studentExamDto.setStudentId(se.getId().getStudent().getId());
	        studentExamDto.setStudentName(se.getId().getStudent().getName());
	        studentExamDto.setStudentSurname(se.getId().getStudent().getSurname());
	        studentExamDto.setExamId(se.getId().getExam().getId());
	        studentExamDto.setBookingDate(se.getBookingDate());
	        studentExamDto.setVote(se.getVote());
	        return studentExamDto;
	    }).collect(Collectors.toList());
	}
	
	public List<StudentExamDto> getDoneExamsByStudent(int studentId) {
	    List<StudentExam> studentExams = studentExamRepo.findAllByStudentId(studentId);
	    return studentExams.stream().filter(se -> se.getId().getExam().isDone())
	            .map(se -> {
	                StudentExamDto seDto = new StudentExamDto();
	                seDto.setBookingDate(se.getBookingDate());
	                seDto.setVote(se.getVote());
	                seDto.setStudentId(se.getId().getStudent().getId());
	                seDto.setStudentName(se.getId().getStudent().getName());
	                seDto.setStudentSurname(se.getId().getStudent().getSurname());
	                seDto.setExamId(se.getId().getExam().getId());
	                seDto.setTeacherName(se.getId().getExam().getCourse().getTeacher().getName());
	                seDto.setTeacherSurname(se.getId().getExam().getCourse().getTeacher().getSurname());
	                seDto.setSubject(se.getId().getExam().getCourse().getSubject());
	                return seDto;
	            })
	            .collect(Collectors.toList());
	}


}
