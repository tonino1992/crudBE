package it.crud.demo.domain;

import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "exams")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DROIT_SEQ")
	@SequenceGenerator(name = "DROIT_SEQ", sequenceName = "DROIT_ACCEES_SEQ", allocationSize = 1, initialValue = 1)
	@Column(nullable = false, updatable = false)
	private int id;

	private Date day;
	private Time hour;
	private String classroom;

	@OneToOne
	@JoinColumn(name = "course_id")
	@JsonBackReference
	private Course course;
	
	@OneToMany(mappedBy = "id.exam")
	private List<StudentExam> students;

	public Exam() {
	}

	public Exam(int id, Date date, String classroom, Course course) {
		this.id = id;
		this.day = date;
		this.classroom = classroom;
		this.course = course;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Time getHour() {
		return hour;
	}

	public void setHour(Time hour) {
		this.hour = hour;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", day=" + day + ", hour=" + hour + ", classroom=" + classroom + ", course=" + course
				+ "]";
	}
	
	
}


