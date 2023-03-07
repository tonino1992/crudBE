package it.crud.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.Exam;
import it.crud.demo.domain.Teacher;
import it.crud.demo.domain.User;
import it.crud.demo.dto.CourseDto;
import it.crud.demo.dto.ExamJoinCourseDto;
import it.crud.demo.dto.TeacherDto;
import it.crud.demo.dto.UserDto;
import it.crud.demo.exceptions.TeacherNotFoundException;
import it.crud.demo.repositories.TeacherRepo;

@Service
public class TeacherService {

	private TeacherRepo teacherRepo;
	private UserService userService;

	@Autowired
	public TeacherService(TeacherRepo teacherRepo, UserService userService) {
		this.teacherRepo = teacherRepo;
		this.userService = userService;
	}

	public List<TeacherDto> getAllTeachers() {
		List<Teacher> teachers = teacherRepo.findAll();
		return teachers.stream().map(teacher -> {
			TeacherDto teacherDto = new TeacherDto();
			teacherDto.setId(teacher.getId());
			teacherDto.setName(teacher.getName());
			teacherDto.setSurname(teacher.getSurname());
			teacherDto.setDateOfBirth(teacher.getDateOfBirth());
			teacherDto.setUserId(teacher.getUserId().getUserId());
			return teacherDto;
		}).collect(Collectors.toList());
	}

	public Teacher getTeacherDaoById(int id) {
		return this.teacherRepo.findTeacherById(id)
				.orElseThrow(() -> new TeacherNotFoundException("Docente non trovato"));
	}

	public TeacherDto getTeacherById(int id) {
		Teacher teacher = this.getTeacherDaoById(id);
		TeacherDto teacherDto = new TeacherDto();

		teacherDto.setId(teacher.getId());
		teacherDto.setName(teacher.getName());
		teacherDto.setSurname(teacher.getSurname());
		teacherDto.setDateOfBirth(teacher.getDateOfBirth());
		teacherDto.setUserId(teacher.getUserId().getUserId());

		return teacherDto;
	}

	public List<CourseDto> getTeacherCourses(int id) {
		Teacher teacher = this.getTeacherDaoById(id);
		return teacher.getCourses().stream().map(course -> {
			CourseDto courseDto = new CourseDto();
			courseDto.setId(course.getId());
			courseDto.setSubject(course.getSubject());
			courseDto.setDone(course.isDone());
			courseDto.setHourAmount(course.getHourAmount());
			courseDto.setTeacherId(course.getTeacher().getId());
			return courseDto;
		}).collect(Collectors.toList());
	}

	public List<ExamJoinCourseDto> getTeacherExams(int id) {
		Teacher teacher = this.getTeacherDaoById(id);
		return teacher.getCourses().stream().filter(course -> course.getExam() != null).map(course -> {
			Exam exam = course.getExam();
			ExamJoinCourseDto examDto = new ExamJoinCourseDto();
			examDto.setId(exam.getId());
			examDto.setClassroom(exam.getClassroom());
			examDto.setDay(exam.getDay());
			examDto.setHour(exam.getHour());
			examDto.setDone(exam.isDone());
			examDto.setCourseSubject(exam.getCourse().getSubject());
			return examDto;
		}).collect(Collectors.toList());
	}

	@Transactional
	public Teacher addTeacher(TeacherDto teacherDto) {
		if (userService.userExists(teacherDto.getUserId())) {
			throw new IllegalArgumentException("Nome utente già in uso");
		} else {
			UserDto userDto = new UserDto();
			userDto.setUserId(teacherDto.getUserId());
			userDto.setEmail(teacherDto.getEmail());
			userDto.setPassword(teacherDto.getPassword());
			userDto.setRole(teacherDto.getRole());
			User userSaved = userService.addOrUpdateUser(userDto);

			Teacher teacher = new Teacher();
			teacher.setName(teacherDto.getName());
			teacher.setSurname(teacherDto.getSurname());
			teacher.setDateOfBirth(teacherDto.getDateOfBirth());
			teacher.setUserId(userSaved);

			return teacherRepo.save(teacher);
		}
	}

	public Teacher updateTeacher(TeacherDto teacherDto) {
		Teacher teacher = getTeacherDaoById(teacherDto.getId());
		teacher.setName(teacherDto.getName());
		teacher.setSurname(teacherDto.getSurname());
		teacher.setDateOfBirth(teacherDto.getDateOfBirth());
		teacher.setUserId(userService.findUserDaoById(teacherDto.getUserId()));
		return teacherRepo.save(teacher);
	}

	@Transactional
	public void deleteTeacher(int id) {
		String userId = this.getTeacherDaoById(id).getUserId().getUserId();
		teacherRepo.deleteById(id);
		userService.deleteUser(userId);
	}

}
