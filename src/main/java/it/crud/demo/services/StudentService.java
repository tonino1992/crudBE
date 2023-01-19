package it.crud.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.Student;
import it.crud.demo.domain.User;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.dto.UserDto;
import it.crud.demo.exceptions.StudentNotFoundException;
import it.crud.demo.repositories.StudentRepo;

@Service
public class StudentService {

	private StudentRepo studentRepo;
	private UserService userService;

	@Autowired
	public StudentService(StudentRepo studentRepo, UserService userService) {
		super();
		this.studentRepo = studentRepo;
		this.userService = userService;
	}

	public Student getStudentDaoById(int id) {
		return studentRepo.findById(id).orElseThrow(() -> new StudentNotFoundException("Studente non trovato"));
	}

	public List<StudentDto> getAllStudents() {
		List<StudentDto> listDto = new ArrayList<StudentDto>();
		List<Student> students = studentRepo.findAll();

		for (Student student : students) {
			StudentDto studentDto = new StudentDto();
			studentDto.setId(student.getId());
			studentDto.setName(student.getName());
			studentDto.setSurname(student.getSurname());
			studentDto.setAge(student.getAge());
			studentDto.setUserId(student.getUserId().getUserId());

			listDto.add(studentDto);
		}

		return listDto;
	}

	public StudentDto findStudentById(int id) {
		Student student = this.getStudentDaoById(id);
		StudentDto studentDto = new StudentDto();
		studentDto.setId(student.getId());
		studentDto.setName(student.getName());
		studentDto.setSurname(student.getSurname());
		studentDto.setAge(student.getAge());
		studentDto.setUserId(student.getUserId().getUserId());

		return studentDto;
	}

	public Student updateStudent(StudentDto studentDto) {
		Student student = new Student();
		student.setId(studentDto.getId());
		student.setName(studentDto.getName());
		student.setSurname(studentDto.getSurname());
		student.setAge(studentDto.getAge());
		student.setUserId(userService.findUserDaoById(studentDto.getUserId()));

		return studentRepo.save(student);
	}

	public Student addStudent(StudentDto studentDto) {
		User user = userService.userExsits(studentDto.getUserId());
		if (user != null) {
			throw new IllegalArgumentException("Nome utente già in uso");
		} else {
			UserDto userDto = new UserDto();
			userDto.setUserId(studentDto.getUserId());
			userDto.setPassword(studentDto.getPassword());
			userDto.setRole(studentDto.getRole());
			User userSaved = userService.addOrUpdateUser(userDto);
			Student student = new Student();
			student.setName(studentDto.getName());
			student.setSurname(studentDto.getSurname());
			student.setAge(studentDto.getAge());
			student.setUserId(userSaved);

			return studentRepo.save(student);
		}
	}

	public void deleteStudent(int id) {
		studentRepo.deleteById(id);
	}

}
