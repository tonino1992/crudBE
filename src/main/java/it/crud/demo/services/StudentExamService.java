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

	public List<ExamJoinCourseDto> getExamsToDoByStudent(Student student) {
		return getExamsByStudent(student.getExams(), e -> e.getVote() == 0);
	}

	public List<ExamJoinCourseDto> getExamsDoneByStudent(Student student) {
		 return getExamsByStudent(student.getExams(), e -> e.getVote() > 0);
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
		StudentExam studentExam = new StudentExam();
		studentExam.setBookingDate(studentExamDto.getBookingDate());
		studentExam.setVote(studentExamDto.getVote());
		studentExam.getId().setExam(examService.getExamDaoById(studentExamDto.getExamId()));
		studentExam.getId().setStudent(studentService.getStudentDaoById(studentExamDto.getStudentId()));
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
			studentDto.setAge(student.getAge());
			studentDto.setUserId(student.getUserId().getUserId());
			students.add(studentDto);
		}
		return students;
	}

}
