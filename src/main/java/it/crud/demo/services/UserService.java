package it.crud.demo.services;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

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
import it.crud.demo.dto.ChangeUserIdDto;
import it.crud.demo.dto.PersonDto;
import it.crud.demo.dto.StudentDto;
import it.crud.demo.dto.TeacherDto;
import it.crud.demo.dto.UserDto;
import it.crud.demo.exceptions.IllegalPasswordException;
import it.crud.demo.exceptions.UserIdAlreadyExsistException;
import it.crud.demo.exceptions.UserNotFoundException;
import it.crud.demo.repositories.ResetPasswordTokenRepo;
import it.crud.demo.repositories.StudentRepo;
import it.crud.demo.repositories.TeacherRepo;
import it.crud.demo.repositories.UserRepo;

@Service
public class UserService implements UserDetailsService {

	private UserRepo userRepo;
	private JavaMailSender javaMailSender;
	private ResetPasswordTokenRepo resetPasswordTokenRepo;
	private StudentRepo studentRepo;
	private TeacherRepo teacherRepo;

	@Autowired
	public UserService(UserRepo userRepo, JavaMailSender javaMailSender, ResetPasswordTokenRepo resetPasswordTokenRepo,
			TeacherRepo teacherRepo, StudentRepo studentRepo) {
		super();
		this.userRepo = userRepo;
		this.javaMailSender = javaMailSender;
		this.resetPasswordTokenRepo = resetPasswordTokenRepo;
		this.studentRepo = studentRepo;
		this.teacherRepo = teacherRepo;
	}

	public User findUserDaoById(String userId) {
		return userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("Utente non trovato"));
	}

	public boolean userExists(String userId) {
		System.out.println(userRepo.findByUserId(userId));
		return userRepo.findByUserId(userId) != null;
	}

	public User addUser(UserDto userDto) {
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setEmail(userDto.getEmail());

		String password = userDto.getPassword();
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setPassword(hashedPassword);

		user.setRole(userDto.getRole());

		return userRepo.save(user);
	}
	
	public User updateUser(UserDto userDto) {
	    User user = this.findUserDaoById(userDto.getUserId());
	    if (userDto.getEmail() != null) {
	        user.setEmail(userDto.getEmail());
	    }
	    if (userDto.getPassword() != null) {
	        String password = userDto.getPassword();
	        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
	        user.setPassword(hashedPassword);
	    }
	    if (userDto.getRole() != null) {
	        user.setRole(userDto.getRole());
	    }
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
			studentDto.setUserId(student.getUserId().getUserId());
			studentDto.setEmail(student.getUserId().getEmail());
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
			teacherDto.setUserId(teacher.getUserId().getUserId());
			teacherDto.setEmail(teacher.getUserId().getEmail());
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
					+ "Il link è valido per 7 giorni a partire dalla data di richiesta.<br><br>"
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

	@Transactional
	public void changeUserId(ChangeUserIdDto userIds) {

		User oldUser = this.findUserDaoById(userIds.getOldUserId());
		User newUser = new User();

		if (userRepo.findById(userIds.getNewUserId()).isPresent()) {
			throw new UserIdAlreadyExsistException("User already exsist!");
		}

		newUser.setUserId(userIds.getNewUserId());
		newUser.setPassword(oldUser.getPassword());
		newUser.setEmail(oldUser.getEmail());
		newUser.setRole(oldUser.getRole());

		userRepo.saveAndFlush(newUser);

		Student student = new Student();
		Teacher teacher = new Teacher();
		if (oldUser.getRole() == UserRole.STUDENT) {
			student = oldUser.getStudent();
			student.setUserId(newUser);
			this.studentRepo.save(student);
		} else {
			teacher = oldUser.getTeacher();
			teacher.setUserId(newUser);
			this.teacherRepo.save(teacher);
		}

		userRepo.delete(oldUser);	
		 

	}

}
