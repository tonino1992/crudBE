package it.crud.demo.dto;

public class CourseJoinTeacherDto {
	
	private int id;
	private String subject;
	private double hourAmount;
	private String teacherName;
	private String teacherSurname;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public double getHourAmount() {
		return hourAmount;
	}
	public void setHourAmount(double hourAmount) {
		this.hourAmount = hourAmount;
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
	
	

}
