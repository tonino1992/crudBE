package it.crud.demo.services;

import java.util.ArrayList;
import java.util.List;

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

	public StudentCourse studentCourseIscription(StudentCourseDto studentCourseDto) throws StudentCourseAlreadyExistsException {

	    Student student = studentService.getStudentDaoById(studentCourseDto.getStudentId());
	    Course course = courseService.getCourseDaoById(studentCourseDto.getCourseId());
	    StudentCourseId id = new StudentCourseId(student,course);
	    
	    if(studentCourseRepo.findById(id).isPresent()) {
	        throw new StudentCourseAlreadyExistsException("Sei già iscritto a questo corso");
	    }
	    StudentCourse studentCourse = new StudentCourse(id);
	    return studentCourseRepo.save(studentCourse);
	}


	public List<CourseJoinTeacherDto> getCoursesByStudent(Student student) {
		List<CourseJoinTeacherDto> courses = new ArrayList<>();

		for (StudentCourse studentCourse : student.getCourses()) {
			Course course = studentCourse.getId().getCourse();
			CourseJoinTeacherDto courseDto = new CourseJoinTeacherDto();
			courseDto.setId(course.getId());
			courseDto.setHourAmount(course.getHourAmount());
			courseDto.setSubject(course.getSubject());
			courseDto.setTeacherName(course.getTeacher().getName());
			courseDto.setTeacherSurname(course.getTeacher().getSurname());
			courses.add(courseDto);
		}
		return courses;
	}

	public List<StudentDto> getStudentsbyCourse(int id) {

		Course course = this.courseService.getCourseDaoById(id);

		List<StudentDto> students = new ArrayList<>();

		for (StudentCourse studentCourse : course.getStudents()) {
			Student student = studentCourse.getId().getStudent();
			StudentDto studentDto = new StudentDto();
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
