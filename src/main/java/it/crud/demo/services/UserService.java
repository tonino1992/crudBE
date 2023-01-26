package it.crud.demo.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import it.crud.demo.domain.ResetPasswordToken;
import it.crud.demo.domain.Student;
import it.crud.demo.domain.Teacher;
import it.crud.demo.domain.User;
import it.crud.demo.domain.enums.UserRole;
import it.crud.demo.dto.PersonDto;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.dto.TeacherDto;
import it.crud.demo.dto.UserDto;
import it.crud.demo.exceptions.UserNotFoundException;
import it.crud.demo.repositories.ResetPasswordTokenRepo;
import it.crud.demo.repositories.UserRepo;

@Service
public class UserService {

	private UserRepo userRepo;
	private JavaMailSender javaMailSender;
	private ResetPasswordTokenRepo resetPasswordTokenRepo;

	@Autowired
	public UserService(UserRepo userRepo, JavaMailSender javaMailSender,
			ResetPasswordTokenRepo resetPasswordTokenRepo) {
		super();
		this.userRepo = userRepo;
		this.javaMailSender = javaMailSender;
		this.resetPasswordTokenRepo = resetPasswordTokenRepo;
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

	public void recuperaPassword(String userId) {
		// Verifica se l'email esiste nel sistema
		User user = userRepo.findByUserId(userId);
		if (user == null) {
			throw new UserNotFoundException("L'utente con l'email specificata non esiste");
		}
		// Crea un token univoco per reimpostare la password
		SecureRandom random = new SecureRandom();
		byte[] token = new byte[24];
		random.nextBytes(token);
		String resetToken = Base64.getEncoder().encodeToString(token);

		// Crea una nuova istanza di ResetPasswordToken
		ResetPasswordToken passwordToken = new ResetPasswordToken(resetToken, user);
		resetPasswordTokenRepo.save(passwordToken);

		// Crea il link per reimpostare la password
		String resetLink = "http://localhost:4200/reset-password/" + resetToken;

		// Invia l'email all'utente con il link per reimpostare la password
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("acampanale@studenti.apuliadigitalmaker.it");
			// helper.setTo(user.getEmail());
			helper.setTo("acampanale@studenti.apuliadigitalmaker.it");
			helper.setSubject("Recupero password");
			helper.setText("Ciao " + user.getUserId() + ",<br><br>"
					+ "Abbiamo ricevuto una richiesta di recupero password per il tuo account.<br>"
					+ "Per reimpostare la password, clicca sul seguente link:<br>" + "<a href='" + resetLink + "'>"
					+ resetLink + "</a><br><br>" + "Se non hai richiesto il recupero password, ignora questa email.<br>"
					+ "Il link è valido per 7 giorni a partire dalla data di richiesta.<br><br>"
					+ "Saluti,<br>Il team di supporto", true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		javaMailSender.send(message);

	}

}
