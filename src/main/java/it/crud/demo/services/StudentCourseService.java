package it.crud.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.Course;
import it.crud.demo.domain.Student;
import it.crud.demo.domain.StudentCourse;
import it.crud.demo.domain.id.StudentCourseId;
import it.crud.demo.dto.CourseJoinTeacherDto;
import it.crud.demo.dto.StudentCourseDto;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.exceptions.StudentCourseAlreadyExistsException;
import it.crud.demo.repositories.StudentCourseRepo;

@Service
public class StudentCourseService {

	private StudentCourseRepo studentCourseRepo;
	private StudentService studentService;
	private CourseService courseService;

	@Autowired
	public StudentCourseService(StudentCourseRepo studentCourseRepo, StudentService studentService,
			CourseService courseService) {
		this.studentCourseRepo = studentCourseRepo;
		this.studentService = studentService;
		this.courseService = courseService;
	}

	public StudentCourse enrollStudentInCourse(StudentCourseDto studentCourseDto)
			throws StudentCourseAlreadyExistsException {
		Student student = studentService.getStudentDaoById(studentCourseDto.getStudentId());
		Course course = courseService.getCourseDaoById(studentCourseDto.getCourseId());
		StudentCourseId id = new StudentCourseId(student, course);

		if (studentCourseRepo.existsById(id)) {
			throw new StudentCourseAlreadyExistsException("Sei già iscritto a questo corso");
		}

		return studentCourseRepo.save(new StudentCourse(id));
	}

	public List<CourseJoinTeacherDto> getCoursesByStudent(int id) {
	    Student student = studentService.getStudentDaoById(id);
	    List<CourseJoinTeacherDto> courses = student.getCourses().stream().map(studentCourse -> {
	        Course course = studentCourse.getId().getCourse();
	        CourseJoinTeacherDto courseDto = new CourseJoinTeacherDto();
	        courseDto.setId(course.getId());
	        courseDto.setHourAmount(course.getHourAmount());
	        courseDto.setSubject(course.getSubject());
	        courseDto.setDone(course.isDone());
	        courseDto.setTeacherId(course.getTeacher().getId());
	        courseDto.setTeacherName(course.getTeacher().getName());
	        courseDto.setTeacherSurname(course.getTeacher().getSurname());
	        return courseDto;
	    }).collect(Collectors.toList());
	    return courses;
	}


	public List<StudentDto> getStudentsByCourse(int id) {
		Course course = courseService.getCourseDaoById(id);
		return course.getStudents().stream().map(StudentCourse::getId).map(StudentCourseId::getStudent).map(student -> {
			StudentDto studentDto = new StudentDto();
			studentDto.setId(student.getId());
			studentDto.setName(student.getName());
			studentDto.setSurname(student.getSurname());
			studentDto.setUserId(student.getUserId().getUserId());
			return studentDto;
		}).collect(Collectors.toList());

	}

}
