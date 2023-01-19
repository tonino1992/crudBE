package it.crud.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.Student;
import it.crud.demo.domain.Teacher;
import it.crud.demo.domain.User;
import it.crud.demo.domain.enums.UserRole;
import it.crud.demo.dto.PersonDto;
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
	
	public User userExsits (String userId) {
		return userRepo.findByUserId(userId);
	}

	public User addOrUpdateUser(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setPassword(userDto.getPassword());
		user.setRole(userDto.getRole());
		return userRepo.save(user);
	}

	public void deleteUser(String userId) {
		userRepo.deleteById(userId);
	}

	public PersonDto validateUser(String userId, String password) {
	    User user = userRepo.findByUserId(userId);
	    if (user != null && user.getPassword().equals(password)) {
	        if (user.getRole().equals(UserRole.STUDENT)) {
	            Student student = user.getStudent();
	            return new StudentDto(student.getId(), student.getUserId().getUserId(), student.getName(), student.getSurname(), student.getAge());
	        } else if (user.getRole().equals(UserRole.TEACHER)) {
	            Teacher teacher = user.getTeacher();
	            return new TeacherDto(teacher.getId(), teacher.getUserId().getUserId(), teacher.getName(), teacher.getSurname(), teacher.getAge());
	        }
	    }
	    throw new UserNotFoundException("utente non valido");
	}


}
