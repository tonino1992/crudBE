package it.crud.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
	    return studentRepo.findAll().stream()
	        .map(student -> {
	            StudentDto studentDto = new StudentDto();
	            studentDto.setId(student.getId());
	            studentDto.setName(student.getName());
	            studentDto.setSurname(student.getSurname());
	            studentDto.setDateOfBirth(student.getDateOfBirth());
	            studentDto.setUserId(student.getUserId().getUserId());
	            return studentDto;
	        })
	        .collect(Collectors.toList());
	}

	public StudentDto findStudentById(int id) {
	    Student student = this.getStudentDaoById(id);
	    StudentDto studentDto = new StudentDto();
	    studentDto.setId(student.getId());
	    studentDto.setName(student.getName());
	    studentDto.setSurname(student.getSurname());
	    studentDto.setDateOfBirth(student.getDateOfBirth());
	    studentDto.setUserId(student.getUserId().getUserId());

	    return studentDto;
	}

	public Student updateStudent(StudentDto studentDto) {
	    Student student = this.getStudentDaoById(studentDto.getId());
	    student.setName(studentDto.getName());
	    student.setSurname(studentDto.getSurname());
	    student.setDateOfBirth(studentDto.getDateOfBirth());
	    student.setUserId(userService.findUserDaoById(studentDto.getUserId()));
	    return studentRepo.save(student);
	}


	@Transactional
	public Student addStudent(StudentDto studentDto) {
		if (userService.userExists(studentDto.getUserId())) {
			
			throw new IllegalArgumentException("Nome utente già in uso");
		} else {
			System.out.println(userService.userExists(studentDto.getUserId()));
			UserDto userDto = new UserDto();
			userDto.setUserId(studentDto.getUserId());
			userDto.setEmail(studentDto.getEmail());
			userDto.setPassword(studentDto.getPassword());
			userDto.setRole(studentDto.getRole());
			User userSaved = userService.addOrUpdateUser(userDto);
			Student student = new Student();
			student.setName(studentDto.getName());
			student.setSurname(studentDto.getSurname());
			student.setDateOfBirth(studentDto.getDateOfBirth());
			student.setUserId(userSaved);

			return studentRepo.save(student);
		}
	}

	@Transactional
	public void deleteStudent(int id) {
		String userId = this.getStudentDaoById(id).getUserId().getUserId();
		studentRepo.deleteById(id);
		userService.deleteUser(userId);
	}
	
}
