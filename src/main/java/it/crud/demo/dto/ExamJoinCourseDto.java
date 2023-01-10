package it.crud.demo.dto;

import java.sql.Date;
import java.sql.Time;

public class ExamJoinCourseDto {

	private int id;
	private Date day;
	private Time hour;
	private int vote;
	private String classroom;
	private String courseSubject;
	private String teacherName;
	private String teacherSurname;

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

	public String getCourseSubject() {
		return courseSubject;
	}

	public void setCourseSubject(String courseSubject) {
		this.courseSubject = courseSubject;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherSurname() {
		return teacherSurname;
	}

	public void setTeacherSurname(String teacherSurname) {
		this.teacherSurname = teacherSurname;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}
		
}
