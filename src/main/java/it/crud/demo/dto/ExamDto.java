package it.crud.demo.dto;

import java.sql.Date;
import java.sql.Time;

public class ExamDto {
	
	private int id;
	private Date day;
	private Time hour;
	private String classroom;
	private int courseId;
	
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
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	

}
