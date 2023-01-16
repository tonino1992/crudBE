package it.crud.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.Course;
import it.crud.demo.domain.Teacher;
import it.crud.demo.dto.CourseDto;
import it.crud.demo.dto.CourseJoinTeacherDto;
import it.crud.demo.exceptions.CourseNotFoundException;
import it.crud.demo.repositories.CourseRepo;

@Service
public class CourseService {

	private CourseRepo courseRepo;
	private TeacherService teacherService;

	@Autowired
	public CourseService(CourseRepo courseRepo, TeacherService teacherService) {
		this.courseRepo = courseRepo;
		this.teacherService = teacherService;
	}

	public Course getCourseDaoById(int id) {
		return courseRepo.findById(id).orElseThrow(() -> new CourseNotFoundException("Corso non trovato"));
	}

	public List<CourseJoinTeacherDto> getAllCourses() {
		List<CourseJoinTeacherDto> listDto = new ArrayList<>();
		List<Course> courses = courseRepo.findAll();

		for (Course course : courses) {
			CourseJoinTeacherDto courseDto = new CourseJoinTeacherDto();
			courseDto.setId(course.getId());
			courseDto.setSubject(course.getSubject());
			courseDto.setHourAmount(course.getHourAmount());
			courseDto.setTeacherName(course.getTeacher().getName());
			courseDto.setTeacherSurname(course.getTeacher().getSurname());
			listDto.add(courseDto);
		}

		return listDto;
	}

	public Course addCourse(CourseDto courseDto) {
		Course course = new Course();
		Teacher teacher = teacherService.getTeacherDaoById(courseDto.getTeacherId());
		course.setSubject(courseDto.getSubject());
		course.setHourAmount(courseDto.getHourAmount());
		course.setTeacher(teacher);

		return courseRepo.save(course);
	}

	public CourseJoinTeacherDto findCourseById(int id) {
		Course course = this.getCourseDaoById(id);
		CourseJoinTeacherDto courseDto = new CourseJoinTeacherDto();
		courseDto.setId(course.getId());
		courseDto.setSubject(course.getSubject());
		courseDto.setHourAmount(course.getHourAmount());
		courseDto.setTeacherName(course.getTeacher().getName());
		courseDto.setTeacherSurname(course.getTeacher().getSurname());

		return courseDto;
	}

}
