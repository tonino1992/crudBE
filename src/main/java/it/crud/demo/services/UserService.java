package it.crud.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.Student;
import it.crud.demo.domain.Teacher;
import it.crud.demo.domain.User;
import it.crud.demo.domain.enums.UserRole;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.dto.TeacherDto;
import it.crud.demo.dto.UserDto;
import it.crud.demo.exceptions.UserNotFoundException;
import it.crud.demo.repositories.UserRepo;

@Service
public class UserService {

	private UserRepo userRepo;

	@Autowired
	public UserService(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}

	public User findUserDaoById(String userId) {
		return userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
	}

	public User addOrUpdateUser(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setPassword(userDto.getPassword());
		user.setRole(UserRole.valueOf(userDto.getRole()));
		return userRepo.save(user);
	}

	public void deleteUser(String userId) {
		userRepo.deleteById(userId);
	}

	public Object validateUser(String userId, String password) {
	    User user = userRepo.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
	    if (user != null && user.getPassword().equals(password)) {
	        if (user.getRole() == UserRole.STUDENT) {
	            // restituisci DTO per studente
	            Student student = user.getStudent();
	            StudentDto studentDto = new StudentDto();
	            studentDto.setId(student.getId());
	            studentDto.setName(student.getName());
	            studentDto.setSurname(student.getSurname());
	            studentDto.setAge(student.getAge());
	            studentDto.setUserId(student.getUserId().getUserId());
	            return studentDto;
	        } else if (user.getRole() == UserRole.TEACHER) {
	            // restituisci DTO per insegnante
	            Teacher teacher = user.getTeacher();
	            TeacherDto teacherDto = new TeacherDto();
	            teacherDto.setId(teacher.getId());
	            teacherDto.setName(teacher.getName());
	            teacherDto.setSurname(teacher.getSurname());
	            teacherDto.setAge(teacher.getAge());
	            teacherDto.setUserId(teacher.getUserId().getUserId());
	            return teacherDto;
	        }
	    }
		return user;
	}

}
