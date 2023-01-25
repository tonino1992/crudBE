package it.crud.demo.services;

import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
	private JavaMailSender javaMailSender;

	@Autowired
	public UserService(UserRepo userRepo, JavaMailSender javaMailSender) {
		super();
		this.userRepo = userRepo;
		this.javaMailSender = javaMailSender;
	}

	public User findUserDaoById(String userId) {
		return userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
	}

	public User userExsits(String userId) {
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
				return new StudentDto(student.getId(), student.getUserId().getUserId(), student.getName(),
						student.getSurname(), student.getDateOfBirth());
			} else if (user.getRole().equals(UserRole.TEACHER)) {
				Teacher teacher = user.getTeacher();
				return new TeacherDto(teacher.getId(), teacher.getUserId().getUserId(), teacher.getName(),
						teacher.getSurname(), teacher.getDateOfBirth());
			}
		}
		throw new UserNotFoundException("utente non valido");
	}

	public void recuperaPassword(String email) throws UnsupportedEncodingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		String text = "<a href= \"http://localhost:4200/login\"";
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			if (helper != null) {
				helper.setFrom("acampanale@studenti.apuliadigitalmaker.it");
				helper.setTo("acampanale@studenti.apuliadigitalmaker.it");
				helper.setSubject("Recupera password");
				helper.setText("clicca per recuperare" + text, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		javaMailSender.send(message);
	}

}
