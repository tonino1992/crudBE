package it.crud.demo.services;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
import it.crud.demo.exceptions.IllegalPasswordException;
import it.crud.demo.exceptions.UserNotFoundException;
import it.crud.demo.repositories.ResetPasswordTokenRepo;
import it.crud.demo.repositories.UserRepo;

@Service
public class UserService implements UserDetailsService {

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

	public boolean userExists(String userId) {
		System.out.println(userRepo.findByUserId(userId));
		return userRepo.findByUserId(userId) != null;
	}

	public User addOrUpdateUser(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setEmail(userDto.getEmail());

		String password = userDto.getPassword();
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setPassword(hashedPassword);

		user.setRole(userDto.getRole());

		return userRepo.save(user);
	}

	public void deleteUser(String userId) {
		userRepo.deleteById(userId);
	}

	public PersonDto validateUser(String userId, String password) {
		User user = this.findUserDaoById(userId);

		if (!BCrypt.checkpw(password, user.getPassword())) {
			throw new IllegalPasswordException("Password errata!");
		}

		if (user.getRole().equals(UserRole.STUDENT)) {
			StudentDto studentDto = new StudentDto();
			Student student = user.getStudent();

			studentDto.setId(student.getId());
			studentDto.setUserId(student.getUserId().getUserId());
			studentDto.setName(student.getName());
			studentDto.setSurname(student.getSurname());
			studentDto.setDateOfBirth(student.getDateOfBirth());

			return studentDto;
		}

		if (user.getRole().equals(UserRole.TEACHER)) {
			TeacherDto teacherDto = new TeacherDto();
			Teacher teacher = user.getTeacher();

			teacherDto.setId(teacher.getId());
			teacherDto.setUserId(teacher.getUserId().getUserId());
			teacherDto.setName(teacher.getName());
			teacherDto.setSurname(teacher.getSurname());
			teacherDto.setDateOfBirth(teacher.getDateOfBirth());

			return teacherDto;
		}

		throw new IllegalStateException("Tipo di utente non supportato");
	}

	public void recuperaPassword(String userId) throws MessagingException {
		// Verifica se l'email esiste nel sistema
		User user = this.findUserDaoById(userId);

		// Crea un token univoco per reimpostare la password
		UUID uuId = UUID.randomUUID();
		String resetToken = uuId.toString();

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
					+ "Il link ?? valido per 7 giorni a partire dalla data di richiesta.<br><br>"
					+ "Saluti,<br>Il team di supporto", true);
		} catch (MessagingException e) {
			throw new MessagingException();
		}

		javaMailSender.send(message);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUserId(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}

}
